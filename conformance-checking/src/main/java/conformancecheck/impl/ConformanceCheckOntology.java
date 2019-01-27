package conformancecheck.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;

import api.StardogConnectionAPI;
import api.StardogConstraintViolation;
import api.StardogConstraintViolationResult;
import api.StatementTriple;
import api.exceptions.NoConnectionToStardogServerException;
import datatypes.ArchitectureRule;

public class ConformanceCheckOntology {

	private OntModel model;
	private String projectPath;
	private String resultPath;
	private String codeOntologyURI;
	private String context;
	private StardogConnectionAPI connectionAPI;

	private Individual conformanceCheckIndividual;
	private Map<Integer, Individual> architectureRuleIndividualCache;

	public ConformanceCheckOntology(StardogConnectionAPI connectionAPI, String projectPath, String codeOntologyURI,
			String context) {
		model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		model.read("./architectureconformance.owl");

		this.projectPath = projectPath;
		this.codeOntologyURI = codeOntologyURI;
		this.context = context;

		this.connectionAPI = connectionAPI;

		architectureRuleIndividualCache = new HashMap<Integer, Individual>();
	}

	public void newConformanceCheck() {
		conformanceCheckIndividual = ConformanceCheckOntologyClassesAndProperties.getConformanceCheckIndividual(model);
		DatatypeProperty dateProperty = ConformanceCheckOntologyClassesAndProperties.getDateProperty(model);
		conformanceCheckIndividual.addLiteral(dateProperty, Calendar.getInstance().getTime().toString());

		String dir = this.projectPath + "/" + "conformance_checks/";
		new File(dir).mkdirs();
		this.resultPath = dir + "check.owl";
		save();
	}

	public void storeArchitectureRule(ArchitectureRule rule) {
		Individual architectureRuleIndividual = ConformanceCheckOntologyClassesAndProperties
				.getArchitectureRuleIndividual(model, rule.getId());

		DatatypeProperty cnlRepresentationProperty = ConformanceCheckOntologyClassesAndProperties
				.getCNLRepresentationProperty(model);
		architectureRuleIndividual.addLiteral(cnlRepresentationProperty, rule.getCnlSentence());

		ObjectProperty validatesProperty = ConformanceCheckOntologyClassesAndProperties.getValidatesProperty(model);
		conformanceCheckIndividual.addProperty(validatesProperty, architectureRuleIndividual);

		DatatypeProperty hasRuleIDProperty = ConformanceCheckOntologyClassesAndProperties.getHasRuleIDProperty(model);
		architectureRuleIndividual.addLiteral(hasRuleIDProperty, rule.getId());

		architectureRuleIndividualCache.put(rule.getId(), architectureRuleIndividual);

		save();
	}

	public void save() {
		try {
			model.write(new FileOutputStream(new File(this.resultPath)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void storeConformanceCheckingResultForRule(ArchitectureRule rule, StardogConstraintViolation violation)
			throws FileNotFoundException {

		// get the model: TODO ConformanceCheckImpl soll ConformanceCheckOntology die Abhängigkeit geben
		Model codemodel = connectionAPI.getModelFromContext(context);
		Individual ruleIndividual = architectureRuleIndividualCache.get(rule.getId());
		Individual architectureViolationIndividual = ConformanceCheckOntologyClassesAndProperties
				.getArchitectureViolationIndividual(model);
		conformanceCheckIndividual.addProperty(
				ConformanceCheckOntologyClassesAndProperties.getHasDetectedViolationProperty(model),
				architectureViolationIndividual);
		ruleIndividual.addProperty(ConformanceCheckOntologyClassesAndProperties.getHasViolationProperty(model),
				architectureViolationIndividual);
		architectureViolationIndividual
				.addProperty(ConformanceCheckOntologyClassesAndProperties.getViolatesProperty(model), ruleIndividual);
		
		
		Individual proofIndividual = ConformanceCheckOntologyClassesAndProperties.getProofIndividual(model);
		proofIndividual.addProperty(ConformanceCheckOntologyClassesAndProperties.getProofsProperty(model), architectureViolationIndividual);
		
		try {
			connectCodeElementsWithViolations(codemodel, ruleIndividual, rule, violation, architectureViolationIndividual);
		} catch (NoConnectionToStardogServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void storeConformanceCheckingResultForRule(ArchitectureRule rule, StardogConstraintViolationResult result)
			throws FileNotFoundException {

		// get the model
		Model codemodel = connectionAPI.getModelFromContext(context);
		Individual ruleIndividual = architectureRuleIndividualCache.get(rule.getId());
		for (StardogConstraintViolation violation : result.getViolations()) {
			Individual architectureViolationIndividual = ConformanceCheckOntologyClassesAndProperties
					.getArchitectureViolationIndividual(model);
			
			conformanceCheckIndividual.addProperty(
					ConformanceCheckOntologyClassesAndProperties.getHasDetectedViolationProperty(model),
					architectureViolationIndividual);
			ruleIndividual.addProperty(ConformanceCheckOntologyClassesAndProperties.getHasViolationProperty(model),
					architectureViolationIndividual);
			architectureViolationIndividual.addProperty(
					ConformanceCheckOntologyClassesAndProperties.getViolatesProperty(model), ruleIndividual);
			
			for (String proof : violation.getProofs()) {
				Individual proofIndividual = ConformanceCheckOntologyClassesAndProperties.getProofIndividual(model);
				proofIndividual.addProperty(ConformanceCheckOntologyClassesAndProperties.getProofsProperty(model), architectureViolationIndividual);
				proofIndividual.addLiteral(ConformanceCheckOntologyClassesAndProperties.getProofsTextProperty(model), proof);
			}
			
			
			try {
				System.out.println("store violation text");
				connectCodeElementsWithViolations(codemodel, ruleIndividual, rule, violation, architectureViolationIndividual);
			} catch (NoConnectionToStardogServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		codemodel.close();

	}
	
	private String getNameOfResource(Model model, String resourceAsString) {
		Resource resource = model.getResource(resourceAsString);
		// Code Ontology concepts and properties
		Property codeElementHasNameProperty = model.getProperty(this.codeOntologyURI + "hasName");
		Statement resourceHasName = model.getProperty(resource, codeElementHasNameProperty);
		if (resourceHasName != null) {
			return resourceHasName.getObject().asLiteral().toString();
		} else {
			return null;
		}
	}

	private void connectCodeElementsWithViolations(Model codemodel, Individual ruleIndividual, ArchitectureRule rule,
			StardogConstraintViolation violation, Individual violationIndividual)
			throws FileNotFoundException, NoConnectionToStardogServerException {

		ruleIndividual.addLiteral(ConformanceCheckOntologyClassesAndProperties.getHasRuleTypeProperty(model),
				rule.getType().toString());
		
		
		violation.getNotInferredSubjectName();
		violation.getNotInferredObjectName();
		
		
		List<StatementTriple> violations = violation.getAsserted();
		String text = "";
		for (StatementTriple triple : violations) {
			if (!triple.getPredicate().contains("type")) {
				String subjectName = getNameOfResource(codemodel, triple.getSubject());
				String objectName = getNameOfResource(codemodel, triple.getObject());
				text = subjectName + "  " + triple.getPredicate().split("#")[1] + "  " + objectName;
//				ViolationTuple tuple = new ViolationTuple(triple.getSubject(), triple.getPredicate(),
//						triple.getObject());
				
				Resource subjectResource = codemodel.getResource(triple.getSubject());
				Resource objectResource = codemodel.getResource(triple.getObject());
				model.add(subjectResource, ConformanceCheckOntologyClassesAndProperties.getCodeElementIsPartOfViolationSubject(model), violationIndividual);
				
			}
		}
		
//		for (ViolationTuple violationTuple : sentence.getViolationTuples()) {
//			Resource subjectResource = codemodel.getResource(violationTuple.getViolatingSubject());
//			Resource objectResource = codemodel.getResource(violationTuple.getViolatingObject());
//			model.add(subjectResource, ConformanceCheckOntologyClassesAndProperties.getCodeElementIsPartOfViolationSubject(model), violationIndividual);
//			model.add(objectResource,
//					ConformanceCheckOntologyClassesAndProperties.getCodeElementIsPartOfViolationObject(model),
//					violationIndividual);
//			model.add(subjectResource, ConformanceCheckOntologyClassesAndProperties.getViolatesProperty(model),
//					ruleIndividual);
//		}		
	}
	
	public void saveResultsToDatabase() throws FileNotFoundException, NoConnectionToStardogServerException {
		Model codemodel = connectionAPI.getModelFromContext(context);
		System.out.println("add model to code model");
		codemodel.add(model);
		File file = new File(this.resultPath);
		System.out.println("write to code model");
		codemodel.write(new FileOutputStream(file));
		System.out.println("add data to database");
		connectionAPI.addDataByRDFFileAsNamedGraph(this.resultPath, context);
	}

}
