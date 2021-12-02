package org.archcnl.domain.common;

import java.util.regex.Pattern;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.NoObjectTypeException;
import org.archcnl.domain.input.io.AdocIoUtils;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;

public abstract class ObjectType implements FormattedDomainObject {

    private static final Pattern CONCEPT_RELATION_PATTERN = Pattern.compile(".+:.+");
    private static final Pattern NAME_PATTERN = Pattern.compile("(?<=.+:).+");

    public abstract String getName();

    public static ObjectType parseObject(String potentialObject) throws NoObjectTypeException {
        try {
            if (potentialObject.matches("'.*'")) {
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
                    return new CustomConcept(objectName, "");
                }
            } else {
                throw new NoObjectTypeException(potentialObject);
            }
        } catch (Exception e) {
            throw new NoObjectTypeException(potentialObject);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return requiredEqualsOverride(obj);
    }

    @Override
    public int hashCode() {
        return requiredHashCodeOverride();
    }

    protected abstract boolean requiredEqualsOverride(Object obj);

    protected abstract int requiredHashCodeOverride();
}
