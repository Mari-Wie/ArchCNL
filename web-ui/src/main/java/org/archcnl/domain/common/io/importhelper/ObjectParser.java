package org.archcnl.domain.common.io.importhelper;

import java.util.regex.Pattern;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.BooleanValue;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.StringValue;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.common.io.RegexUtils;
import org.archcnl.domain.common.io.importhelper.exceptions.NoObjectTypeException;

public class ObjectParser {

    private static final Pattern CONCEPT_RELATION_PATTERN = Pattern.compile(".+:.+");
    private static final Pattern NAME_PATTERN = Pattern.compile("(?<=.+:).+");

    private ObjectParser() {}

    public static ObjectType parseObject(
            final String potentialObject, final ConceptManager conceptManager)
            throws NoObjectTypeException {
        try {
            if (potentialObject.matches("'.*'")) {
                return new StringValue(potentialObject.substring(1, potentialObject.length() - 1));
            } else if (potentialObject.startsWith("?")) {
                return new Variable(potentialObject.substring(1));
            } else if (potentialObject.matches("'.+'\\^\\^xsd:boolean")) {
                String boolString =
                        RegexUtils.getFirstMatch(Pattern.compile("(?<=').+(?=')"), potentialObject);
                return new BooleanValue(Boolean.parseBoolean(boolString));
            } else if (potentialObject.matches(ObjectParser.CONCEPT_RELATION_PATTERN.toString())) {
                String objectName =
                        RegexUtils.getFirstMatch(ObjectParser.NAME_PATTERN, potentialObject);
                return conceptManager
                        .getConceptByName(objectName)
                        .orElse(new CustomConcept(objectName, ""));
            } else {
                throw new NoObjectTypeException(potentialObject);
            }
        } catch (Exception e) {
            throw new NoObjectTypeException(potentialObject);
        }
    }
}
