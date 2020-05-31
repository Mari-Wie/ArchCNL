package conformancecheck.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;

import api.StardogConstraintViolation;
import api.StatementTriple;
import datatypes.ArchitectureRule;

public class ConformanceCheckOntology {

	/* Result */
	private OntModel model;

	private Individual conformanceCheckIndividual;
	private Map<Integer, Individual> architectureRuleIndividualCache;
	private Individual architectureRuleIndividual;
	private Individual architectureViolationIndividual;

	public ConformanceCheckOntology() {
		model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		model.read("./architectureconformance.owl");
		architectureRuleIndividualCache = new HashMap<Integer, Individual>();
	}

	public void newConformanceCheck() {
		conformanceCheckIndividual = ConformanceCheckOntologyClassesAndProperties.getConformanceCheckIndividual(model);
		DatatypeProperty dateProperty = ConformanceCheckOntologyClassesAndProperties.getDateProperty(model);
		conformanceCheckIndividual.addLiteral(dateProperty, Calendar.getInstance().getTime().toString());

	}

	public void storeArchitectureRule(ArchitectureRule rule) {
		architectureRuleIndividual = ConformanceCheckOntologyClassesAndProperties.getArchitectureRuleIndividual(model,
				rule.getId());

		DatatypeProperty cnlRepresentationProperty = ConformanceCheckOntologyClassesAndProperties
				.getCNLRepresentationProperty(model);
		architectureRuleIndividual.addLiteral(cnlRepresentationProperty, rule.getCnlSentence());

		ObjectProperty validatesProperty = ConformanceCheckOntologyClassesAndProperties.getValidatesProperty(model);
		conformanceCheckIndividual.addProperty(validatesProperty, architectureRuleIndividual);

		DatatypeProperty hasRuleIDProperty = ConformanceCheckOntologyClassesAndProperties.getHasRuleIDProperty(model);
		architectureRuleIndividual.addLiteral(hasRuleIDProperty, rule.getId());

		architectureRuleIndividualCache.put(rule.getId(), architectureRuleIndividual);

	}

	public void storeConformanceCheckingResultForRule(CodeModel codemodel, ArchitectureRule rule,
			StardogConstraintViolation violation) {
		architectureViolationIndividual = ConformanceCheckOntologyClassesAndProperties
				.getArchitectureViolationIndividual(model);
		conformanceCheckIndividual.addProperty(
				ConformanceCheckOntologyClassesAndProperties.getHasDetectedViolationProperty(model),
				architectureViolationIndividual);
		architectureRuleIndividual.addProperty(
				ConformanceCheckOntologyClassesAndProperties.getHasViolationProperty(model),
				architectureViolationIndividual);
		architectureViolationIndividual.addProperty(
				ConformanceCheckOntologyClassesAndProperties.getViolatesProperty(model), architectureRuleIndividual);

		Individual proofIndividual = ConformanceCheckOntologyClassesAndProperties.getProofIndividual(model);
		proofIndividual.addProperty(ConformanceCheckOntologyClassesAndProperties.getProofsProperty(model),
				architectureViolationIndividual);

		connectCodeElementsWithViolations(codemodel, rule, violation);
	}

	private void connectCodeElementsWithViolations(CodeModel codeModel, ArchitectureRule rule,
			StardogConstraintViolation violation) {

		architectureRuleIndividual.addLiteral(
				ConformanceCheckOntologyClassesAndProperties.getHasRuleTypeProperty(model), rule.getType().toString());

		// violation.getNotInferredSubjectName();
		// violation.getNotInferredObjectName();

		List<StatementTriple> violations = violation.getAsserted();
		//String text = "";
		for (StatementTriple triple : violations) {
			// if (!triple.getPredicate().contains("type")) {
			//String subjectName = codeModel.getNameOfResource(triple.getSubject());
			//String objectName = codeModel.getNameOfResource(triple.getObject());
			//text = subjectName + "  " + triple.getPredicate().split("#")[1] + "  " + objectName;

			Resource subjectResource = codeModel.getResource(triple.getSubject());
			Resource objectResource = codeModel.getResource(triple.getObject());
			model.add(subjectResource,
					ConformanceCheckOntologyClassesAndProperties.getCodeElementIsPartOfViolationSubject(model),
					architectureViolationIndividual);
			model.add(objectResource,
					ConformanceCheckOntologyClassesAndProperties.getCodeElementIsPartOfViolationObject(model),
					architectureViolationIndividual);
		}

	}

	public OntModel getModel() {
		return model;
	}

}
