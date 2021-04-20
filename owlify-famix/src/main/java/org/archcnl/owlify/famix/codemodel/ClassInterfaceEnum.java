package org.archcnl.owlify.famix.codemodel;

import java.util.ArrayList;
import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;

public abstract class ClassInterfaceEnum extends DefinedType {

    private List<DefinedType> nestedTypes;
    private List<Method> methods;
    private List<Field> fields;
    private List<Modifier> modifiers;

    public ClassInterfaceEnum(
            String name,
            List<DefinedType> nestedTypes,
            List<Method> methods,
            List<Field> fields,
            List<Modifier> modifiers,
            List<AnnotationInstance> annotations) {
        super(name, annotations);
        this.nestedTypes = nestedTypes;
        this.methods = methods;
        this.fields = fields;
        this.modifiers = modifiers;
    }

    /** @return the nestedTypes */
    public List<DefinedType> getNestedTypes() {
        return nestedTypes;
    }

    /** @return the methods */
    public List<Method> getMethods() {
        return methods;
    }

    /** @return the fields */
    public List<Field> getFields() {
        return fields;
    }

    /** @return the modifiers */
    public List<Modifier> getModifiers() {
        return modifiers;
    }

    @Override
    public List<String> getNestedTypeNames() {
        List<String> result = new ArrayList<>();
        result.add(getName());

        nestedTypes.forEach(type -> result.addAll((type.getNestedTypeNames())));
        return result;
    }

    @Override
    protected void secondPassProcess(FamixOntologyNew ontology, Individual individual) {
        fields.forEach(field -> field.modelIn(ontology, getName(), individual));
        methods.forEach(method -> method.modelIn(ontology, getName(), individual));
        modifiers.forEach(mod -> mod.modelIn(ontology, individual));

        // recursively call nested types
        nestedTypes.forEach(t -> t.secondPass(ontology));
    }

    @Override
    protected void firstPassPostProcess(FamixOntologyNew ontology) {
        nestedTypes.forEach(t -> t.firstPass(ontology));
    }
}
