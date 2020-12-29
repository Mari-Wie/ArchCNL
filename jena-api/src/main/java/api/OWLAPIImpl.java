package api;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import stemmer.StanfordLemmatizer;

public class OWLAPIImpl implements OntologyAPI {

	private static final Logger LOG = LogManager.getLogger(OWLAPIImpl.class);
	
	private OWLOntology currentOntology;
	private OWLDataFactory df = OWLManager.getOWLDataFactory();
	OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
	private String filePath;

	public OWLAPIImpl() {

	}

	@Override
	public void createOntology(String filePath, String iriPath) {
		LOG.trace("creating ontology with filePath \""+filePath+"\" and iriPath \""+iriPath+"\"");
		this.filePath = filePath;
		try {
			IRI iri = IRI.create(iriPath);
			currentOntology = manager.createOntology(iri);
			File output = new File(filePath);
			// Output will be deleted on exit; to keep temporary file replace
			// previous line with the following
			// File output = File.createTempFile("saved_pizza", ".owl");
			IRI documentIRI2 = IRI.create(output);
			LOG.debug("Saving ontology to file: " + output.getAbsolutePath());
			// save in OWL/XML format
			manager.saveOntology(currentOntology, new OWLXMLDocumentFormat(), documentIRI2);
			// save in RDF/XML
			manager.saveOntology(currentOntology, documentIRI2);
		} catch (OWLOntologyCreationException e) {
			LOG.error("Creating an ontology failed.");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OWLOntologyStorageException e) {
			LOG.error("Saving an ontology failed.");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Override
	public void removeOntology(String iriPath) {
		// TODO: parameter iriPath is not used -> remove it
		manager.removeOntology(currentOntology);
	}

	@Override
	public void addSubClassAxiom(OWLClassExpression superClass, OWLClassExpression subClass) {
		OWLAxiom subClassAxiom = df.getOWLSubClassOfAxiom(subClass, superClass);
		manager.addAxiom(currentOntology, subClassAxiom);
		triggerSave();

	}

	private String lemmatizeProperty(String propertyName) {
		StanfordLemmatizer slem = new StanfordLemmatizer();
		List<String> result = slem.lemmatize(propertyName);
		return result.get(0);
	}

	@Override
	public OWLClassExpression addMaxCardinalityRestrictionAxiom(OWLClassExpression object,
			OWLObjectProperty property, int count) {
		return df.getOWLObjectMaxCardinality(count, property, object);
	}

	@Override
	public OWLClassExpression addMinCardinalityRestrictionAxiom(OWLClassExpression object,
			OWLObjectProperty property, int count) {
		
		return df.getOWLObjectMinCardinality(count, property, object);
	}

	@Override
	public OWLClassExpression addExactCardinalityRestrictionAxiom(OWLClassExpression object,
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

	@Override
	public void triggerSave() {
		File output = new File(this.filePath);
		IRI documentIRI2 = IRI.create(output);
		// save in OWL/XML format
		try {
			LOG.debug("Saving ontology to file: " + output.getAbsolutePath());
			manager.saveOntology(currentOntology, new OWLXMLDocumentFormat(), documentIRI2);
			// save in RDF/XML
			manager.saveOntology(currentOntology, documentIRI2);
		} catch (OWLOntologyStorageException e) {
			LOG.error("Saving an ontology failed.");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public OWLClass getOWLClass(String iriPath, String subjectName) {
		OWLClass subject = df.getOWLClass(iriPath + "#" + subjectName);

		return subject;
	}

	@Override
	public OWLDataHasValue addDataHasValue(String value, OWLDataProperty roleName) {
		OWLLiteral literal = df.getOWLLiteral(value);
		OWLDataHasValue hasValues = df.getOWLDataHasValue(roleName, literal);

		// TODO: Is the state even modified here? If not, the call of triggerSave() has no effect and can be removed.
		triggerSave();

		return hasValues;
	}

	@Override
	public OWLDataHasValue addDataHasValue(int value, OWLDataProperty roleName) {
		OWLLiteral literal = df.getOWLLiteral(value);
		OWLDataHasValue hasValue = df.getOWLDataHasValue(roleName, literal);
		
		// TODO: Is the state even modified here? If not, the call of triggerSave() has no effect and can be removed.
		triggerSave();

		return hasValue;
	}

	@Override
	public OWLClassExpression addSomeValuesFrom(OWLObjectProperty role,
			OWLClassExpression expression) {
		OWLObjectSomeValuesFrom someValues = df.getOWLObjectSomeValuesFrom(role, expression);
		
		// TODO: Is the state even modified here? If not, the call of triggerSave() has no effect and can be removed.
		triggerSave();

		return someValues;

	}
	
//	@Override
//	public void addSomeValuesFrom(String iriPath, OWLClassExpression subclass, OWLClassExpression superclass,
//			OWLObjectProperty role) {
//		// TODO: parameter iriPath is not used, remove it
//		OWLObjectSomeValuesFrom someValues = df.getOWLObjectSomeValuesFrom(role, superclass);
//		OWLSubClassOfAxiom subClassAxiom = df.getOWLSubClassOfAxiom(subclass, someValues);
//		manager.addAxiom(currentOntology, subClassAxiom);
//
//		triggerSave();
//
//	}

	@Override
	public OWLObjectIntersectionOf intersectionOf(ArrayList<OWLClassExpression> expressions) {
		return df.getOWLObjectIntersectionOf(expressions);
	}

	@Override
	public void addDomainRangeAxiom(OWLClassExpression subject, OWLClassExpression object,
			OWLObjectProperty property) {
		OWLObjectPropertyDomainAxiom domainAxiom = df.getOWLObjectPropertyDomainAxiom(property, subject);

		OWLObjectPropertyRangeAxiom rangeAxiom = df.getOWLObjectPropertyRangeAxiom(property, object);
		manager.addAxiom(currentOntology, domainAxiom);
		manager.addAxiom(currentOntology, rangeAxiom);
		
		triggerSave();
	}

	@Override
	public OWLProperty getOWLObjectProperty(String iriPath, String roleName) {
		OWLObjectProperty prop = df.getOWLObjectProperty(iriPath + "#" + lemmatizeProperty(roleName));
		return prop;
	}

	@Override
	public OWLProperty getOWLDatatypeProperty(String iriPath, String roleName) {
		// TODO: the role name is not lemmatized, unlike the behavior of getOWLObjectProperty()
		// is this desired?
		return df.getOWLDataProperty(iriPath + "#" + roleName);
	}

	@Override
	public OWLClassExpression unionOf(ArrayList<OWLClassExpression> expressions) {
		return df.getOWLObjectUnionOf(expressions);
	}

	@Override
	public void addNegationAxiom(OWLClassExpression subject, OWLClassExpression object,
			OWLObjectProperty property) {
		OWLObjectSomeValuesFrom someValues = df.getOWLObjectSomeValuesFrom(property, object);
		OWLObjectComplementOf complement = df.getOWLObjectComplementOf(someValues);
		OWLAxiom subClass = df.getOWLSubClassOfAxiom(subject, complement);
		manager.addAxiom(currentOntology, subClass);

		triggerSave();
	}

	@Override
	public void addNegationAxiom(OWLClassExpression first, OWLClassExpression second) {
		OWLObjectComplementOf complement = df.getOWLObjectComplementOf(second);
		OWLAxiom subClass = df.getOWLSubClassOfAxiom(first, complement);
		manager.addAxiom(currentOntology, subClass);
		
		triggerSave();
	}

	@Override
	public void addSubPropertyOfAxiom(String iriPath, String subProperty, String superProperty) {
		// TODO: This might cause some bugs: what if one of the properties refers to a DatatypeProperty?
		OWLObjectProperty sub = df.getOWLObjectProperty(iriPath + "#" + lemmatizeProperty(subProperty));
		OWLObjectProperty sup = df.getOWLObjectProperty(iriPath + "#" + lemmatizeProperty(superProperty));
		OWLAxiom subPropertyAxiom = df.getOWLSubObjectPropertyOfAxiom(sub, sup);

		manager.addAxiom(currentOntology, subPropertyAxiom);
		triggerSave();
	}

	@Override
	public OWLClassExpression getOWLTop() {
		return df.getOWLThing();

	}

	@Override
	public OWLClassExpression createOnlyRestriction(OWLObjectProperty role,
			OWLClassExpression concept) {
		OWLObjectAllValuesFrom allValues = df.getOWLObjectAllValuesFrom(role, concept);

		return allValues;
	}
}
