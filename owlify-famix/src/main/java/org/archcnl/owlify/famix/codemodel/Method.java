package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasName;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasSignature;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.isLocatedAt;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.definesMethod;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasCaughtException;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasDeclaredException;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasDeclaredType;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.throwsException;

import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;
import org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses;
import org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties;

/**
 * Models a method (or interface operation or constructor).
 *
 * <p>Represented by the "Method" ontology class.
 */
public class Method {
    private final String location;
    private final String name;
    private final String signature;
    private List<Modifier> modifiers;
    private List<Parameter> parameters;
    private List<Type> declaredExceptions; // throws SomethingHappenedException
    private List<Type> thrownExceptions; // throw new SomethingHappenedException()
    private List<Type> caughtExceptions;
    private List<LocalVariable> localVariables;
    private List<AnnotationInstance> annotations;
    private Type returnType;
    private final boolean isConstructor;

    /**
     * Constructor.
     *
     * @param name Simple name of the method.
     * @param signature Signature (using the simple name) of the method.
     * @param modifiers List of modifiers for this method.
     * @param parameters List of this method's parameters.
     * @param declaredExceptions List of exceptions which are declared to be thrown by this method
     *     (throws in Java).
     * @param annotations List of annotations for this method.
     * @param returnType Return type of this method. Use "void" when void is returned. Use
     *     Type.UNUSED_VALUE for constructors.
     * @param isConstructor Whether this method is a constructor.
     * @param thrownExceptions List of exceptions thrown from this method, i.e. via a "throw"
     *     statement.
     * @param caughtExceptions List of exceptions which appear in a catch phrase in this method.
     * @param localVariables List of local variables defined in this method's body.
     */
    public Method(
            String location,
            String name,
            String signature,
            List<Modifier> modifiers,
            List<Parameter> parameters,
            List<Type> declaredExceptions,
            List<AnnotationInstance> annotations,
            Type returnType,
            boolean isConstructor,
            List<Type> thrownExceptions,
            List<Type> caughtExceptions,
            List<LocalVariable> localVariables) {
        this.location = location;
        this.name = name;
        this.signature = signature;
        this.modifiers = modifiers;
        this.parameters = parameters;
        this.declaredExceptions = declaredExceptions;
        this.thrownExceptions = thrownExceptions;
        this.annotations = annotations;
        this.returnType = returnType;
        this.isConstructor = isConstructor;
        this.caughtExceptions = caughtExceptions;
        this.localVariables = localVariables;
    }

    /** @return the simple name */
    public String getPosition() {
        return location;
    }

    /** @return the simple name */
    public String getName() {
        return name;
    }

    /** @return the signature */
    public String getSignature() {
        return signature;
    }

    /** @return the modifiers */
    public List<Modifier> getModifiers() {
        return modifiers;
    }

    /** @return the parameters */
    public List<Parameter> getParameters() {
        return parameters;
    }

    /** @return the declared exceptions */
    public List<Type> getDeclaredExceptions() {
        return declaredExceptions;
    }

    /** @return the thrown exceptions */
    public List<Type> getThrownExceptions() {
        return thrownExceptions;
    }

    /** @return the annotations */
    public List<AnnotationInstance> getAnnotations() {
        return annotations;
    }

    /** @return the declared return type */
    public Type getReturnType() {
        return returnType;
    }

    /** @return whether this is a constructor */
    public boolean isConstructor() {
        return isConstructor;
    }

    /** @return the caught exceptions */
    public List<Type> getCaughtExceptions() {
        return caughtExceptions;
    }

    /** @return the local variables */
    public List<LocalVariable> getLocalVariables() {
        return localVariables;
    }

    /**
     * Models this method in the given ontology.
     *
     * @param ontology The ontology in which this method will be modeled.
     * @param parentName A unique name identifying the type in which this method is defined.
     * @param parent The individual of the type in which this method is defined.
     */
    public void modelIn(FamixOntology ontology, String parentName, Individual parent) {
        final String uri = parentName + "." + signature;

        Individual m = ontology.createIndividual(FamixClasses.Method, uri);

        m.addLiteral(ontology.get(isLocatedAt), location);
        m.addLiteral(ontology.get(hasName), name);
        m.addLiteral(ontology.get(hasSignature), signature);
        parent.addProperty(ontology.get(definesMethod), m);
        m.addLiteral(ontology.get(FamixDatatypeProperties.isConstructor), isConstructor);

        if (returnType != Type.UNUSED_VALUE) {
            m.addProperty(ontology.get(hasDeclaredType), returnType.getIndividual(ontology));
        }

        modifiers.forEach(mod -> mod.modelIn(ontology, m));
        parameters.forEach(param -> param.modelIn(ontology, uri, m));
        localVariables.forEach(localVar -> localVar.modelIn(ontology, uri, m));
        annotations.forEach(anno -> anno.modelIn(ontology, uri, m));

        addDeclaredExceptions(ontology, m);
        addThrownExceptions(ontology, m);
        addCaughtExceptions(ontology, m);
    }

    private void addThrownExceptions(FamixOntology ontology, Individual method) {
        for (Type exception : thrownExceptions) {
            method.addProperty(ontology.get(throwsException), exception.getIndividual(ontology));
        }
    }

    private void addCaughtExceptions(FamixOntology ontology, Individual method) {
        for (Type exception : caughtExceptions) {
            method.addProperty(ontology.get(hasCaughtException), exception.getIndividual(ontology));
        }
    }

    private void addDeclaredExceptions(FamixOntology ontology, Individual method) {
        for (Type exception : declaredExceptions) {
            method.addProperty(
                    ontology.get(hasDeclaredException), exception.getIndividual(ontology));
        }
    }
}
