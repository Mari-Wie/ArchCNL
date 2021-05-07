package org.archcnl.owlify.famix.codemodel;

import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;
import org.archcnl.owlify.famix.ontology.FamixURIs;

public class Method {
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

    public Method(
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

    /** @return the name */
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

    /** @return the declaredExceptions */
    public List<Type> getDeclaredExceptions() {
        return declaredExceptions;
    }

    /** @return the thrownExceptions */
    public List<Type> getThrownExceptions() {
        return thrownExceptions;
    }

    /** @return the annotations */
    public List<AnnotationInstance> getAnnotations() {
        return annotations;
    }

    /** @return the returnType */
    public Type getReturnType() {
        return returnType;
    }

    /** @return the isConstructor */
    public boolean isConstructor() {
        return isConstructor;
    }

    /** @return the caughtExceptions */
    public List<Type> getCaughtExceptions() {
        return caughtExceptions;
    }

    /** @return the localVariables */
    public List<LocalVariable> getLocalVariables() {
        return localVariables;
    }

    public void modelIn(FamixOntologyNew ontology, String parentName, Individual parent) {
        final String uri = parentName + "." + name;

        Individual m = ontology.codeModel().getOntClass(FamixURIs.METHOD).createIndividual(uri);

        m.addLiteral(ontology.codeModel().getDatatypeProperty(FamixURIs.HAS_NAME), name);
        m.addLiteral(ontology.codeModel().getDatatypeProperty(FamixURIs.HAS_SIGNATURE), signature);
        parent.addProperty(ontology.codeModel().getObjectProperty(FamixURIs.DEFINES_METHOD), m);
        m.addLiteral(
                ontology.codeModel().getDatatypeProperty(FamixURIs.IS_CONSTRUCTOR), isConstructor);

        if (returnType != Type.UNUSED_VALUE) {
            m.addProperty(
                    ontology.codeModel().getObjectProperty(FamixURIs.HAS_DECLARED_TYPE),
                    returnType.getIndividual(ontology));
        }

        modifiers.forEach(mod -> mod.modelIn(ontology, m));
        parameters.forEach(param -> param.modelIn(ontology, m));
        localVariables.forEach(localVar -> localVar.modelIn(ontology, m));
        annotations.forEach(anno -> anno.modelIn(ontology, m));

        addDeclaredExceptions(ontology, m);
        addThrownExceptions(ontology, m);
        addCaughtExceptions(ontology, m);
    }

    private void addThrownExceptions(FamixOntologyNew ontology, Individual method) {
        for (Type exception : thrownExceptions) {
            // TODO: proper name or issue 117
            Individual individual =
                    ontology.codeModel()
                            .getOntClass(FamixURIs.THROWN_EXCEPTION)
                            .createIndividual("TODO NAME");
            individual.addProperty(
                    ontology.codeModel().getObjectProperty(FamixURIs.HAS_DEFINING_CLASS),
                    exception.getIndividual(ontology));
            method.addProperty(
                    ontology.codeModel().getObjectProperty(FamixURIs.THROWS_EXCEPTION), individual);
        }
    }

    private void addCaughtExceptions(FamixOntologyNew ontology, Individual method) {
        for (Type exception : caughtExceptions) {
            // TODO: proper name or issue 117
            Individual individual =
                    ontology.codeModel()
                            .getOntClass(FamixURIs.CAUGHT_EXCEPTION)
                            .createIndividual("TODO NAME");
            individual.addProperty(
                    ontology.codeModel().getObjectProperty(FamixURIs.HAS_DEFINING_CLASS),
                    exception.getIndividual(ontology));
            method.addProperty(
                    ontology.codeModel().getObjectProperty(FamixURIs.HAS_CAUGHT_EXCEPTION),
                    individual);
        }
    }

    private void addDeclaredExceptions(FamixOntologyNew ontology, Individual method) {
        for (Type exception : declaredExceptions) {
            // TODO: proper name or issue 117
            Individual declaredException =
                    ontology.codeModel()
                            .getOntClass(FamixURIs.DECLARED_EXCEPTION)
                            .createIndividual(exception.getName());
            declaredException.addProperty(
                    ontology.codeModel().getObjectProperty(FamixURIs.HAS_DEFINING_CLASS),
                    exception.getIndividual(ontology));
            method.addProperty(
                    ontology.codeModel().getObjectProperty(FamixURIs.HAS_DECLARED_EXCEPTION),
                    declaredException);
        }
    }
}
