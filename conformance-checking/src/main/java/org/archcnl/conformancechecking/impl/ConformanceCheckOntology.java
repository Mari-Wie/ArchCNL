package org.archcnl.conformancechecking.impl;

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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.common.datatypes.ArchitectureRule;
import org.archcnl.common.datatypes.ConstraintViolation;
import org.archcnl.common.datatypes.StatementTriple;
 
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
		LOG.trace("Starting ConformanceCheckOntology ...");
		model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		LOG.debug("Reading resource ontology...");
		InputStream architectureConformanceInputStream = getClass().getResourceAsStream("/ontologies/architectureconformance.owl");
		model.read(architectureConformanceInputStream, null);
		architectureRuleIndividualCache = new HashMap<>();
	}

	public void newConformanceCheck() 
	{
		LOG.trace("Starting newConformanceCheck ...");
		conformanceCheckIndividual = ConformanceCheckOntologyClassesAndProperties.getConformanceCheckIndividual(model);
		DatatypeProperty dateProperty = ConformanceCheckOntologyClassesAndProperties.getDateProperty(model);
		conformanceCheckIndividual.addLiteral(dateProperty, Calendar.getInstance().getTime().toString());

	}

	public void storeArchitectureRule(ArchitectureRule rule) 
	{
		LOG.trace("Starting storeArchitectureRule ...");
		LOG.debug("Storing architecture rule:" + rule.getCnlSentence());
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
		LOG.trace("Starting storeConformanceCheckingResultForRule: " + rule.getCnlSentence());
		LOG.debug("Storing conformance checking results for architecture rule: " + rule.getCnlSentence());
		LOG.debug("Creating ArchitectureViolation ...");
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

		connectCodeElementsWithViolations(codemodel, rule, violation);
	}

	private void connectCodeElementsWithViolations(Model codeModel, ArchitectureRule rule,
			ConstraintViolation violation) 
	{
		LOG.trace("Starting connectCodeElementsWithViolations: " + rule.getCnlSentence());
		
		Individual proofIndividual = addNewProof();
		storeRuleType(rule);
		addAssertedStatementsToProof(codeModel, violation, proofIndividual);
		addNotInferredStatementsToProof(codeModel, violation, proofIndividual);
	}

    private Individual addNewProof() {
        LOG.debug("Creating Proof ...");
        Individual proofIndividual = ConformanceCheckOntologyClassesAndProperties.getProofIndividual(model);
        
        proofIndividual.addProperty(ConformanceCheckOntologyClassesAndProperties.getProofsProperty(model),
                architectureViolationIndividual);
        return proofIndividual;
    }

    private void storeRuleType(ArchitectureRule rule) {
        LOG.debug("Storing the rule type ...");
		
		String ruleType = rule.getType().toString();
		DatatypeProperty datatypeProperty = ConformanceCheckOntologyClassesAndProperties.getHasRuleTypeProperty(model);
		architectureRuleIndividual.addLiteral(datatypeProperty, ruleType);
    }

    private void addAssertedStatementsToProof(Model codeModel, ConstraintViolation violation, Individual proofIndividual) {
        LOG.debug("Adding asserted statements ...");
		for (StatementTriple triple : violation.getAsserted()) 
		{
		    LOG.debug("Adding asserted statement to proof: " + triple.getSubject() + " " + triple.getPredicate() + " " + triple.getObject());

			Individual assertedStatement = ConformanceCheckOntologyClassesAndProperties.getAssertedStatement(model);
			addTripleToStatement(codeModel, triple, assertedStatement);
			proofIndividual.addProperty(ConformanceCheckOntologyClassesAndProperties.getHasAssertedStatement(model), assertedStatement);
		}
    }

    private void addNotInferredStatementsToProof(Model codeModel, ConstraintViolation violation, Individual proofIndividual) {
        LOG.debug("Adding not inferred statements ...");
		for (StatementTriple triple : violation.getNotInferred()) 
        {
            LOG.debug("Adding not inferred statement to proof: " + triple.getSubject() + " " + triple.getPredicate() + " " + triple.getObject());

            Individual notInferredStatement = ConformanceCheckOntologyClassesAndProperties.getNotInferredStatement(model);
            addTripleToStatement(codeModel, triple, notInferredStatement);
            proofIndividual.addProperty(ConformanceCheckOntologyClassesAndProperties.getHasNotInferredStatement(model), notInferredStatement);
        }
    }

    private void addTripleToStatement(Model codeModel, StatementTriple triple, Individual statement) {
        Resource subjectResource = codeModel.getResource(triple.getSubject());
        Resource predicateResource = codeModel.getResource(triple.getPredicate());
        Resource objectResource = codeModel.getResource(triple.getObject());
        
        statement.addProperty(ConformanceCheckOntologyClassesAndProperties.getHasSubject(model), subjectResource);
        statement.addProperty(ConformanceCheckOntologyClassesAndProperties.getHasPredicate(model), predicateResource);
        statement.addProperty(ConformanceCheckOntologyClassesAndProperties.getHasObject(model), objectResource);
    }

	public OntModel getModel() 
	{
		return model;
	}

}
