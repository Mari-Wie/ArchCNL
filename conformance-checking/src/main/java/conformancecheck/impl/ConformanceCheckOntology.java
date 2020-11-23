package conformancecheck.impl;

import java.io.InputStream;
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
import org.apache.jena.rdf.model.Resource;

import datatypes.ArchitectureRule;
import datatypes.ConstraintViolation;
import datatypes.StatementTriple;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
 
public class ConformanceCheckOntology 
{

	private static final Logger LOG = LogManager.getLogger(ConformanceCheckImpl.class);
	
	/* Result */
	private OntModel model;

	private Individual conformanceCheckIndividual;
	private Map<Integer, Individual> architectureRuleIndividualCache;
	private Individual architectureRuleIndividual;
	private Individual architectureViolationIndividual;

	public ConformanceCheckOntology() 
	{
		LOG.info("Start ConformanceCheckOntology ...");
		model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		InputStream architectureConformanceInputStream = getClass().getResourceAsStream("/ontologies/architectureconformance.owl");
		model.read(architectureConformanceInputStream, null);
		architectureRuleIndividualCache = new HashMap<Integer, Individual>();
	}

	public void newConformanceCheck() 
	{
		LOG.info("Start newConformanceCheck ...");
		conformanceCheckIndividual = ConformanceCheckOntologyClassesAndProperties.getConformanceCheckIndividual(model);
		DatatypeProperty dateProperty = ConformanceCheckOntologyClassesAndProperties.getDateProperty(model);
		conformanceCheckIndividual.addLiteral(dateProperty, Calendar.getInstance().getTime().toString());

	}

	public void storeArchitectureRule(ArchitectureRule rule) 
	{
		LOG.info("Start storeArchitectureRule ...");
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

	public void storeConformanceCheckingResultForRule(Model codemodel, ArchitectureRule rule,
			ConstraintViolation violation) 
	{
		LOG.info("Start storeConformanceCheckingResultForRule: " + rule.getCnlSentence());
		
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

	private void connectCodeElementsWithViolations(Model codeModel, ArchitectureRule rule,
			ConstraintViolation violation) 
	{
		LOG.info("Start connectCodeElementsWithViolations: " + rule.getCnlSentence());

		String ruleType = rule.getType().toString();
		DatatypeProperty datatypeProperty = ConformanceCheckOntologyClassesAndProperties.getHasRuleTypeProperty(model);
		architectureRuleIndividual.addLiteral(datatypeProperty, ruleType);

		LOG.info("architectureRuleIndividual hinzugefügt");

		List<StatementTriple> violations = violation.getAsserted();

		for (StatementTriple triple : violations) 
		{
			LOG.info("StatementTriple: " + triple.getSubject() + " , " + triple.getPredicate() + " , " + triple.getObject());
			
			Resource subjectResource = codeModel.getResource(triple.getSubject());
			Resource objectResource = codeModel.getResource(triple.getObject());
			LOG.info("Subject und Objet Ressource erstellt");
			
			model.add(subjectResource,
					ConformanceCheckOntologyClassesAndProperties.getCodeElementIsPartOfViolationSubject(model),
					architectureViolationIndividual);
			model.add(objectResource,
					ConformanceCheckOntologyClassesAndProperties.getCodeElementIsPartOfViolationObject(model),
					architectureViolationIndividual);
		}

	}

	public OntModel getModel() 
	{
		return model;
	}

}
