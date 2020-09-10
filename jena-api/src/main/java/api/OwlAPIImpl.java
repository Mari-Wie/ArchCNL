package api;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import api.axioms.ConditionalAxiom;
import api.axioms.CustomAxiom;
import api.axioms.DomainAxiom;
import api.axioms.ExactCardinalityAxiom;
import api.axioms.ExistentialAxiom;
import api.axioms.MaxCardinalityRestrictionAxiom;
import api.axioms.MinCardinalityRestrictionAxiom;
import api.axioms.NegationAxiomWithUnion;
import api.axioms.RangeAxiom;
import api.axioms.SubConceptAxiom;
import api.axioms.UniversalAxiom;
import stemmer.StanfordLemmatizer;

public class OwlAPIImpl implements OWLAPI {

	private OWLOntology currentOntology;
	private OWLDataFactory df = OWLManager.getOWLDataFactory();
	OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
	private String filePath;

	private Integer id = 0;

	private List<String> _numbers;

	private static Map<CustomAxiom, Integer> axioms;

	public OwlAPIImpl() {

		axioms = new HashMap<CustomAxiom, Integer>();

		_numbers = new ArrayList<String>();
		_numbers.add("no");
		_numbers.add("one");
		_numbers.add("two");
		_numbers.add("three");
		_numbers.add("four");
		_numbers.add("five");
		_numbers.add("six");
		_numbers.add("seven");
		_numbers.add("eight");
		_numbers.add("nine");
		_numbers.add("ten");

	}

	public void createOntology(String filePath, String iriPath) {

		this.filePath = filePath;
		try {
			IRI iri = IRI.create(iriPath);
			currentOntology = manager.createOntology(iri);
			File output = new File(filePath);
			// Output will be deleted on exit; to keep temporary file replace
			// previous line with the following
			// File output = File.createTempFile("saved_pizza", ".owl");
			IRI documentIRI2 = IRI.create(output);
			// save in OWL/XML format
			manager.saveOntology(currentOntology, new OWLXMLDocumentFormat(), documentIRI2);
			// save in RDF/XML
			manager.saveOntology(currentOntology, documentIRI2);
			// print out the ontology
			// StringDocumentTarget target = new StringDocumentTarget();
			// manager.saveOntology(currentOntology, target);
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OWLOntologyStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Map<CustomAxiom, Integer> getAxioms() {
		return axioms;
	}

	public void addOntologyClass(String iriPath, String className) {
		OWLClass aClass = df.getOWLClass(IRI.create(iriPath + "#" + className));
		OWLDeclarationAxiom axiom = df.getOWLDeclarationAxiom(aClass);
		manager.addAxiom(currentOntology, axiom);
	}

	public void addSubClassAxiom(String iriPath, String superClass, String subClass) {

		OWLClass a = df.getOWLClass(iriPath + "#" + subClass);
		OWLClass b = df.getOWLClass(iriPath + "#" + superClass);
		OWLAxiom subClassAxiom = df.getOWLSubClassOfAxiom(a, b);
		manager.addAxiom(currentOntology, subClassAxiom);

		CustomAxiom axiom = new SubConceptAxiom(iriPath + "#" + subClass, iriPath + "#" + superClass);
		axioms.put(axiom, id);

		triggerSave();

		id++;
	}

	public void addSubClassAxiom(String iriPath, OWLClassExpression superClass, OWLClassExpression subClass) {

		OWLAxiom subClassAxiom = df.getOWLSubClassOfAxiom(subClass, superClass);
		manager.addAxiom(currentOntology, subClassAxiom);

//		System.out.println(subClassAxiom);
		CustomAxiom axiom = new SubConceptAxiom(iriPath + "#" + subClass, iriPath + "#" + superClass);
		axioms.put(axiom, id);

		triggerSave();

	}

	public void addObjectProperty(String iriPath, String propertyName) {

		OWLObjectProperty property = df
				.getOWLObjectProperty(IRI.create(iriPath + "#" + lemmatizeProperty(propertyName)));
		OWLAxiom axiom = df.getOWLDeclarationAxiom(property);
		manager.addAxiom(currentOntology, axiom);
		triggerSave();
	}

	private String lemmatizeProperty(String propertyName) {
		StanfordLemmatizer slem = new StanfordLemmatizer();
		List<String> result = slem.lemmatize(propertyName);
		return result.get(0);
	}

	public void addDataProperty(String iriPath, String propertyName) {
	}

	public void addMaxCardinalityRestrictionAxiom(String iriPath, String subjectName, String objectName,
			String propertyName, String count) {

		OWLClass subject = df.getOWLClass(iriPath + "#" + subjectName);
		OWLClass object = df.getOWLClass(iriPath + "#" + objectName);
		OWLObjectProperty property = df.getOWLObjectProperty(iriPath + "#" + lemmatizeProperty(propertyName));
		int number = parseCount(count);
		OWLObjectMaxCardinality maxCardinality = df.getOWLObjectMaxCardinality(number, property, object);
		OWLAxiom subClassAxiom = df.getOWLSubClassOfAxiom(subject, maxCardinality);
		manager.addAxiom(currentOntology, subClassAxiom);

		CustomAxiom axiom = new MaxCardinalityRestrictionAxiom(iriPath, subjectName, objectName,
				lemmatizeProperty(propertyName), count);
		axioms.put(axiom, id);

		triggerSave();

		id++;

	}

	public void addMaxCardinalityRestrictionAxiom(String iriPath, OWLClassExpression subject, OWLClassExpression object,
			OWLObjectProperty property, int count) {

		OWLObjectMaxCardinality maxCardinality = df.getOWLObjectMaxCardinality(count, property, object);
		OWLAxiom subClassAxiom = df.getOWLSubClassOfAxiom(subject, maxCardinality);
		manager.addAxiom(currentOntology, subClassAxiom);

		triggerSave();

	}

	public OWLClassExpression addMaxCardinalityRestrictionAxiom(String namespace, OWLClassExpression object,
			OWLObjectProperty property, int count) {
		return df.getOWLObjectMaxCardinality(count, property, object);
	}

	public OWLClassExpression addMinCardinalityRestrictionAxiom(String namespace, OWLClassExpression object,
			OWLObjectProperty property, int count) {
		return df.getOWLObjectMinCardinality(count, property, object);
	}

	public OWLClassExpression addExactCardinalityRestrictionAxiom(String namespace, OWLClassExpression object,
			OWLObjectProperty property, int count) {
		return df.getOWLObjectExactCardinality(count, property, object);
	}

	public void addMinCardinalityRestrictionAxiom(String iriPath, OWLClassExpression subject, OWLClassExpression object,
			OWLObjectProperty property, int count) {

		OWLObjectMinCardinality maxCardinality = df.getOWLObjectMinCardinality(count, property, object);
		OWLAxiom subClassAxiom = df.getOWLSubClassOfAxiom(subject, maxCardinality);
		manager.addAxiom(currentOntology, subClassAxiom);

		triggerSave();

	}

	public void addExactCardinalityRestrictionAxiom(String iriPath, OWLClassExpression subject,
			OWLClassExpression object, OWLObjectProperty property, int count) {

		OWLObjectExactCardinality maxCardinality = df.getOWLObjectExactCardinality(count, property, object);
		OWLAxiom subClassAxiom = df.getOWLSubClassOfAxiom(subject, maxCardinality);
		manager.addAxiom(currentOntology, subClassAxiom);

		triggerSave();

	}

	public void addMinCardinalityRestrictionAxiom(String iriPath, String subjectName, String objectName,
			String propertyName, String count) {

		OWLClass subject = df.getOWLClass(iriPath + "#" + subjectName);
		OWLClass object = df.getOWLClass(iriPath + "#" + objectName);
		OWLObjectProperty property = df.getOWLObjectProperty(iriPath + "#" + lemmatizeProperty(propertyName));
		int number = parseCount(count);
		OWLObjectMinCardinality maxCardinality = df.getOWLObjectMinCardinality(number, property, object);
		OWLAxiom subClassAxiom = df.getOWLSubClassOfAxiom(subject, maxCardinality);
		manager.addAxiom(currentOntology, subClassAxiom);

		CustomAxiom axiom = new MinCardinalityRestrictionAxiom(iriPath, subjectName, objectName,
				lemmatizeProperty(propertyName), count);
		axioms.put(axiom, id);

		triggerSave();

		id++;

	}

	private int parseCount(String count) {
		if (_numbers.contains(count)) {
			return _numbers.indexOf(count);
		}
		return Integer.parseInt(count);
	}

	public void triggerSave() {
		File output = new File(this.filePath);
		IRI documentIRI2 = IRI.create(output);
		// save in OWL/XML format
		try {
			manager.saveOntology(currentOntology, new OWLXMLDocumentFormat(), documentIRI2);
			// save in RDF/XML
			manager.saveOntology(currentOntology, documentIRI2);
		} catch (OWLOntologyStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addExactCardinalityRestrictionAxiom(String iriPath, String subjectName, String objectName,
			String propertyName, String count) {

		OWLClass subject = df.getOWLClass(iriPath + "#" + subjectName);
		OWLClass object = df.getOWLClass(iriPath + "#" + objectName);
		OWLObjectProperty property = df.getOWLObjectProperty(iriPath + "#" + lemmatizeProperty(propertyName));
		int number = parseCount(count);
		OWLObjectExactCardinality exactCardinality = df.getOWLObjectExactCardinality(number, property, object);
		OWLAxiom subClassAxiom = df.getOWLSubClassOfAxiom(subject, exactCardinality);
		manager.addAxiom(currentOntology, subClassAxiom);

		CustomAxiom axiom = new ExactCardinalityAxiom(iriPath, subjectName, objectName, lemmatizeProperty(propertyName),
				count);

		axioms.put(axiom, id);

		triggerSave();

		id++;
	}

	public void addSomeRestriction(String iriPath, String subjectName, String objectName, String roleName) {

		OWLClass subject = df.getOWLClass(iriPath + "#" + subjectName);
		OWLClass object = df.getOWLClass(iriPath + "#" + objectName);
		OWLObjectProperty property = df.getOWLObjectProperty(iriPath + "#" + roleName);
		OWLObjectSomeValuesFrom someValues = df.getOWLObjectSomeValuesFrom(property, object);
		OWLAxiom subClassAxiom = df.getOWLSubClassOfAxiom(subject, someValues);
		manager.addAxiom(currentOntology, subClassAxiom);

		CustomAxiom axiom = new ExistentialAxiom(iriPath, subjectName, objectName, roleName);

		axioms.put(axiom, id);

		triggerSave();

		id++;
	}

	public OWLClass getOWLClass(String iriPath, String subjectName) {
		OWLClass subject = df.getOWLClass(iriPath + "#" + subjectName);

		return subject;
	}

	public OWLObjectSomeValuesFrom addSomeValuesFrom(String iriPath, String subjectName, String objectName,
			String roleName) {

		OWLClass subject = df.getOWLClass(iriPath + "#" + subjectName);
		OWLClass object = df.getOWLClass(iriPath + "#" + objectName);
		OWLObjectProperty property = df.getOWLObjectProperty(iriPath + "#" + roleName);
		OWLObjectSomeValuesFrom someValues = df.getOWLObjectSomeValuesFrom(property, object);

		triggerSave();

		return someValues;

	}

	public OWLObjectSomeValuesFrom addSomeValuesFrom(String iriPath, String subjectName, OWLClass object,
			String roleName) {

		OWLClass subject = df.getOWLClass(iriPath + "#" + subjectName);
		OWLObjectProperty property = df.getOWLObjectProperty(iriPath + "#" + roleName);
		OWLObjectSomeValuesFrom someValues = df.getOWLObjectSomeValuesFrom(property, object);

		triggerSave();

		return someValues;

	}

	public OWLObjectSomeValuesFrom addSomeValuesFrom(String iriPath, OWLClass object, OWLObjectProperty roleName) {

		OWLObjectSomeValuesFrom someValues = df.getOWLObjectSomeValuesFrom(roleName, object);

		triggerSave();

		return someValues;

	}

	public OWLDataHasValue addDataHasValue(String iriPath, String value, OWLDataProperty roleName) {

		OWLLiteral literal = df.getOWLLiteral(value);
		OWLDataHasValue hasValues = df.getOWLDataHasValue(roleName, literal);

		triggerSave();

		return hasValues;

	}

	public OWLDataHasValue addDataHasIntegerValue(String iriPath, int value, OWLDataProperty roleName) {

		OWLLiteral literal = df.getOWLLiteral(value);
		OWLDataHasValue hasValues = df.getOWLDataHasValue(roleName, literal);

		triggerSave();

		return hasValues;
	}

	public OWLClassExpression addSomeValuesFrom(String iriPath, OWLObjectProperty roleName,
			OWLClassExpression expression) {

		OWLObjectSomeValuesFrom someValues = df.getOWLObjectSomeValuesFrom(roleName, expression);

		triggerSave();

		return someValues;

	}

	public void addSomeValuesFrom(String iriPath, OWLClassExpression subclass, OWLClassExpression superclass,
			OWLObjectProperty role) {

		OWLObjectSomeValuesFrom someValues = df.getOWLObjectSomeValuesFrom(role, superclass);
		OWLSubClassOfAxiom subClassAxiom = df.getOWLSubClassOfAxiom(subclass, someValues);
		manager.addAxiom(currentOntology, subClassAxiom);

		triggerSave();

	}

	public OWLObjectIntersectionOf intersectionOf(String iriPath, String subjectName,
			OWLObjectSomeValuesFrom someValues) {
		OWLClass subject = df.getOWLClass(iriPath + "#" + subjectName);
		return df.getOWLObjectIntersectionOf(subject, someValues);

	}

	public OWLObjectIntersectionOf intersectionOf(String iriPath, OWLClassExpression first, OWLClassExpression second) {
		return df.getOWLObjectIntersectionOf(first, second);

	}

	public OWLObjectIntersectionOf intersectionOf(String iriPath, OWLClassExpression[] list) {
		return df.getOWLObjectIntersectionOf(list);
	}

	public OWLObjectIntersectionOf intersectionOf(String iriPath, OWLClass subject,
			OWLObjectSomeValuesFrom someValues) {
		return df.getOWLObjectIntersectionOf(subject, someValues);

	}

	public void addSubPropertyOfAxiom(String iriPath, String subProperty, String superProperty) {

		OWLObjectProperty sub = df.getOWLObjectProperty(iriPath + "#" + lemmatizeProperty(subProperty));
		OWLObjectProperty sup = df.getOWLObjectProperty(iriPath + "#" + lemmatizeProperty(superProperty));
		OWLAxiom subPropertyAxiom = df.getOWLSubObjectPropertyOfAxiom(sub, sup);

		manager.addAxiom(currentOntology, subPropertyAxiom);
		CustomAxiom axiom = new ConditionalAxiom(iriPath, lemmatizeProperty(subProperty),
				lemmatizeProperty(superProperty));
		axioms.put(axiom, id);

		triggerSave();

		id++;
	}

	public void addDomainRangeAxiom(String iriPath, String subjectName, String objectName, String property) {

		OWLClass subject = df.getOWLClass(iriPath + "#" + subjectName);
		OWLClass object = df.getOWLClass(iriPath + "#" + objectName);
		OWLObjectProperty prop = df.getOWLObjectProperty(iriPath + "#" + property);
		OWLObjectPropertyDomainAxiom domainAxiom = df.getOWLObjectPropertyDomainAxiom(prop, subject);

		OWLObjectPropertyRangeAxiom rangeAxiom = df.getOWLObjectPropertyRangeAxiom(prop, object);
		manager.addAxiom(currentOntology, domainAxiom);
		manager.addAxiom(currentOntology, rangeAxiom);

		// TODO: funktioniert das dann alles noch? Lieber ein DomainRangeAxiom nutzen
		// und dann auf diese beiden referenzieren?
		CustomAxiom axiom = new DomainAxiom(iriPath, property, subjectName);
		CustomAxiom axiom2 = new RangeAxiom(iriPath, property, objectName);

		axioms.put(axiom, id);
		axioms.put(axiom2, id);

		id++;
		triggerSave();
	}

	public void addDomainRangeAxiom(String namespace, OWLClassExpression subject, OWLClassExpression object,
			OWLObjectProperty property) {
		OWLObjectPropertyDomainAxiom domainAxiom = df.getOWLObjectPropertyDomainAxiom(property, subject);

		OWLObjectPropertyRangeAxiom rangeAxiom = df.getOWLObjectPropertyRangeAxiom(property, object);
		manager.addAxiom(currentOntology, domainAxiom);
		manager.addAxiom(currentOntology, rangeAxiom);

		triggerSave();
	}

	public void addNegationAxiomWithUnion(String iriPath, String subjectName, List<String> objects, String property) {

		OWLClass subject = df.getOWLClass(iriPath + "#" + subjectName);
		OWLObjectProperty prop = df.getOWLObjectProperty(iriPath + "#" + lemmatizeProperty(property));

		List<OWLClass> owlObjects = new ArrayList<OWLClass>();
		OWLClass object;
		for (String s : objects) {
			object = df.getOWLClass(iriPath + "#" + s);
			owlObjects.add(object);
		}

		OWLObjectUnionOf owlObjectUnionOf = df.getOWLObjectUnionOf(owlObjects);
		OWLObjectSomeValuesFrom someValues = df.getOWLObjectSomeValuesFrom(prop, owlObjectUnionOf);
		OWLObjectComplementOf complement = df.getOWLObjectComplementOf(someValues);
		OWLAxiom subClass = df.getOWLSubClassOfAxiom(subject, complement);
		manager.addAxiom(currentOntology, subClass);

		CustomAxiom axiom = new NegationAxiomWithUnion(iriPath + "#", subjectName, objects,
				lemmatizeProperty(property));
		axioms.put(axiom, id);

		id++;

		triggerSave();
	}

	public void addOnlyRestrictionWithUnion(String iriPath, String subjectName, String property, List<String> objects) {
		OWLClass subject = df.getOWLClass(iriPath + "#" + subjectName);
		OWLObjectProperty prop = df.getOWLObjectProperty(iriPath + "#" + lemmatizeProperty(property));

		List<OWLClass> owlObjects = new ArrayList<OWLClass>();
		OWLClass object;
		for (String s : objects) {
			object = df.getOWLClass(iriPath + "#" + s);
			owlObjects.add(object);
		}

		OWLObjectUnionOf owlObjectUnionOf = df.getOWLObjectUnionOf(owlObjects);
		OWLObjectAllValuesFrom allValues = df.getOWLObjectAllValuesFrom(prop, owlObjectUnionOf);
		OWLAxiom subClass = df.getOWLSubClassOfAxiom(subject, allValues);
		manager.addAxiom(currentOntology, subClass);

		CustomAxiom axiom = new UniversalAxiom(iriPath + "#", subjectName, objects, property);

		axioms.put(axiom, id);

		id++;

		triggerSave();

	}

	public void addOnlyRestriction(String iriPath, OWLClassExpression subject, OWLObjectProperty role,
			OWLClassExpression object) {
		OWLObjectAllValuesFrom allValues = df.getOWLObjectAllValuesFrom(role, object);
		OWLAxiom subClass = df.getOWLSubClassOfAxiom(subject, allValues);
		manager.addAxiom(currentOntology, subClass);
		triggerSave();
	}

	public void addBooleanAxiom(String iriPath, String subjectName, String property) {
		OWLClass subject = df.getOWLClass(iriPath + "#" + subjectName);
		OWLDataProperty prop = df.getOWLDataProperty(iriPath + "#" + property);

		OWLLiteral booleanLiteral = df.getOWLLiteral(true);
		OWLDataHasValue owlDataHasValue = df.getOWLDataHasValue(prop, booleanLiteral);

		OWLAxiom subClass = df.getOWLSubClassOfAxiom(subject, owlDataHasValue);

//		axioms.put(subClass, id);

	}

	public void addRestrictedConceptAxiom(String namespace, String subject, String property1, String object,
			String property2, String object2) {
		// TODO Auto-generated method stub

	}

	public void addNegatedBooleanAxiom(String namespace, String subject, String property) {

	}

	public OWLProperty getOWLObjectProperty(String iriPath, String roleName) {
		OWLObjectProperty prop = df.getOWLObjectProperty(iriPath + "#" + lemmatizeProperty(roleName));
		return prop;
	}

	public OWLProperty getOWLDatatypeProperty(String iriPath, String roleName) {
		return df.getOWLDataProperty(iriPath + "#" + roleName);
	}

	public void addOnlyRestriction(String namespace, String subjectName, String propertyName, List<String> objects) {
		// TODO Auto-generated method stub

	}

	public OWLClassExpression unionOf(String namespace, ArrayList<OWLClassExpression> expressions) {
		return df.getOWLObjectUnionOf(expressions);
	}

	public void addNegationAxiom(String string, OWLClassExpression subject, OWLClassExpression object,
			OWLObjectProperty property) {

		OWLObjectSomeValuesFrom someValues = df.getOWLObjectSomeValuesFrom(property, object);
		OWLObjectComplementOf complement = df.getOWLObjectComplementOf(someValues);
		OWLAxiom subClass = df.getOWLSubClassOfAxiom(subject, complement);
		manager.addAxiom(currentOntology, subClass);

		triggerSave();

	}

	public void addNegationAxiom(String string, OWLClassExpression subject, OWLClassExpression object) {
		OWLObjectComplementOf complement = df.getOWLObjectComplementOf(object);
		OWLAxiom subClass = df.getOWLSubClassOfAxiom(subject, complement);
		manager.addAxiom(currentOntology, subClass);

		triggerSave();
	}

	public OWLClassExpression getOWLTop(String namespace) {

		return df.getOWLThing();

	}

	public OWLClassExpression createOnlyRestriction(String iriPath, OWLObjectProperty role,
			OWLClassExpression concept) {

		OWLObjectAllValuesFrom allValues = df.getOWLObjectAllValuesFrom(role, concept);
		return allValues;
	}

	@Override
	public void removeOntology(String iriPath) {
		// TODO Auto-generated method stub
		
	}

}
