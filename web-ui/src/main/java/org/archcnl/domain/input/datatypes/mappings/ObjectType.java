package org.archcnl.domain.input.datatypes.mappings;

import java.util.Objects;
import java.util.regex.Pattern;
import org.archcnl.domain.input.datatypes.RulesConceptsAndRelations;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.NoObjectTypeException;
import org.archcnl.domain.input.io.AdocIoUtils;

public abstract class ObjectType {

    private static final Pattern CONCEPT_RELATION_PATTERN = Pattern.compile(".+:.+");
    private static final Pattern NAME_PATTERN = Pattern.compile("(?<=.+:).+");

    public abstract String toStringRepresentation();

    public abstract String getName();

    public static ObjectType parseObject(String potentialObject) throws NoObjectTypeException {
        try {
            if (potentialObject.matches("'.+'")) {
                return new StringValue(potentialObject.substring(1, potentialObject.length() - 1));
            } else if (potentialObject.startsWith("?")) {
                return new Variable(potentialObject.substring(1));
            } else if (potentialObject.matches("'.+'\\^\\^xsd:boolean")) {
                String boolString =
                        AdocIoUtils.getFirstMatch(
                                Pattern.compile("(?<=').+(?=')"), potentialObject);
                return new BooleanValue(Boolean.getBoolean(boolString));
            } else if (potentialObject.matches(CONCEPT_RELATION_PATTERN.toString())) {
                String objectName = AdocIoUtils.getFirstMatch(NAME_PATTERN, potentialObject);
                try {
                    return RulesConceptsAndRelations.getInstance()
                            .getConceptManager()
                            .getConceptByName(objectName);
                } catch (ConceptDoesNotExistException e) {
                    return new CustomConcept(objectName);
                }
            } else {
                throw new NoObjectTypeException(potentialObject);
            }
        } catch (Exception e) {
            throw new NoObjectTypeException(potentialObject);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ObjectType)) {
            return false;
        }
        ObjectType otherObjectType = (ObjectType) o;
        return toStringRepresentation().equals(otherObjectType.toStringRepresentation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(toStringRepresentation());
    }
}
