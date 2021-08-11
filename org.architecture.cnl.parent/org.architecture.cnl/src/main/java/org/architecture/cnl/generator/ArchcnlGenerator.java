package org.architecture.cnl.generator;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.common.datatypes.RuleType;
import org.archcnl.owlcreator.api.APIFactory;
import org.archcnl.owlcreator.api.OntologyAPI;
import org.architecture.cnl.RuleTypeStorageSingleton;
import org.architecture.cnl.archcnl.*;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.semanticweb.owlapi.model.*;

/**
 * This class is responsible for the conversion from the (already parsed) CNL to OWL statements.
 *
 * <p>See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
public class ArchcnlGenerator extends AbstractGenerator {
    private static final Logger LOG = LogManager.getLogger(ArchcnlGenerator.class);
    private static int id = 0;
    private String namespace;
    private OntologyAPI api;

    /**
     * "Translates" some parsed CNL sentences to an OWL ontology. The ontology will be stored in a
     * file. The file's path is './architecture<id>.owl' where '<id>' is a counter which counts how
     * often this method is called. Thus, the first call will produce a file 'architecture0.owl',
     * the second one a file 'architecture1.owl', and so on.
     *
     * <p>The ontology uses the namespace "http://www.arch-ont.org/ontologies/architecture.owl".
     * When refering to its elements, this namespace/prefix must be used (e.g. when writing
     * architecture-to-code mapping rules).
     *
     * @param resource The parsed CNL input.
     * @param fsa ???, but is not used anyway
     * @param context ???, but is not used anyway
     */
    @Override
    public void doGenerate(
            final Resource resource,
            final IFileSystemAccess2 fsa,
            final IGeneratorContext context) {
        this.namespace = "http://www.arch-ont.org/ontologies/architecture.owl";
        final String filename = RuleTypeStorageSingleton.getInstance().getOutputFile();
        this.api = APIFactory.get();
        try {
            this.api.createOntology(filename, this.namespace);
        } catch (IOException e) {
            throw new UncheckedIOException("Error while creating an ontology", e);
        }
        Iterable<EObject> resourceIterable =
                IteratorExtensions.toIterable(resource.getAllContents());
        Iterable<Sentence> sentenceIterable = Iterables.filter(resourceIterable, Sentence.class);
        LOG.debug("Start compiling CNL sentences ...");
        for (final Sentence s : sentenceIterable) {
            {
                ConceptExpression _subject = s.getSubject();
                String sentenceSubjectString =
                        ("ID " + id + ": " + "sentence subject: " + _subject);
                LOG.trace(sentenceSubjectString);
                EObject ruletype = s.getRuletype();
                String sentenceRuletypeString =
                        ("ID " + id + ": " + "sentence ruletype: " + ruletype);
                LOG.trace(sentenceRuletypeString);
                this.compile(s);
            }
        }
        this.api.triggerSave();
        LOG.debug("... compiled all sentences.");
        this.api.removeOntology();
    }

    /** Compiles a single CNL sentence. */
    public void compile(final Sentence s) {
        final ConceptExpression subject = s.getSubject();
        final EObject ruletype = s.getRuletype();
        final RuleTypeStorageSingleton typeStorage = RuleTypeStorageSingleton.getInstance();
        LOG.trace("Compiling a new sentence ...");
        LOG.trace("ID " + id + ": " + ruletype);
        if (ruletype instanceof MustRuleType) {
            this.compile(((MustRuleType) ruletype), subject);
            typeStorage.storeTypeOfRule(id, RuleType.EXISTENTIAL);
        } else if (ruletype instanceof CanOnlyRuleType) {
            this.compile(((CanOnlyRuleType) ruletype), subject);
            typeStorage.storeTypeOfRule(id, RuleType.UNIVERSAL);
        } else if (ruletype instanceof OnlyCanRuleType) {
            this.compile(((OnlyCanRuleType) ruletype));
            typeStorage.storeTypeOfRule(id, RuleType.DOMAIN_RANGE);
        } else if (ruletype instanceof ConditionalRuleType) {
            this.compile(((ConditionalRuleType) ruletype));
            typeStorage.storeTypeOfRule(id, RuleType.CONDITIONAL);
        } else if (ruletype instanceof NegationRuleType) {
            this.compile(((NegationRuleType) ruletype));
            typeStorage.storeTypeOfRule(id, RuleType.NEGATION);
        } else if (ruletype instanceof SubConceptRuleType) {
            this.compile(((SubConceptRuleType) ruletype), subject);
            typeStorage.storeTypeOfRule(id, RuleType.SUB_CONCEPT);
        } else if (ruletype instanceof CardinalityRuleType) {
            this.compile(((CardinalityRuleType) ruletype), subject);
        } else if (ruletype instanceof FactStatement) {
            this.compile(((FactStatement) ruletype));
            typeStorage.storeTypeOfRule(id, RuleType.FACT);
        }

        LOG.debug(("Processed sentence with ID " + id));
        id++;
    }

    public void compile(final SubConceptRuleType subconcept, final ConceptExpression subject) {
        LOG.trace("ID " + id + ": " + "compiling SubConceptRuleType ...");
        final OWLClassExpression subjectConceptExpression = this.compile(subject);
        final OWLClass object =
                this.api.createOWLClass(
                        this.namespace, subconcept.getObject().getConcept().getConceptName());
        this.api.addSubClassAxiom(object, subjectConceptExpression);
    }

    public void compile(final CardinalityRuleType cardrule, final ConceptExpression subject) {
        LOG.trace("ID " + id + ": " + "compiling CardinalityRuleType ...");
        final OWLClassExpression subjectConceptExpression = this.compile(subject);
        OWLClassExpression object = this.compile(cardrule.getObject().getExpression());
        final ArrayList<OWLClassExpression> listResult = new ArrayList<>();
        listResult.add(object);
        EList<AndObjectConceptExpression> _objectAndList = cardrule.getObject().getObjectAndList();
        for (final AndObjectConceptExpression o : _objectAndList) {
            {
                final OWLClassExpression result = this.compile(o.getExpression());
                listResult.add(result);
            }
        }
        int _size = listResult.size();
        boolean _greaterThan = (_size > 1);
        if (_greaterThan) {
            object = this.api.createIntersection(listResult);
            listResult.clear();
        }
        EList<OrObjectConceptExpression> _objectOrList = cardrule.getObject().getObjectOrList();
        for (final OrObjectConceptExpression o_1 : _objectOrList) {
            {
                final OWLClassExpression result = this.compile(o_1.getExpression());
                listResult.add(result);
            }
        }
        int _size_1 = listResult.size();
        boolean _greaterThan_1 = (_size_1 > 1);
        if (_greaterThan_1) {
            object = this.api.createUnion(listResult);
            listResult.clear();
        }
        this.api.addSubClassAxiom(object, subjectConceptExpression);
    }

    public void compile(final NegationRuleType negation) {
        LOG.trace("ID " + id + ": " + "compiling NegationRuleType ...");
        if (negation instanceof Nothing) {
            var subject = api.getOWLTop();
            var object = compile(negation.getObject().getExpression());
            var listResult = new ArrayList<OWLClassExpression>();
            listResult.add(object);

            for (var andObject : negation.getObject().getObjectAndList()) {
                var result = compile(andObject.getExpression());
                listResult.add(result);
            }

            // if there are "and objectes", create intersections
            if (listResult.size() > 1) {
                object = api.createIntersection(listResult);
                listResult.clear();
            }

            for (var orObject : negation.getObject().getObjectOrList()) {
                var result = compile(orObject.getExpression());
                listResult.add(result);
            }

            // if there are "or objectes", create unions
            if (listResult.size() > 1) {
                object = api.createUnion(listResult);
                listResult.clear();
            }

            // everything (in this case the topmost owl object, e.g. everything, as subject) should
            // be disjoint with the object
            api.addDisjointAxiom(subject, object);
        } else {
            var subject = compile(negation.getSubject());

            if (negation.getObject().getAnything() != null) {
                var relation =
                        api.createOWLObjectProperty(
                                namespace,
                                negation.getObject().getAnything().getRelation().getRelationName());
                var object = api.getOWLTop();

                // the relation does not apply to the subject in any case, since the object topmost
                // owl object, e.g. everything
                api.addNegationAxiom(subject, object, relation);
            } else {
                var object = compile(negation.getObject().getExpression());
                var listResult = new ArrayList<OWLClassExpression>();
                listResult.add(object);

                for (var andObject : negation.getObject().getObjectAndList()) {
                    var result = compile(andObject.getExpression());
                    listResult.add(result);
                }

                if (listResult.size() > 1) {
                    object = api.createIntersection(listResult);
                    listResult.clear();
                }

                for (var orObject : negation.getObject().getObjectOrList()) {
                    var result = compile(orObject.getExpression());
                    listResult.add(result);
                }

                if (listResult.size() > 1) {
                    object = api.createUnion(listResult);
                    listResult.clear();
                }

                // subject should be disjoint with the object
                api.addDisjointAxiom(subject, object);
            }
        }
    }

    public void compile(final ConditionalRuleType conditional) {
        this.compile(conditional.getSubject());
        this.compile(conditional.getObject());
        OWLObjectProperty subProperty =
                this.api.createOWLObjectProperty(
                        this.namespace, conditional.getRelation().getRelationName());
        OWLObjectProperty superProperty =
                this.api.createOWLObjectProperty(
                        this.namespace, conditional.getRelation2().getRelationName());
        this.api.addSubPropertyOfAxiom(subProperty, superProperty);
    }

    public void compile(final OnlyCanRuleType onlycan) {
        LOG.trace("ID " + id + ": " + "compiling OnlyCanRuleType ...");
        final OWLClassExpression subjectConceptExpression = this.compile(onlycan.getSubject());
        OWLClassExpression object = this.compile(onlycan.getObject().getExpression().getConcept());
        OWLObjectProperty _createOWLObjectProperty =
                this.api.createOWLObjectProperty(
                        this.namespace,
                        onlycan.getObject().getExpression().getRelation().getRelationName());
        final EList<OrObjectConceptExpression> objectOrList = onlycan.getObject().getObjectOrList();
        final ArrayList<OWLClassExpression> listResult = new ArrayList<>();
        listResult.add(object);
        for (final OrObjectConceptExpression o : objectOrList) {
            {
                final OWLClassExpression result = this.compile(o.getExpression().getConcept());
                listResult.add(result);
            }
        }
        object = this.api.createUnion(listResult);
        this.api.addDomainRangeAxiom(subjectConceptExpression, object, _createOWLObjectProperty);
    }

    public void compile(final CanOnlyRuleType canonly, final ConceptExpression subject) {
        LOG.trace("ID " + id + ": " + "compiling CanOnlyRuleType ...");
        final OWLClassExpression subjectConceptExpression = this.compile(subject);
        OWLClassExpression object = this.compile(canonly.getObject().getExpression().getConcept());
        OWLObjectProperty _createOWLObjectProperty =
                this.api.createOWLObjectProperty(
                        this.namespace,
                        canonly.getObject().getExpression().getRelation().getRelationName());
        final EList<AndObjectConceptExpression> objectAndList =
                canonly.getObject().getObjectAndList();
        final EList<OrObjectConceptExpression> objectOrList = canonly.getObject().getObjectOrList();
        final ArrayList<OWLClassExpression> listResult = new ArrayList<>();
        object = this.api.createOnlyRestriction(_createOWLObjectProperty, object);
        listResult.add(object);
        for (final AndObjectConceptExpression o : objectAndList) {
            {
                OWLClassExpression result = this.compile(o.getExpression().getConcept());
                result = this.api.createOnlyRestriction(_createOWLObjectProperty, result);
                listResult.add(result);
            }
        }
        int _size = listResult.size();
        boolean _greaterThan = (_size > 1);
        if (_greaterThan) {
            object = this.api.createIntersection(listResult);
            listResult.clear();
        }
        for (final OrObjectConceptExpression o_1 : objectOrList) {
            {
                OWLClassExpression result = this.compile(o_1.getExpression().getConcept());
                result = this.api.createOnlyRestriction(_createOWLObjectProperty, result);
                listResult.add(result);
            }
        }
        int _size_1 = listResult.size();
        boolean _greaterThan_1 = (_size_1 > 1);
        if (_greaterThan_1) {
            object = this.api.createUnion(listResult);
            listResult.clear();
        }
        this.api.addSubClassAxiom(object, subjectConceptExpression);
    }

    public void compile(final MustRuleType must, final ConceptExpression subject) {
        LOG.trace("ID " + id + ": " + "compiling MustRuleType ... ");
        final OWLClassExpression subjectConceptExpression = this.compile(subject);
        OWLClassExpression object = this.compile(must.getObject().getExpression().getConcept());
        OWLObjectProperty _createOWLObjectProperty =
                this.api.createOWLObjectProperty(
                        this.namespace,
                        must.getObject().getExpression().getRelation().getRelationName());
        final EList<AndObjectConceptExpression> objectAndList = must.getObject().getObjectAndList();
        final EList<OrObjectConceptExpression> objectOrList = must.getObject().getObjectOrList();
        final ArrayList<OWLClassExpression> listResult = new ArrayList<>();
        object = this.api.createSomeValuesFrom(_createOWLObjectProperty, object);
        listResult.add(object);
        for (final AndObjectConceptExpression o : objectAndList) {
            {
                OWLClassExpression result = this.compile(o.getExpression().getConcept());
                result = this.api.createSomeValuesFrom(_createOWLObjectProperty, result);
                listResult.add(result);
            }
        }
        int _size = listResult.size();
        boolean _greaterThan = (_size > 1);
        if (_greaterThan) {
            object = this.api.createIntersection(listResult);
            listResult.clear();
        }
        for (final OrObjectConceptExpression o_1 : objectOrList) {
            {
                OWLClassExpression result = this.compile(o_1.getExpression().getConcept());
                result = this.api.createSomeValuesFrom(_createOWLObjectProperty, result);
                listResult.add(result);
            }
        }
        int _size_1 = listResult.size();
        boolean _greaterThan_1 = (_size_1 > 1);
        if (_greaterThan_1) {
            object = this.api.createUnion(listResult);
            listResult.clear();
        }
        this.api.addSubClassAxiom(object, subjectConceptExpression);
    }

    public OWLClassExpression compile(final ObjectConceptExpression object) {
        LOG.trace("ID " + id + ": " + "compiling ObjectConceptExpression ...");
        OWLObjectProperty _createOWLObjectProperty =
                this.api.createOWLObjectProperty(
                        this.namespace, object.getRelation().getRelationName());
        final OWLClassExpression concept = this.compile(object.getConcept());
        final int count = object.getNumber();
        final RuleTypeStorageSingleton typeStorage = RuleTypeStorageSingleton.getInstance();
        String _cardinality = object.getCardinality();
        boolean _equals = Objects.equal(_cardinality, "at-most");
        if (_equals) {
            typeStorage.storeTypeOfRule(id, RuleType.AT_MOST);
            return this.api.createMaxCardinalityRestrictionAxiom(
                    concept, _createOWLObjectProperty, count);
        } else {
            String _cardinality_1 = object.getCardinality();
            boolean _equals_1 = Objects.equal(_cardinality_1, "at-least");
            if (_equals_1) {
                typeStorage.storeTypeOfRule(id, RuleType.AT_LEAST);
                return this.api.createMinCardinalityRestrictionAxiom(
                        concept, _createOWLObjectProperty, count);
            } else {
                String _cardinality_2 = object.getCardinality();
                boolean _equals_2 = Objects.equal(_cardinality_2, "exactly");
                if (_equals_2) {
                    typeStorage.storeTypeOfRule(id, RuleType.EXACTLY);
                    return this.api.createExactCardinalityRestrictionAxiom(
                            concept, _createOWLObjectProperty, count);
                } else {
                    return this.api.createSomeValuesFrom(_createOWLObjectProperty, concept);
                }
            }
        }
    }

    public OWLClassExpression compile(final ConceptExpression conceptExpression) {
        LOG.trace("ID " + id + ": " + "compiling ConceptExpression ... ");
        final OWLClass conceptAsOWL =
                this.api.createOWLClass(
                        this.namespace, conceptExpression.getConcept().getConceptName());
        OWLClassExpression result = conceptAsOWL;
        final EList<ThatExpression> thatList = conceptExpression.getThat();
        boolean _isEmpty = thatList.isEmpty();
        if (_isEmpty) {
            return result;
        } else {
            final ThatExpression that = thatList.get(0);
            result = this.compile(that);
            ArrayList<OWLClassExpression> elements = new ArrayList<>();
            elements.add(conceptAsOWL);
            elements.add(result);
            result = this.api.createIntersection(elements);
            return result;
        }
    }

    public OWLClassExpression compile(final ThatExpression that) {
        LOG.trace("ID " + id + ": " + "compiling ThatExpression ...");
        ArrayList<OWLClassExpression> results = new ArrayList<>();
        EList<StatementList> _list = that.getList();
        for (final StatementList statements : _list) {
            {
                final EObject expression = statements.getExpression();
                if (expression instanceof ConceptExpression) {
                    Relation _relation = statements.getRelation();
                    final ObjectRelation relation = ((ObjectRelation) _relation);
                    OWLObjectProperty _createOWLObjectProperty =
                            this.api.createOWLObjectProperty(
                                    this.namespace, relation.getRelationName());
                    final OWLClassExpression owlexpression =
                            this.compile((ConceptExpression) expression);
                    OWLClassExpression result =
                            this.api.createSomeValuesFrom(_createOWLObjectProperty, owlexpression);
                    results.add(result);
                } else {
                    if (expression instanceof DataStatement) {
                        LOG.trace(statements.getRelation());
                        Relation _relation_1 = statements.getRelation();
                        final DatatypeRelation relation_1 = ((DatatypeRelation) _relation_1);
                        OWLDataProperty _createOWLDatatypeProperty =
                                this.api.createOWLDatatypeProperty(
                                        this.namespace, relation_1.getRelationName());
                        final String dataString = ((DataStatement) expression).getStringValue();
                        if (dataString != null) {
                            final OWLDataHasValue dataHasValue =
                                    this.api.createDataHasValue(
                                            dataString, _createOWLDatatypeProperty);
                            results.add(dataHasValue);
                        } else {
                            final OWLDataHasValue dataHasValue_1 =
                                    this.api.createDataHasValue(
                                            ((DataStatement) expression).getIntValue(),
                                            _createOWLDatatypeProperty);
                            results.add(dataHasValue_1);
                        }
                    } else {
                        if (expression instanceof VariableStatement) {
                            LOG.trace("with Variable");
                            return null;
                        }
                    }
                }
            }
        }
        return this.api.createIntersection(results);
    }

    public void compile(final FactStatement fact) {
        LOG.trace("ID " + id + ": " + "compiling FactStatement ...");
        EObject _assertion = fact.getAssertion();
        if (_assertion instanceof ConceptAssertion) {
            EObject _assertion_1 = fact.getAssertion();
            this.compile((ConceptAssertion) _assertion_1);
        } else {
            EObject _assertion_2 = fact.getAssertion();
            if (_assertion_2 instanceof RoleAssertion) {
                EObject _assertion_3 = fact.getAssertion();
                this.compile(((RoleAssertion) _assertion_3));
            }
        }
    }

    public void compile(final ConceptAssertion fact) {
        LOG.trace("ID " + id + ": " + "compiling ConceptAssertion...");
        OWLNamedIndividual individual =
                this.api.createNamedIndividual(this.namespace, fact.getIndividual());
        OWLClass concept =
                this.api.createOWLClass(this.namespace, fact.getConcept().getConceptName());
        this.api.addClassAssertionAxiom(individual, concept);
    }

    public void compile(final RoleAssertion fact) {
        LOG.trace("ID " + id + ": " + "compiling RoleAssertion...");
        OWLNamedIndividual individual =
                this.api.createNamedIndividual(this.namespace, fact.getIndividual());
        if (fact instanceof ObjectPropertyAssertion) {
            this.compile((ObjectPropertyAssertion) fact);
        } else {
            if (fact instanceof DatatypePropertyAssertion) {
                this.compile((DatatypePropertyAssertion) fact);
            }
        }
    }

    public void compile(final ObjectPropertyAssertion fact) {
        OWLNamedIndividual individual =
                this.api.createNamedIndividual(this.namespace, fact.getIndividual());
        OWLObjectProperty relation =
                this.api.createOWLObjectProperty(
                        this.namespace, fact.getRelation().getRelationName());
        OWLNamedIndividual otherIndividual =
                this.api.createNamedIndividual(this.namespace, fact.getIndividual2());
        this.api.addObjectPropertyAssertion(individual, relation, otherIndividual);
    }

    public void compile(final DatatypePropertyAssertion fact) {
        OWLNamedIndividual individual =
                this.api.createNamedIndividual(this.namespace, fact.getIndividual());
        OWLDataProperty relation =
                this.api.createOWLDatatypeProperty(
                        this.namespace, fact.getRelation().getRelationName());
        String _stringValue = fact.getStringValue();
        boolean _tripleNotEquals = (_stringValue != null);
        if (_tripleNotEquals) {
            this.api.addClassAssertionAxiom(
                    individual, this.api.createDataHasValue(fact.getStringValue(), relation));
        } else {
            this.api.addClassAssertionAxiom(
                    individual, this.api.createDataHasValue(fact.getIntValue(), relation));
        }
    }
}
