package ontology;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;

public class FamixOntology {

	private FamixOntClassesAndProperties classesAndProperties;
	private OntModel model;

	/*
	 * A map between the fully qualified name of a class and the corresponding
	 * individual of the ontology.
	 */
	private Map<String, Individual> famixTypeIndividualCache;
	private Map<String, Individual> namespaceIndividualCache;
	private Map<String, String> individualToConcreteOntClass;
	private Map<Individual, Map<String,Individual>> annotationTypeToAttribute;

	private long typeID;
	private long inheritanceID;
	private long annotationInstanceID;
	private long attributeID;
	private long methodID;
	private long parameterID;
	private long exceptionId;
	private long localVariableId;
	private long namespaceId;
	private long annotationTypeAttributeId;
	private long annotationInstanceAttributeId;
	private long invocationId;
	private long importId;

	public FamixOntology(InputStream famixOntologyInputStream) {
		famixTypeIndividualCache = new HashMap<String, Individual>();
		individualToConcreteOntClass = new HashMap<String, String>();
		namespaceIndividualCache = new HashMap<String, Individual>();
		classesAndProperties = new FamixOntClassesAndProperties();
		annotationTypeToAttribute = new HashMap<Individual, Map<String,Individual>>();
		loadFamixModel(famixOntologyInputStream);
	}

	private void loadFamixModel(InputStream famixOntologyInputStream) {
		model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		model.read(famixOntologyInputStream, null);
	}
	
	public Individual getNamespaceIndividualWithName(String namespaceName) {
		if(!namespaceIndividualCache.containsKey(namespaceName)) {
			Individual individual = classesAndProperties.createNamespaceIndividual(model,namespaceId++);
			namespaceIndividualCache.put(namespaceName, individual);
		}
		return namespaceIndividualCache.get(namespaceName);
	}
	
	public Individual getFamixClassWithName(String className) {

		if (!famixTypeIndividualCache.containsKey(className)) {
			Individual individual = classesAndProperties.getFamixClassIndividual(model, className, typeID);
			famixTypeIndividualCache.put(className, individual);
			typeID++;
		}
		individualToConcreteOntClass.put(className, "FamixClass");
		return famixTypeIndividualCache.get(className);
	}

	public Individual getAnnotationTypeIndividualWithName(String annotationTypeName) {
		if (!famixTypeIndividualCache.containsKey(annotationTypeName)) {
			Individual individual = classesAndProperties.getAnnotationTypeIndividual(model, annotationTypeName, typeID);
			setHasNamePropertyForNamedEntity(annotationTypeName, individual);
			famixTypeIndividualCache.put(annotationTypeName, individual);
			typeID++;
		}

		individualToConcreteOntClass.put(annotationTypeName, "AnnotationType");
		return famixTypeIndividualCache.get(annotationTypeName);
	}

	public Individual getEnumTypeIndividualWithName(String enumTypeName) {
		if (!famixTypeIndividualCache.containsKey(enumTypeName)) {
			Individual individual = classesAndProperties.getEnumTypeIndividual(model, enumTypeName, typeID);
			famixTypeIndividualCache.put(enumTypeName, individual);
			typeID++;
		}
		individualToConcreteOntClass.put(enumTypeName, "Enum");
		return famixTypeIndividualCache.get(enumTypeName);
	}
	
	public Individual createAnnotationTypeAttributeIndividual() {
		return classesAndProperties.getAnnotationTypeAttributeIndividual(model, annotationTypeAttributeId++);
		
	}

	public void setHasNamePropertyForNamedEntity(String name, Individual namedEntity) {
		DatatypeProperty property = classesAndProperties.getHasNameProperty(model);
		namedEntity.addLiteral(property, name);
	}

	public DatatypeProperty getHasNameProperty() {
		return classesAndProperties.getHasNameProperty(model);
	}

	public Map<String, Individual> getFamixClassIndividualCache() {
		return famixTypeIndividualCache;
	}

	public void setIsInterfaceForFamixClass(boolean isInterface, Individual famixClass) {
		DatatypeProperty property = classesAndProperties.getIsInterfaceProperty(model);
		famixClass.addLiteral(property, isInterface);
	}

	public DatatypeProperty getIsInterfaceProperty() {
		return classesAndProperties.getIsInterfaceProperty(model);
	}

	public void setHasModifierForNamedEntity(String modifier, Individual namedEntity) {
		DatatypeProperty property = classesAndProperties.getHasModifierProperty(model);
		namedEntity.addLiteral(property, modifier);
	}

	public DatatypeProperty getHasModifierProperty() {
		return classesAndProperties.getHasModifierProperty(model);
	}

	public void setHasAnnotationInstanceForEntity(Individual annotationIndividual, Individual famixTypeIndividual) {
		ObjectProperty property = classesAndProperties.getHasAnnotationInstanceProperty(model);
		famixTypeIndividual.addProperty(property, annotationIndividual);

	}

	public ObjectProperty getHasAnnotationProperty() {
		return classesAndProperties.getHasAnnotationInstanceProperty(model);
	}

	public void setInheritanceBetweenSubClassAndSuperClass(Individual superClassIndividual,
			Individual subClassIndividual) {
		Individual inheritance = classesAndProperties.getInheritanceIndividual(model, inheritanceID);
		inheritanceID++;
		ObjectProperty subclassProperty = classesAndProperties.getSubClassProperty(model);
		ObjectProperty superclassProperty = classesAndProperties.getSuperClassProperty(model);
		inheritance.addProperty(subclassProperty, subClassIndividual);
		inheritance.addProperty(superclassProperty, superClassIndividual);
	}

	public Individual getInheritanceBetweenSubClassAndSuperClass(Individual superClassIndividual,
			Individual subClassIndividual) {
		Individual inheritance = classesAndProperties.getInheritanceIndividual(model, inheritanceID);
		inheritanceID++;
		ObjectProperty subclass = classesAndProperties.getSubClassProperty(model);
		ObjectProperty superclass = classesAndProperties.getSuperClassProperty(model);
		inheritance.addProperty(subclass, subClassIndividual);
		inheritance.addProperty(superclass, superClassIndividual);

		return inheritance;
	}

	public String getOntologyClassOfFamixTypeIndividual(String typeName) {
		return individualToConcreteOntClass.get(typeName);
	}

	public ObjectProperty getSubClassProperty() {
		return classesAndProperties.getSubClassProperty(model);
	}

	public ObjectProperty getSuperClassProperty() {
		return classesAndProperties.getSuperClassProperty(model);
	}

	public Individual getAnnotationInstanceIndividual() {
		return classesAndProperties.getAnnotationInstanceIndividual(model, annotationInstanceID++);
	}

	public void setHasAnnotationTypeProperty(Individual annotationInstanceIndividual, Individual annotationType) {
		ObjectProperty hasAnnotationTypeProperty = classesAndProperties.getHasAnnotationTypeProperty(model);
		annotationInstanceIndividual.addProperty(hasAnnotationTypeProperty, annotationType);
	}

	public void save(String resultPath) {
		try {
			model.write(new FileOutputStream(new File(resultPath)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Individual getAttributeIndividual() {

		return classesAndProperties.getAttributeIndividual(model, attributeID++);
	}

	public void setDefinesAttributePropertyForType(Individual parent, Individual fieldIndividual) {
		ObjectProperty definesAttribute = classesAndProperties.getDefinesAttributeObjectProperty(model);
		parent.addProperty(definesAttribute, fieldIndividual);
				
	}

	public void setDeclaredTypeForBehavioralOrStructuralEntity(Individual entity, Individual declaredType) {
		ObjectProperty hasDeclaredTypeProperty = classesAndProperties.getHasDeclaredTypeProperty(model);
		entity.addProperty(hasDeclaredTypeProperty, declaredType);
	}

	public Individual getPrimitiveTypeIndividual(String primitiveTypeName) {
		return classesAndProperties.getPrimitiveTypeIndividual(model,primitiveTypeName);
	}

	public Individual getMethodIndividual() {
		return classesAndProperties.getMethodIndividual(model, methodID++);
	}

	public void setSignatureOfBehavioralEntity(String signature, Individual methodIndividual) {
		DatatypeProperty hasSignatureProperty = classesAndProperties.getHasSignatureProperty(model);
		methodIndividual.addLiteral(hasSignatureProperty, signature);
	}

	public void setDefinesMethodPropertyForFamixType(Individual parent, Individual methodIndividual) {
		ObjectProperty definesMethodProperty = classesAndProperties.getDefinesMethodProperty(model);
		parent.addProperty(definesMethodProperty, methodIndividual);
	}

	public Individual getParameterIndividual() {
		return classesAndProperties.getParameterIndividual(model,parameterID++);
	}

	public void setDefinesParameterPropertyForBehavioralEntity(Individual parent, Individual parameterIndividual) {
		ObjectProperty definesParameterProperty = classesAndProperties.getDefinesParameterProperty(model);
		parent.addProperty(definesParameterProperty, parameterIndividual);
	}

	public void setIsExternalTypeProperty(boolean isExternal, Individual externalType) {
		DatatypeProperty isExternalProperty = classesAndProperties.getIsExternalProperty(model);
		externalType.addLiteral(isExternalProperty, isExternal);
	}

	public void setHasDeclaredExceptionProperty(Individual thrownException, Individual methodIndividual) {
		ObjectProperty hasDeclaredExceptionProperty = classesAndProperties.getHasDeclaredExceptionProperty(model);
		methodIndividual.addProperty(hasDeclaredExceptionProperty, thrownException);
	}

	
	
	public Individual getDeclaredExceptionIndividual() {
		return classesAndProperties.getDeclaredExceptionIndividual(model, exceptionId++);		
	}

	public Individual getThrownExceptionIndividual() {
		return classesAndProperties.getThrownExceptionIndividual(model, exceptionId++);		
	}
	
	public Individual getCaughtExceptionIndividual() {
		return classesAndProperties.getCaughtExceptionIndividual(model, exceptionId++);		
	}

	public void setExceptionHasDefiningClass(Individual exceptionIndividual, Individual definingClass) {
		ObjectProperty hasDefiningClassProperty = classesAndProperties.getHasDefiningClassProperty(model);
		exceptionIndividual.addProperty(hasDefiningClassProperty, definingClass);
	}

	public Individual getLocalVariableIndividual() {
		return classesAndProperties.getLocalVariableIndividual(model,localVariableId++);
	}

	public void setHasCaughtExceptionProperty(Individual caughtExceptionIndividual, Individual methodIndividual) {
		ObjectProperty hasCaughtExceptionProperty = classesAndProperties.getHasCaughtExceptionProperty(model);
		methodIndividual.addProperty(hasCaughtExceptionProperty, caughtExceptionIndividual);
	}

	public void setThrowsExceptionProperty(Individual thrownExceptionIndividual, Individual methodIndividual) {
		ObjectProperty throwsExceptionProperty = classesAndProperties.getThrowsExceptionProperty(model);
		methodIndividual.addProperty(throwsExceptionProperty, thrownExceptionIndividual);
		
	}

	public void setIsConstructorProperty(boolean isConstructor, Individual methodIndividual) {
		DatatypeProperty isConstructorProperty = classesAndProperties.getIsConstructorProperty(model);
		methodIndividual.addLiteral(isConstructorProperty, isConstructor);
	}

	public void setNamespaceContainsProperty(Individual parentNamespace, Individual namespaceIndividual) {
		ObjectProperty namespaceContainsProperty = classesAndProperties.getNamespaceContainsProperty(model);
		parentNamespace.addProperty(namespaceContainsProperty, namespaceIndividual);
	}

	public void add(OntModel ontology) {
		model.add(ontology);
	}

	public void setHasAnnotationTypeAttributeForAnnotationType(Individual annotationTypeIndividual,
			String nameOfAnnotationAttribute, Individual annotationTypeAttributeIndividual) {
		ObjectProperty hasAnnotationTypeAttribute = classesAndProperties.getHasAnnotationTypeAttributeProperty(model);
		annotationTypeIndividual.addProperty(hasAnnotationTypeAttribute, annotationTypeAttributeIndividual);
		Map<String,Individual> annotationTypeAttributes = annotationTypeToAttribute.get(annotationTypeIndividual);
		if(annotationTypeToAttribute.get(annotationTypeIndividual) == null) {
			annotationTypeAttributes = new HashMap<String,Individual>();
		}
		annotationTypeAttributes.put(nameOfAnnotationAttribute, annotationTypeAttributeIndividual);
		annotationTypeToAttribute.put(annotationTypeIndividual, annotationTypeAttributes);
	}

	public Individual getAnnotationTypeAttributeOfAnnotationTypeByName(String name, Individual annotationType) {
		Map<String,Individual> annotationTypeAttributes = annotationTypeToAttribute.get(annotationType);
		
		if(annotationTypeAttributes == null) {
			return null;
		}
		
		for (String annotationTypeAttributeName : annotationTypeAttributes.keySet()) {
			if(annotationTypeAttributeName.equals(name)) {
				return annotationTypeAttributes.get(name);
			}
		}
		return null;
	}

	public Individual getAnnotationInstanceAttributeIndividual() {
		return classesAndProperties.getAnnotationInstanceAttributeIndividual(model,annotationInstanceAttributeId++);
	}

	public void setHasAnnotationTypeAttributeForAnnotationInstanceAttribute(Individual annotationTypeAttribute,
			Individual annotationInstanceAttribute) {
		ObjectProperty property = classesAndProperties.getHasAnnotationTypeAttributeProperty(model);
		annotationInstanceAttribute.addProperty(property, annotationTypeAttribute);
	}

	public void setHasAnnotationInstanceAttributeForAnnotationInstance(Individual annotationInstanceAttribute,
			Individual annotationInstance) {
		ObjectProperty property = classesAndProperties.getHasAnnotationInstanceAttribute(model);
		annotationInstance.addProperty(property, annotationInstanceAttribute);
	}

	public void setHasValueForAnnotationInstanceAttribute(Individual annotationInstanceAttribute, String value) {
		DatatypeProperty property = classesAndProperties.getHasValueProperty(model);
		annotationInstanceAttribute.addLiteral(property, value);
	}

	public void setHasFullQualifiedNameForType(Individual currentUnitIndividual, String fullQualifiedName) {
		DatatypeProperty property = classesAndProperties.getHasFullQualifiedNameProperty(model);
		currentUnitIndividual.addLiteral(property, fullQualifiedName);
	}

	public void setDefinesLocalVariableProperty(Individual parent, Individual localVariableIndividual) {
		
		ObjectProperty property = classesAndProperties.getDefinesVariableProperty(model);
		parent.addProperty(property, localVariableIndividual);
		
	}

	public Individual getInvocationIndividual() {
		return classesAndProperties.getInvocationIndividual(model,invocationId++);
	}

	public void setHasReceiverProperty(Individual invocation, Individual receiver) {
		ObjectProperty property = classesAndProperties.getHasReceiverProperty(model);
		invocation.addProperty(property, receiver);
	}

	public void setHasSender(Individual invocation, Individual sender) {
		ObjectProperty property = classesAndProperties.getHasSenderProperty(model);
		invocation.addProperty(property, sender);
		
	}

	public Individual getImportAssociationIndividual() {
		return classesAndProperties.getImportAssociationIndividual(model,importId++);
	}

	public void setImports(Individual accessingUnit, Individual importedType) {
		ObjectProperty property = classesAndProperties.getImportsProperty(model);
		accessingUnit.addProperty(property, importedType);
	}

	public void setInvokes(Individual accessingUnit, Individual receiver) {
		ObjectProperty property = classesAndProperties.getInvokesProperty(model);
		accessingUnit.addProperty(property, receiver);
	}


}
