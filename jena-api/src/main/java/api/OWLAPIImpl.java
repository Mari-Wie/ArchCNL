package api;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import stemmer.StanfordLemmatizer;

public class OWLAPIImpl implements OntologyAPI {

	private OWLOntology currentOntology;
	private OWLDataFactory df = OWLManager.getOWLDataFactory();
	OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
	private String filePath;

	public OWLAPIImpl() {

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
	
	@Override
	public void removeOntology(String iriPath) {
		manager.removeOntology(currentOntology);
	}

	public void addSubClassAxiom(String iriPath, OWLClassExpression superClass, OWLClassExpression subClass) {

		OWLAxiom subClassAxiom = df.getOWLSubClassOfAxiom(subClass, superClass);
		manager.addAxiom(currentOntology, subClassAxiom);

		// CustomAxiom axiom = new SubConceptAxiom(subClass.toString(),
		// superClass.toString());
		// axioms.put(axiom, id);

		triggerSave();

	}

	private String lemmatizeProperty(String propertyName) {
		StanfordLemmatizer slem = new StanfordLemmatizer();
		List<String> result = slem.lemmatize(propertyName);
		return result.get(0);
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

	// method is not used as of today, keep for the case it will be needed in future
//	private int parseCount(String count) {
//		if (_numbers.contains(count)) {
//			return _numbers.indexOf(count);
//		}
//		return Integer.parseInt(count);
//	}

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

	public OWLClass getOWLClass(String iriPath, String subjectName) {
		OWLClass subject = df.getOWLClass(iriPath + "#" + subjectName);

		return subject;
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

	public OWLObjectIntersectionOf intersectionOf(String iriPath, OWLClassExpression first, OWLClassExpression second) {
		return df.getOWLObjectIntersectionOf(first, second);

	}

	public OWLObjectIntersectionOf intersectionOf(String iriPath, OWLClassExpression[] list) {
		return df.getOWLObjectIntersectionOf(list);
	}

	public void addDomainRangeAxiom(String namespace, OWLClassExpression subject, OWLClassExpression object,
			OWLObjectProperty property) {
		OWLObjectPropertyDomainAxiom domainAxiom = df.getOWLObjectPropertyDomainAxiom(property, subject);

		OWLObjectPropertyRangeAxiom rangeAxiom = df.getOWLObjectPropertyRangeAxiom(property, object);
		manager.addAxiom(currentOntology, domainAxiom);
		manager.addAxiom(currentOntology, rangeAxiom);
		
		triggerSave();
	}

	public OWLProperty getOWLObjectProperty(String iriPath, String roleName) {
		OWLObjectProperty prop = df.getOWLObjectProperty(iriPath + "#" + lemmatizeProperty(roleName));
		return prop;
	}

	public OWLProperty getOWLDatatypeProperty(String iriPath, String roleName) {
		return df.getOWLDataProperty(iriPath + "#" + roleName);
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

	public void addSubPropertyOfAxiom(String iriPath, String subProperty, String superProperty) {

		OWLObjectProperty sub = df.getOWLObjectProperty(iriPath + "#" + lemmatizeProperty(subProperty));
		OWLObjectProperty sup = df.getOWLObjectProperty(iriPath + "#" + lemmatizeProperty(superProperty));
		OWLAxiom subPropertyAxiom = df.getOWLSubObjectPropertyOfAxiom(sub, sup);

		manager.addAxiom(currentOntology, subPropertyAxiom);
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



	

}
