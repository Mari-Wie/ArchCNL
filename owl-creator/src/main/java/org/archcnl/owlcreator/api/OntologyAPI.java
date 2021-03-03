package org.archcnl.owlcreator.api;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLProperty;

import java.util.ArrayList;

/**
 * Interface for creating and filling OWL 2.0 ontologies with
 * the org.semanticweb.owlapi library.
 */
public interface OntologyAPI {
	/**
	 * Creates a new OWL ontology. Must be called before any other method which adds something to an ontology.
	 * @param filePath The path to the file in which the ontology will be stored (in XML format).
	 * @param iriPath The IRI (international resource modifier) path/prefix of this ontology.
	 */
	public void createOntology(String filePath, String iriPath);
	
	/**
	 * The current ontology cannot be added after calling this method. This method is also closing
	 * internal resources, so make sure that you call it when you are done creating your ontology.
	 * To create a new ontology, call {@link #createOntology(String, String)} again. No method of this
	 * interface which adds something to an ontology 
	 * must be called until a new ontology has been created.
	 */
	public void removeOntology();

	/**
	 * Adds an OWL axiom to this ontology which states that the OWL class "subClass" is the sub class of 
	 * @param superClass the super class
	 * @param subClass the sub class
	 */
	public void addSubClassAxiom(OWLClassExpression superClass, OWLClassExpression subClass);

	/**
	 * Adds an axiom to the ontology which states that the two given OWL object properties are in a sub/super-type
	 * relation. 
	 * 
	 * @param subProperty The "sub" property, i.e. which is included by the other one.
	 * @param superProperty The "super" property, i.e. which includes the other one.
	 */
	public void addSubPropertyOfAxiom(OWLObjectProperty subProperty, OWLObjectProperty superProperty);
	
	/**
	 * Saves the current state of the ontology in two files. The first one is the one specified by
	 * the filePath passed to {@link #createOntology(String, String)} and stores the ontology in 
	 * OWL/XML format. The second one is stored in RDF/XML format. Its filename is derived from the 
	 * filename of the first file.
	 */
	public void triggerSave();

	/**
	 * Adds axioms to the ontology, which state that the domain of the given property is the 
	 * given subject class and that its range is the given object class. Note that this is not
	 * a "constraint" on the property but rather a statement used during inference.
	 * @param subject The class of legitimate subjects.
	 * @param object The class of legitimate objects.
	 * @param property The property which is defined/restricted.
	 */
	public void addDomainRangeAxiom(OWLClassExpression subject, OWLClassExpression object, OWLObjectProperty property);

	/**
	 * Returns an OWL class expression which represents the set of individuals which have only "role" properties 
	 * where the object is from the class "concept". Example: role="makes-sound" concept="Bark" -> a class of objects
	 * which can only make "Bark" sounds.
	 * @param role The property used in the expression.
	 * @param concept The class of objects used in the expression.
	 * @return The class expression.
	 */
	public OWLClassExpression createOnlyRestriction(OWLObjectProperty role, OWLClassExpression concept);

	/**
	 * Returns an OWL class with the given name from the given namespace (IRI).
	 * @param iriPath The international resource identifier (IRI) of the class. 
	 * @param subjectName The name of the OWL class.
	 * @return An OWL class with the name "<IRI>#<classname>".
	 */
	public OWLClass createOWLClass(String iriPath, String subjectName);
	
	/**
	 * Returns an OWL object property (property between two indivuduals/classes and not between an
	 * individual and a literal) with the given name. However, the given property name is lemmatized before use.
	 * @param iriPath The international resource identifier (IRI) of the property.
	 * @param roleName The name of the property.
	 * @return The property with the name "<IRI>#<lemmatized-property-name>",
	 */
	public OWLObjectProperty createOWLObjectProperty(String iriPath, String roleName);
	
	/**
	 * Returns an OWL datatype property (property between an individual and a literal and not
	 * between two indivuduals/classes) with the given name.
	 * @param iriPath The international resource identifier (IRI) of the property.
	 * @param roleName The name of the property.
	 * @return The property with the name "<IRI>#<property-name>",
	 */
	public OWLDataProperty createOWLDatatypeProperty(String iriPath, String roleName);
	
	/**
	 * Returns an OWL restriction which states that the given OWL data property must have the
	 * specified (string) value.
	 * @param value The required value of the data property.
	 * @param role The data property which is restricted.
	 * @return The restriction.
	 */
	public OWLDataHasValue createDataHasValue(String value, OWLDataProperty role);
	
	/**
	 * Returns an OWL restriction which states that the given OWL data property must have the
	 * specified (integer) value.
	 * @param value The required value of the data property.
	 * @param role The data property which is restricted.
	 * @return The restriction.
	 */
	public OWLDataHasValue createDataHasValue(int value, OWLDataProperty roleName);
	
	/**
	 * Returns an OWL restriction which states that the given OWL property "role" must exist at least once with an object
	 * from the OWL class "expression"
	 * @param role The property which is restricted.
	 * @param expression The class of the objects to which the restriction applies.
	 * @return The restriction.
	 */
	public OWLClassExpression createSomeValuesFrom(OWLObjectProperty role, OWLClassExpression expression);
	
	/**
	 * Returns an OWL class which represents the intersection of the given classes.
	 * @param expressions The list of OWL class expressions which are intersected.
	 * @return A new class description representing the intersection of the input classes.
	 */
	public OWLObjectIntersectionOf createIntersection(ArrayList<OWLClassExpression> expressions);
	
	/**
	 * Returns an OWL class which represents the union of the given classes.
	 * @param expressions The list of OWL class expressions which are united.
	 * @return A new class description representing the union of the input classes.
	 */
	public OWLClassExpression createUnion(ArrayList<OWLClassExpression> expressions);

	/**
	 * Adds an axiom to the ontology which states that the given property does not apply to the "subject" 
	 * class when the object is from the "object" class. Example: subject="Cat" property="makes-sound" object="Bark" 
	 * adds an axiom which implies that (if no other axioms are present) everything which does not make a sound from
	 * the class "Bark" can be considered as a "Cat".
	 * @param subject The class to which the axiom applies.
	 * @param object The object class used in the axiom.
	 * @param property The property used in the axiom.
	 */
	public void addNegationAxiom(OWLClassExpression subject, OWLClassExpression object, OWLObjectProperty property);

	/**
	 * Adds an axiom to the ontology which states that the given classes are disjoint, i.e. that
	 * it is a subset of the complement of the second class.
	 * @param first The first class, i.e. the class to which the axiom applies.
	 * @param second The second class, i.e. the class which is used in the axiom.
	 */
	public void addDisjointAxiom(OWLClassExpression first, OWLClassExpression second);

	/**
	 * Returns the "topmost" OWL class "Thing", which is the superclass of every OWL class.
	 * @return The corresponding OWL class.
	 */
	public OWLClassExpression getOWLTop();
	
	/**
	 * Returns an OWL class expression which states that the given property "relation" must be present at most "count" times for the
	 * instances of the OWL class "object".
	 * @param object The object to which the constraint applies.
	 * @param relation The property to which the constrain applies.
	 * @param count The upper bound on the cardinality.
	 * @return The corresponding class expression.
	 */
	public OWLClassExpression createMaxCardinalityRestrictionAxiom(OWLClassExpression object, OWLObjectProperty relation, int count);
	
	/**
	 * Returns an OWL class expression which states that the given property "relation" must be present at least "count" times for the
	 * instances of the OWL class "object".
	 * @param object The object to which the constraint applies.
	 * @param relation The property to which the constrain applies.
	 * @param count The lower bound on the cardinality.
	 * @return The corresponding class expression.
	 */
	public OWLClassExpression createMinCardinalityRestrictionAxiom(OWLClassExpression object, OWLObjectProperty relation, int count);
	
	/**
	 * Returns an OWL class expression which states that the given property "relation" must be present exactly "count" times for the
	 * instances of the OWL class "object".
	 * @param object The object to which the constraint applies.
	 * @param relation The property to which the constrain applies.
	 * @param count The exact value of the cardinality.
	 * @return The corresponding class expression.
	 */
	public OWLClassExpression createExactCardinalityRestrictionAxiom(OWLClassExpression object, OWLObjectProperty relation, int count);

	/**
	 * Returns an OWL named individual with the given name in the given namespace.
	 * @param iriPath the namespace/name prefix
	 * @param individualName the name of the individual
	 */
	public OWLNamedIndividual createNamedIndividual(String iriPath, String individualName);
	
	/**
	 * Adds an axiom which states that the given individual belongs to the given class.
	 * @param individual The concerned OWL named individual.
	 * @param concept The concerned OWL class.
	 */
	public void addClassAssertionAxiom(OWLNamedIndividual individual, OWLClassExpression concept);
	
	/**
	 * Adds an axiom which states that the given individuals are in the given relation.
	 * @param individual The individual acting as the subject.
	 * @param relation The relation between the individuals.
	 * @param otherIndividual The individual acting as the object.
	 */
	public void addObjectPropertyAssertion(OWLNamedIndividual individual, 
            OWLObjectProperty relation, OWLNamedIndividual otherIndividual);
}
