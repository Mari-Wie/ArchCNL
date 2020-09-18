/*
 * generated by Xtext 2.22.0
 */
package org.architecture.cnl.serializer;

import com.google.inject.Inject;
import java.util.Set;
import org.architecture.cnl.archcnl.AndObjectConceptExpression;
import org.architecture.cnl.archcnl.Anything;
import org.architecture.cnl.archcnl.ArchcnlPackage;
import org.architecture.cnl.archcnl.CanOnlyRuleType;
import org.architecture.cnl.archcnl.CardinalityRuleType;
import org.architecture.cnl.archcnl.Concept;
import org.architecture.cnl.archcnl.ConceptAssertion;
import org.architecture.cnl.archcnl.ConceptExpression;
import org.architecture.cnl.archcnl.ConditionalRuleType;
import org.architecture.cnl.archcnl.DataStatement;
import org.architecture.cnl.archcnl.DatatypePropertyAssertion;
import org.architecture.cnl.archcnl.DatatypeRelation;
import org.architecture.cnl.archcnl.FactStatement;
import org.architecture.cnl.archcnl.Model;
import org.architecture.cnl.archcnl.MustRuleType;
import org.architecture.cnl.archcnl.NegationRuleType;
import org.architecture.cnl.archcnl.Nothing;
import org.architecture.cnl.archcnl.ObjectConceptExpression;
import org.architecture.cnl.archcnl.ObjectPropertyAssertion;
import org.architecture.cnl.archcnl.ObjectRelation;
import org.architecture.cnl.archcnl.OnlyCanRuleType;
import org.architecture.cnl.archcnl.OrObjectConceptExpression;
import org.architecture.cnl.archcnl.Sentence;
import org.architecture.cnl.archcnl.StatementList;
import org.architecture.cnl.archcnl.SubConceptRuleType;
import org.architecture.cnl.archcnl.ThatExpression;
import org.architecture.cnl.archcnl.Variable;
import org.architecture.cnl.archcnl.VariableStatement;
import org.architecture.cnl.services.ArchcnlGrammarAccess;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class ArchcnlSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private ArchcnlGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == ArchcnlPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case ArchcnlPackage.AND_OBJECT_CONCEPT_EXPRESSION:
				sequence_AndObjectConceptExpression(context, (AndObjectConceptExpression) semanticObject); 
				return; 
			case ArchcnlPackage.ANYTHING:
				sequence_Anything(context, (Anything) semanticObject); 
				return; 
			case ArchcnlPackage.CAN_ONLY_RULE_TYPE:
				sequence_CanOnlyRuleType(context, (CanOnlyRuleType) semanticObject); 
				return; 
			case ArchcnlPackage.CARDINALITY_RULE_TYPE:
				sequence_CardinalityRuleType(context, (CardinalityRuleType) semanticObject); 
				return; 
			case ArchcnlPackage.CONCEPT:
				sequence_Concept(context, (Concept) semanticObject); 
				return; 
			case ArchcnlPackage.CONCEPT_ASSERTION:
				sequence_ConceptAssertion(context, (ConceptAssertion) semanticObject); 
				return; 
			case ArchcnlPackage.CONCEPT_EXPRESSION:
				sequence_ConceptExpression(context, (ConceptExpression) semanticObject); 
				return; 
			case ArchcnlPackage.CONDITIONAL_RULE_TYPE:
				sequence_ConditionalRuleType(context, (ConditionalRuleType) semanticObject); 
				return; 
			case ArchcnlPackage.DATA_STATEMENT:
				sequence_DataStatement(context, (DataStatement) semanticObject); 
				return; 
			case ArchcnlPackage.DATATYPE_PROPERTY_ASSERTION:
				sequence_DatatypePropertyAssertion(context, (DatatypePropertyAssertion) semanticObject); 
				return; 
			case ArchcnlPackage.DATATYPE_RELATION:
				sequence_DatatypeRelation(context, (DatatypeRelation) semanticObject); 
				return; 
			case ArchcnlPackage.FACT_STATEMENT:
				sequence_FactStatement(context, (FactStatement) semanticObject); 
				return; 
			case ArchcnlPackage.MODEL:
				sequence_Model(context, (Model) semanticObject); 
				return; 
			case ArchcnlPackage.MUST_RULE_TYPE:
				sequence_MustRuleType(context, (MustRuleType) semanticObject); 
				return; 
			case ArchcnlPackage.NEGATION_RULE_TYPE:
				sequence_NegationRuleType(context, (NegationRuleType) semanticObject); 
				return; 
			case ArchcnlPackage.NOTHING:
				sequence_Nothing(context, (Nothing) semanticObject); 
				return; 
			case ArchcnlPackage.OBJECT:
				sequence_Object(context, (org.architecture.cnl.archcnl.Object) semanticObject); 
				return; 
			case ArchcnlPackage.OBJECT_CONCEPT_EXPRESSION:
				sequence_ObjectConceptExpression(context, (ObjectConceptExpression) semanticObject); 
				return; 
			case ArchcnlPackage.OBJECT_PROPERTY_ASSERTION:
				sequence_ObjectPropertyAssertion(context, (ObjectPropertyAssertion) semanticObject); 
				return; 
			case ArchcnlPackage.OBJECT_RELATION:
				sequence_ObjectRelation(context, (ObjectRelation) semanticObject); 
				return; 
			case ArchcnlPackage.ONLY_CAN_RULE_TYPE:
				sequence_OnlyCanRuleType(context, (OnlyCanRuleType) semanticObject); 
				return; 
			case ArchcnlPackage.OR_OBJECT_CONCEPT_EXPRESSION:
				sequence_OrObjectConceptExpression(context, (OrObjectConceptExpression) semanticObject); 
				return; 
			case ArchcnlPackage.SENTENCE:
				sequence_Sentence(context, (Sentence) semanticObject); 
				return; 
			case ArchcnlPackage.STATEMENT_LIST:
				sequence_StatementList(context, (StatementList) semanticObject); 
				return; 
			case ArchcnlPackage.SUB_CONCEPT_RULE_TYPE:
				sequence_SubConceptRuleType(context, (SubConceptRuleType) semanticObject); 
				return; 
			case ArchcnlPackage.THAT_EXPRESSION:
				sequence_ThatExpression(context, (ThatExpression) semanticObject); 
				return; 
			case ArchcnlPackage.VARIABLE:
				sequence_Variable(context, (Variable) semanticObject); 
				return; 
			case ArchcnlPackage.VARIABLE_STATEMENT:
				sequence_VariableStatement(context, (VariableStatement) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Contexts:
	 *     AndObjectConceptExpression returns AndObjectConceptExpression
	 *
	 * Constraint:
	 *     expression=ObjectConceptExpression
	 */
	protected void sequence_AndObjectConceptExpression(ISerializationContext context, AndObjectConceptExpression semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.AND_OBJECT_CONCEPT_EXPRESSION__EXPRESSION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.AND_OBJECT_CONCEPT_EXPRESSION__EXPRESSION));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getAndObjectConceptExpressionAccess().getExpressionObjectConceptExpressionParserRuleCall_1_0(), semanticObject.getExpression());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Anything returns Anything
	 *
	 * Constraint:
	 *     relation=Relation
	 */
	protected void sequence_Anything(ISerializationContext context, Anything semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.ANYTHING__RELATION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.ANYTHING__RELATION));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getAnythingAccess().getRelationRelationParserRuleCall_0_0(), semanticObject.getRelation());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     CanOnlyRuleType returns CanOnlyRuleType
	 *
	 * Constraint:
	 *     (modifier='can-only' object=Object)
	 */
	protected void sequence_CanOnlyRuleType(ISerializationContext context, CanOnlyRuleType semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.CAN_ONLY_RULE_TYPE__MODIFIER) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.CAN_ONLY_RULE_TYPE__MODIFIER));
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.CAN_ONLY_RULE_TYPE__OBJECT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.CAN_ONLY_RULE_TYPE__OBJECT));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getCanOnlyRuleTypeAccess().getModifierCanOnlyKeyword_0_0(), semanticObject.getModifier());
		feeder.accept(grammarAccess.getCanOnlyRuleTypeAccess().getObjectObjectParserRuleCall_1_0(), semanticObject.getObject());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     CardinalityRuleType returns CardinalityRuleType
	 *
	 * Constraint:
	 *     (modifer='can' object=Object)
	 */
	protected void sequence_CardinalityRuleType(ISerializationContext context, CardinalityRuleType semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.CARDINALITY_RULE_TYPE__MODIFER) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.CARDINALITY_RULE_TYPE__MODIFER));
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.CARDINALITY_RULE_TYPE__OBJECT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.CARDINALITY_RULE_TYPE__OBJECT));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getCardinalityRuleTypeAccess().getModiferCanKeyword_0_0(), semanticObject.getModifer());
		feeder.accept(grammarAccess.getCardinalityRuleTypeAccess().getObjectObjectParserRuleCall_1_0(), semanticObject.getObject());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     ConceptAssertion returns ConceptAssertion
	 *
	 * Constraint:
	 *     (individual=NAME concept=Concept)
	 */
	protected void sequence_ConceptAssertion(ISerializationContext context, ConceptAssertion semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.CONCEPT_ASSERTION__INDIVIDUAL) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.CONCEPT_ASSERTION__INDIVIDUAL));
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.CONCEPT_ASSERTION__CONCEPT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.CONCEPT_ASSERTION__CONCEPT));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getConceptAssertionAccess().getIndividualNAMETerminalRuleCall_0_0(), semanticObject.getIndividual());
		feeder.accept(grammarAccess.getConceptAssertionAccess().getConceptConceptParserRuleCall_3_0(), semanticObject.getConcept());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     ConceptExpression returns ConceptExpression
	 *
	 * Constraint:
	 *     (concept=Concept that+=ThatExpression*)
	 */
	protected void sequence_ConceptExpression(ISerializationContext context, ConceptExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Concept returns Concept
	 *
	 * Constraint:
	 *     conceptName=NAME
	 */
	protected void sequence_Concept(ISerializationContext context, Concept semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.CONCEPT__CONCEPT_NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.CONCEPT__CONCEPT_NAME));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getConceptAccess().getConceptNameNAMETerminalRuleCall_0(), semanticObject.getConceptName());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     ConditionalRuleType returns ConditionalRuleType
	 *
	 * Constraint:
	 *     (
	 *         start='If' 
	 *         subject=ConceptExpression 
	 *         relation=Relation 
	 *         object=ConceptExpression 
	 *         relation2=Relation 
	 *         object2=ConceptExpression
	 *     )
	 */
	protected void sequence_ConditionalRuleType(ISerializationContext context, ConditionalRuleType semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.CONDITIONAL_RULE_TYPE__START) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.CONDITIONAL_RULE_TYPE__START));
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.CONDITIONAL_RULE_TYPE__SUBJECT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.CONDITIONAL_RULE_TYPE__SUBJECT));
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.CONDITIONAL_RULE_TYPE__RELATION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.CONDITIONAL_RULE_TYPE__RELATION));
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.CONDITIONAL_RULE_TYPE__OBJECT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.CONDITIONAL_RULE_TYPE__OBJECT));
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.CONDITIONAL_RULE_TYPE__RELATION2) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.CONDITIONAL_RULE_TYPE__RELATION2));
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.CONDITIONAL_RULE_TYPE__OBJECT2) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.CONDITIONAL_RULE_TYPE__OBJECT2));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getConditionalRuleTypeAccess().getStartIfKeyword_0_0(), semanticObject.getStart());
		feeder.accept(grammarAccess.getConditionalRuleTypeAccess().getSubjectConceptExpressionParserRuleCall_1_0(), semanticObject.getSubject());
		feeder.accept(grammarAccess.getConditionalRuleTypeAccess().getRelationRelationParserRuleCall_2_0(), semanticObject.getRelation());
		feeder.accept(grammarAccess.getConditionalRuleTypeAccess().getObjectConceptExpressionParserRuleCall_3_0(), semanticObject.getObject());
		feeder.accept(grammarAccess.getConditionalRuleTypeAccess().getRelation2RelationParserRuleCall_8_0(), semanticObject.getRelation2());
		feeder.accept(grammarAccess.getConditionalRuleTypeAccess().getObject2ConceptExpressionParserRuleCall_10_0(), semanticObject.getObject2());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     DataStatement returns DataStatement
	 *
	 * Constraint:
	 *     (stringValue=STRING | intValue=INT)
	 */
	protected void sequence_DataStatement(ISerializationContext context, DataStatement semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     RoleAssertion returns DatatypePropertyAssertion
	 *     DatatypePropertyAssertion returns DatatypePropertyAssertion
	 *
	 * Constraint:
	 *     (individual=NAME relation=DatatypeRelation (string=STRING | int=INT))
	 */
	protected void sequence_DatatypePropertyAssertion(ISerializationContext context, DatatypePropertyAssertion semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Relation returns DatatypeRelation
	 *     DatatypeRelation returns DatatypeRelation
	 *
	 * Constraint:
	 *     relationName=RELATION_NAME
	 */
	protected void sequence_DatatypeRelation(ISerializationContext context, DatatypeRelation semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.RELATION__RELATION_NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.RELATION__RELATION_NAME));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getDatatypeRelationAccess().getRelationNameRELATION_NAMETerminalRuleCall_0_0(), semanticObject.getRelationName());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     FactStatement returns FactStatement
	 *
	 * Constraint:
	 *     (assertion=ConceptAssertion | assertion=RoleAssertion)
	 */
	protected void sequence_FactStatement(ISerializationContext context, FactStatement semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Model returns Model
	 *
	 * Constraint:
	 *     sentence+=Sentence+
	 */
	protected void sequence_Model(ISerializationContext context, Model semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     MustRuleType returns MustRuleType
	 *
	 * Constraint:
	 *     (modifier='must' object=Object)
	 */
	protected void sequence_MustRuleType(ISerializationContext context, MustRuleType semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.MUST_RULE_TYPE__MODIFIER) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.MUST_RULE_TYPE__MODIFIER));
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.MUST_RULE_TYPE__OBJECT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.MUST_RULE_TYPE__OBJECT));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getMustRuleTypeAccess().getModifierMustKeyword_0_0(), semanticObject.getModifier());
		feeder.accept(grammarAccess.getMustRuleTypeAccess().getObjectObjectParserRuleCall_1_0(), semanticObject.getObject());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     NegationRuleType returns NegationRuleType
	 *
	 * Constraint:
	 *     (subject=ConceptExpression object=Object)
	 */
	protected void sequence_NegationRuleType(ISerializationContext context, NegationRuleType semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.NEGATION_RULE_TYPE__SUBJECT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.NEGATION_RULE_TYPE__SUBJECT));
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.NEGATION_RULE_TYPE__OBJECT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.NEGATION_RULE_TYPE__OBJECT));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getNegationRuleTypeAccess().getSubjectConceptExpressionParserRuleCall_1_0_1_0(), semanticObject.getSubject());
		feeder.accept(grammarAccess.getNegationRuleTypeAccess().getObjectObjectParserRuleCall_1_0_3_0(), semanticObject.getObject());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     NegationRuleType returns Nothing
	 *     Nothing returns Nothing
	 *
	 * Constraint:
	 *     object=Object
	 */
	protected void sequence_Nothing(ISerializationContext context, Nothing semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.NEGATION_RULE_TYPE__OBJECT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.NEGATION_RULE_TYPE__OBJECT));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getNothingAccess().getObjectObjectParserRuleCall_2_0(), semanticObject.getObject());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     ObjectConceptExpression returns ObjectConceptExpression
	 *
	 * Constraint:
	 *     (
	 *         (relation=Relation ((cardinality='at-most' | cardinality='at-least' | cardinality='exactly') number=INT)? concept=ConceptExpression) | 
	 *         (relation=DatatypeRelation data=DataStatement)
	 *     )
	 */
	protected void sequence_ObjectConceptExpression(ISerializationContext context, ObjectConceptExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     RoleAssertion returns ObjectPropertyAssertion
	 *     ObjectPropertyAssertion returns ObjectPropertyAssertion
	 *
	 * Constraint:
	 *     (individual=NAME relation=Relation concept=Concept)
	 */
	protected void sequence_ObjectPropertyAssertion(ISerializationContext context, ObjectPropertyAssertion semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.ROLE_ASSERTION__INDIVIDUAL) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.ROLE_ASSERTION__INDIVIDUAL));
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.ROLE_ASSERTION__RELATION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.ROLE_ASSERTION__RELATION));
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.OBJECT_PROPERTY_ASSERTION__CONCEPT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.OBJECT_PROPERTY_ASSERTION__CONCEPT));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getObjectPropertyAssertionAccess().getIndividualNAMETerminalRuleCall_0_0(), semanticObject.getIndividual());
		feeder.accept(grammarAccess.getObjectPropertyAssertionAccess().getRelationRelationParserRuleCall_1_0(), semanticObject.getRelation());
		feeder.accept(grammarAccess.getObjectPropertyAssertionAccess().getConceptConceptParserRuleCall_3_0(), semanticObject.getConcept());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Relation returns ObjectRelation
	 *     ObjectRelation returns ObjectRelation
	 *
	 * Constraint:
	 *     relationName=RELATION_NAME
	 */
	protected void sequence_ObjectRelation(ISerializationContext context, ObjectRelation semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.RELATION__RELATION_NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.RELATION__RELATION_NAME));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getObjectRelationAccess().getRelationNameRELATION_NAMETerminalRuleCall_0(), semanticObject.getRelationName());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Object returns Object
	 *
	 * Constraint:
	 *     (anything=Anything | (expression=ObjectConceptExpression (objectAndList+=AndObjectConceptExpression | objectOrList+=OrObjectConceptExpression)*))
	 */
	protected void sequence_Object(ISerializationContext context, org.architecture.cnl.archcnl.Object semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     OnlyCanRuleType returns OnlyCanRuleType
	 *
	 * Constraint:
	 *     (start='Only' subject=ConceptExpression object=Object)
	 */
	protected void sequence_OnlyCanRuleType(ISerializationContext context, OnlyCanRuleType semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.ONLY_CAN_RULE_TYPE__START) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.ONLY_CAN_RULE_TYPE__START));
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.ONLY_CAN_RULE_TYPE__SUBJECT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.ONLY_CAN_RULE_TYPE__SUBJECT));
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.ONLY_CAN_RULE_TYPE__OBJECT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.ONLY_CAN_RULE_TYPE__OBJECT));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getOnlyCanRuleTypeAccess().getStartOnlyKeyword_0_0(), semanticObject.getStart());
		feeder.accept(grammarAccess.getOnlyCanRuleTypeAccess().getSubjectConceptExpressionParserRuleCall_1_0(), semanticObject.getSubject());
		feeder.accept(grammarAccess.getOnlyCanRuleTypeAccess().getObjectObjectParserRuleCall_3_0(), semanticObject.getObject());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     OrObjectConceptExpression returns OrObjectConceptExpression
	 *
	 * Constraint:
	 *     expression=ObjectConceptExpression
	 */
	protected void sequence_OrObjectConceptExpression(ISerializationContext context, OrObjectConceptExpression semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.OR_OBJECT_CONCEPT_EXPRESSION__EXPRESSION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.OR_OBJECT_CONCEPT_EXPRESSION__EXPRESSION));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getOrObjectConceptExpressionAccess().getExpressionObjectConceptExpressionParserRuleCall_1_0(), semanticObject.getExpression());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Sentence returns Sentence
	 *
	 * Constraint:
	 *     (
	 *         (subject=ConceptExpression (ruletype=MustRuleType | ruletype=CanOnlyRuleType | ruletype=CardinalityRuleType | ruletype=SubConceptRuleType)) | 
	 *         ruletype=OnlyCanRuleType | 
	 *         ruletype=ConditionalRuleType | 
	 *         ruletype=NegationRuleType | 
	 *         facts+=FactStatement
	 *     )
	 */
	protected void sequence_Sentence(ISerializationContext context, Sentence semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     StatementList returns StatementList
	 *
	 * Constraint:
	 *     (relation=Relation (expression=ConceptExpression | expression=DataStatement | expression=VariableStatement))
	 */
	protected void sequence_StatementList(ISerializationContext context, StatementList semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     SubConceptRuleType returns SubConceptRuleType
	 *
	 * Constraint:
	 *     (modifier='must' object=ConceptExpression)
	 */
	protected void sequence_SubConceptRuleType(ISerializationContext context, SubConceptRuleType semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.SUB_CONCEPT_RULE_TYPE__MODIFIER) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.SUB_CONCEPT_RULE_TYPE__MODIFIER));
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.SUB_CONCEPT_RULE_TYPE__OBJECT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.SUB_CONCEPT_RULE_TYPE__OBJECT));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getSubConceptRuleTypeAccess().getModifierMustKeyword_0_0(), semanticObject.getModifier());
		feeder.accept(grammarAccess.getSubConceptRuleTypeAccess().getObjectConceptExpressionParserRuleCall_2_0(), semanticObject.getObject());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     ThatExpression returns ThatExpression
	 *
	 * Constraint:
	 *     list+=StatementList+
	 */
	protected void sequence_ThatExpression(ISerializationContext context, ThatExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     VariableStatement returns VariableStatement
	 *
	 * Constraint:
	 *     (concept=Concept variable=Variable)
	 */
	protected void sequence_VariableStatement(ISerializationContext context, VariableStatement semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.VARIABLE_STATEMENT__CONCEPT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.VARIABLE_STATEMENT__CONCEPT));
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.VARIABLE_STATEMENT__VARIABLE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.VARIABLE_STATEMENT__VARIABLE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getVariableStatementAccess().getConceptConceptParserRuleCall_0_0(), semanticObject.getConcept());
		feeder.accept(grammarAccess.getVariableStatementAccess().getVariableVariableParserRuleCall_1_0(), semanticObject.getVariable());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Variable returns Variable
	 *
	 * Constraint:
	 *     name=VARIABLE_NAME
	 */
	protected void sequence_Variable(ISerializationContext context, Variable semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ArchcnlPackage.Literals.VARIABLE__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ArchcnlPackage.Literals.VARIABLE__NAME));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getVariableAccess().getNameVARIABLE_NAMETerminalRuleCall_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
}
