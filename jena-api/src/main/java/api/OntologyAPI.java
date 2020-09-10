package api;

//import java.util.List;
//import java.util.Map;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataProperty;
//import org.semanticweb.owlapi.model.OWLLiteral;
//import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
//import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLProperty;

//import api.axioms.CustomAxiom;
import java.util.ArrayList;

public interface OntologyAPI {
	
	public void createOntology(String filePath, String iriPath);
	
	public void removeOntology(String iriPath);

	public void addSubClassAxiom(String iriPath, OWLClassExpression superClass, OWLClassExpression subClass);

	public void addSubPropertyOfAxiom(String iriPath, String subProperty, String superProperty);

	public void triggerSave();

	public void addDomainRangeAxiom(String namespace, OWLClassExpression subject, OWLClassExpression object, OWLObjectProperty property);

	public void addNegationAxiom(String string, OWLClassExpression subject, OWLClassExpression object);

	public OWLClassExpression createOnlyRestriction(String iriPath, OWLObjectProperty role, OWLClassExpression concept);

	public OWLClass getOWLClass(String iriPath, String subjectName);
	
	public OWLProperty getOWLObjectProperty(String iriPath, String roleName);
	
	public OWLProperty getOWLDatatypeProperty(String iriPath, String roleName);
	
	public OWLDataHasValue addDataHasValue(String iriPath, String value, OWLDataProperty role);
	
	public OWLDataHasValue addDataHasIntegerValue(String iriPath, int value, OWLDataProperty roleName);
	
	public void addSomeValuesFrom(String iriPath, OWLClassExpression subclass, OWLClassExpression superclass, OWLObjectProperty roleName);
	
	public OWLObjectIntersectionOf  intersectionOf(String iriPath, OWLClassExpression[] list);
	
	public OWLClassExpression addSomeValuesFrom(String iriPath, OWLObjectProperty roleName, OWLClassExpression expression);
	
	public OWLObjectIntersectionOf  intersectionOf(String iriPath, OWLClassExpression first, OWLClassExpression second);

	public OWLClassExpression unionOf(String string, ArrayList<OWLClassExpression> expressions);

	public void addNegationAxiom(String string, OWLClassExpression expression, OWLClassExpression expression2, OWLObjectProperty property);

	public OWLClassExpression getOWLTop(String string);
	
	public OWLClassExpression addMaxCardinalityRestrictionAxiom(String namespace, OWLClassExpression object, OWLObjectProperty relation, int count);
	public OWLClassExpression addMinCardinalityRestrictionAxiom(String namespace, OWLClassExpression object, OWLObjectProperty relation, int count);
	public OWLClassExpression addExactCardinalityRestrictionAxiom(String namespace, OWLClassExpression object, OWLObjectProperty relation, int count);

}
