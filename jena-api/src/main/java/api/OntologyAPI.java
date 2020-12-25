package api;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLProperty;

import java.util.ArrayList;

// TODO: This interface enforces a dependency to org.semanticweb.owlapi on its clients.
//		 Moreover, the implementing class OWLAPIImpl does nothing than passing the parameters of its
//		 methods to this library. Thus, the clients could use the library directly. This would remove
//		 some code to maintain. (And a class with a very ugly name - OWLAPIImpl.)
/**
 * Interface for creating and filling OWL 2.0 ontologies with
 * the org.semanticweb.owlapi library.
 */
public interface OntologyAPI {
	// TODO: fix naming mess with "addXY" methods that just return something without modifying the ontology 
	// -> rename them to "getXY", "createXY", or something similar
	
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
	 * @param iriPath unused
	 */
	public void removeOntology(String iriPath);

	/**
	 * Adds an OWL axiom to this ontology which states that the OWL class "subClass" is the sub class of 
	 * the OWL class "superClass". The ontology is subsequently saved by calling {@link #triggerSave()}.
	 * @param iriPath unused
	 * @param superClass the super class
	 * @param subClass the sub class
	 */
	public void addSubClassAxiom(String iriPath, OWLClassExpression superClass, OWLClassExpression subClass);

	// TODO: This method seems very error-prone due to its restricted and "strange" interface/signature.
	/**
	 * Adds an axiom to the ontology which states that the two given OWL OBJECT(!) properties are in a sub/super-type
	 * relation. Datatype properties are NOT viewed by this operation. It is currently unclear whether this is
	 * intended or not. 
	 * The ontology is subsequently saved by calling {@link #triggerSave()}.
	 * 
	 * @param iriPath The international resource identifier (IRI) of BOTH object properties.
	 * @param subProperty The name (missing the IRI) of the "sub" property, i.e. which is included by the other one.
	 * @param superProperty The name (missing the IRI) of the "super" property, i.e. which includes the other one.
	 */
	public void addSubPropertyOfAxiom(String iriPath, String subProperty, String superProperty);

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
	 * Saves the ontology by calling {@link #triggerSave()}.
	 * @param namespace unused
	 * @param subject The class of legitimate subjects.
	 * @param object The class of legitimate objects.
	 * @param property The property which is defined/restricted.
	 */
	public void addDomainRangeAxiom(String namespace, OWLClassExpression subject, OWLClassExpression object, OWLObjectProperty property);

	// TODO: The prefix "create" messes the naming even more up than it already is. Rename method, I choose you!
	/**
	 * Returns an OWL class expression which represents the set of individuals which have only "role" properties 
	 * where the object is from the class "concept". Example: role="makes-sound" concept="Bark" -> a class of objects
	 * which can only make "Bark" sounds.
	 * @param iriPath unused
	 * @param role The property used in the expression.
	 * @param concept The class of objects used in the expression.
	 * @return The class expression.
	 */
	public OWLClassExpression createOnlyRestriction(String iriPath, OWLObjectProperty role, OWLClassExpression concept);

	/**
	 * Returns an OWL class with the given name from the given namespace (IRI).
	 * @param iriPath The international resource identifier (IRI) of the class. 
	 * @param subjectName The name of the OWL class.
	 * @return An OWL class with the name "<IRI>#<classname>".
	 */
	public OWLClass getOWLClass(String iriPath, String subjectName);
	
	/**
	 * Returns an OWL object property (property between two indivuduals/classes and not between an
	 * individual and a literal) with the given name. However, the given property name is lemmatized before use.
	 * @param iriPath The international resource identifier (IRI) of the property.
	 * @param roleName The name of the property.
	 * @return The property with the name "<IRI>#<lemmatized-property-name>",
	 */
	public OWLProperty getOWLObjectProperty(String iriPath, String roleName);
	
	/**
	 * Returns an OWL datatype property (property between an individual and a literal and not
	 * between two indivuduals/classes) with the given name.
	 * @param iriPath The international resource identifier (IRI) of the property.
	 * @param roleName The name of the property.
	 * @return The property with the name "<IRI>#<property-name>",
	 */
	public OWLProperty getOWLDatatypeProperty(String iriPath, String roleName);
	
	/**
	 * Returns an OWL restriction which states that the given OWL data property must have the
	 * specified (string) value.
	 * @param iriPath not used
	 * @param value The required value of the data property.
	 * @param role The data property which is restricted.
	 * @return The restriction.
	 */
	public OWLDataHasValue addDataHasValue(String iriPath, String value, OWLDataProperty role);
	
	/**
	 * Returns an OWL restriction which states that the given OWL data property must have the
	 * specified (integer) value.
	 * @param iriPath not used
	 * @param value The required value of the data property.
	 * @param role The data property which is restricted.
	 * @return The restriction.
	 */
	public OWLDataHasValue addDataHasIntegerValue(String iriPath, int value, OWLDataProperty roleName);
	
//	public void addSomeValuesFrom(String iriPath, OWLClassExpression subclass, OWLClassExpression superclass, OWLObjectProperty roleName);
	
	/**
	 * Returns an OWL restriction which states that the given OWL property "role" must exist at least once with an object
	 * from the OWL class "expression"
	 * @param iriPath unused
	 * @param role The property which is restricted.
	 * @param expression The class of the objects to which the restriction applies.
	 * @return The restriction.
	 */
	public OWLClassExpression addSomeValuesFrom(String iriPath, OWLObjectProperty role, OWLClassExpression expression);
	
	/**
	 * Returns an OWL class which represents the intersection of the given classes.
	 * @param iriPath unused
	 * @param first the first class
	 * @param second the second class
	 * @return A new class description representing the intersection of the input classes.
	 */
	public OWLObjectIntersectionOf  intersectionOf(String iriPath, OWLClassExpression first, OWLClassExpression second);

	// TODO: change parameter to ArrayList (so that this method is similar to unionOf())
	/**
	 * Returns an OWL class which represents the intersection of the given classes.
	 * @param iriPath unused
	 * @param expressions The list of OWL class expressions which are intersected.
	 * @return A new class description representing the intersection of the input classes.
	 */
	public OWLObjectIntersectionOf  intersectionOf(String iriPath, OWLClassExpression[] list);
	
	/**
	 * Returns an OWL class which represents the union of the given classes.
	 * @param iriPath unused
	 * @param expressions The list of OWL class expressions which are united.
	 * @return A new class description representing the union of the input classes.
	 */
	public OWLClassExpression unionOf(String iriPath, ArrayList<OWLClassExpression> expressions);

	/**
	 * Adds an axiom to the ontology which states that the given property does not apply to the "subject" 
	 * class when the object is from the "object" class. Example: subject="Cat" property="makes-sound" object="Bark" 
	 * adds an axiom which implies that (if no other axioms are present) everything which does not make a sound from
	 * the class "Bark" can be considered as a "Cat".
	 * The ontology is saved after the modification by calling {@link #triggerSave()}.
	 * @param string unused
	 * @param subject The class to which the axiom applies.
	 * @param object The object class used in the axiom.
	 * @param property The property used in the axiom.
	 */
	public void addNegationAxiom(String string, OWLClassExpression subject, OWLClassExpression object, OWLObjectProperty property);

	/**
	 * Adds an axiom to the onology which states that the first class is not contained in the second class, i.e. that
	 * it is a subset of the complement of the second class.
	 * The ontology is saved after the modification by calling {@link #triggerSave()}.
	 * @param string unused
	 * @param first The first class, i.e. the class to which the axiom applies.
	 * @param second The second class, i.e. the class which is used in the axiom.
	 */
	public void addNegationAxiom(String string, OWLClassExpression first, OWLClassExpression second);

	/**
	 * Returns the "topmost" OWL class "Thing", which is the superclass of every OWL class.
	 * @param string unused
	 * @return The corresponding OWL class.
	 */
	public OWLClassExpression getOWLTop(String string);
	
	/**
	 * Returns an OWL class expression which states that the given property "relation" must be present at most "count" times for the
	 * instances of the OWL class "object".
	 * @param namespace unused
	 * @param object The object to which the constraint applies.
	 * @param relation The property to which the constrain applies.
	 * @param count The upper bound on the cardinality.
	 * @return The corresponding class expression.
	 */
	public OWLClassExpression addMaxCardinalityRestrictionAxiom(String namespace, OWLClassExpression object, OWLObjectProperty relation, int count);
	
	/**
	 * Returns an OWL class expression which states that the given property "relation" must be present at least "count" times for the
	 * instances of the OWL class "object".
	 * @param namespace unused
	 * @param object The object to which the constraint applies.
	 * @param relation The property to which the constrain applies.
	 * @param count The lower bound on the cardinality.
	 * @return The corresponding class expression.
	 */
	public OWLClassExpression addMinCardinalityRestrictionAxiom(String namespace, OWLClassExpression object, OWLObjectProperty relation, int count);
	
	/**
	 * Returns an OWL class expression which states that the given property "relation" must be present exactly "count" times for the
	 * instances of the OWL class "object".
	 * @param namespace unused
	 * @param object The object to which the constraint applies.
	 * @param relation The property to which the constrain applies.
	 * @param count The exact value of the cardinality.
	 * @return The corresponding class expression.
	 */
	public OWLClassExpression addExactCardinalityRestrictionAxiom(String namespace, OWLClassExpression object, OWLObjectProperty relation, int count);

}
