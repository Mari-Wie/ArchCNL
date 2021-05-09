package org.archcnl.owlify.famix.codemodel;

import java.util.ArrayList;
import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;

/** Abstract super-class of classes, interfaces and enums. */
public abstract class ClassInterfaceEnum extends DefinedType {

    private List<DefinedType> nestedTypes;
    private List<Method> methods;
    private List<Field> fields;

    /**
     * Constructor.
     *
     * @param name Fully qualified name of the modeled class/interface/enum.
     * @param nestedTypes List of types which declaration is nested in the declaration of this type.
     * @param methods List of methods defined for this type.
     * @param fields List of fields defined in this type.
     * @param modifiers List of modifiers for this type's declaration.
     * @param annotations List of annotation instances for this type's declaration.
     */
    protected ClassInterfaceEnum(
            String name,
            List<DefinedType> nestedTypes,
            List<Method> methods,
            List<Field> fields,
            List<Modifier> modifiers,
            List<AnnotationInstance> annotations) {
        super(name, annotations, modifiers);
        this.nestedTypes = nestedTypes;
        this.methods = methods;
        this.fields = fields;
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
        methods.forEach(method -> method.modelIn(ontology, individual));

        // recursively call nested types
        nestedTypes.forEach(t -> t.secondPass(ontology));
    }

    @Override
    protected void firstPassPostProcess(FamixOntologyNew ontology) {
        nestedTypes.forEach(t -> t.firstPass(ontology));
    }
}
