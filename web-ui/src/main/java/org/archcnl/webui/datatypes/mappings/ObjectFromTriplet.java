package org.archcnl.webui.datatypes.mappings;

public class ObjectFromTriplet {

    private Concept concept;
    private Variable variable;
    private String value;

    public ObjectFromTriplet(String object) {
        setObject(object);
    }

    public ObjectFromTriplet(Variable object) {
        setObject(object);
    }

    public ObjectFromTriplet(Concept object) {
        setObject(object);
    }

    /**
     * Return value can be null. Exactly one of the three getters will return something not equal to
     * zero.
     *
     * @return The concept of this object
     */
    public Concept getConcept() {
        return concept;
    }

    /**
     * Return value can be null. Exactly one of the three getters will return something not equal to
     * zero.
     *
     * @return The variable of this object
     */
    public Variable getVariable() {
        return variable;
    }

    /**
     * Return value can be null. Exactly one of the three getters will return something not equal to
     * zero.
     *
     * @return The value of this object
     */
    public String getValue() {
        return value;
    }

    public void setObject(Variable variable) {
        this.variable = variable;
        value = null;
        concept = null;
    }

    public void setObject(String value) {
        this.value = value;
        variable = null;
        concept = null;
    }

    public void setObject(Concept concept) {
        this.concept = concept;
        variable = null;
        value = null;
    }

    public String toStringRepresentation() {
        if (value != null) {
            return "'" + value + "'";
        } else if (concept != null) {
            return concept.toStringRepresentation();
        } else {
            return variable.toStringRepresentation();
        }
    }
}
