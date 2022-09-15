package org.archcnl.domain.common.conceptsandrelations;

import java.util.Collections;
import java.util.Set;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ActualObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.StringValue;

public class JenaBuiltinRelation extends Relation {

    private static final JenaBuiltinRelation regexRelation =
            new JenaBuiltinRelation(
                    "matches",
                    "regex",
                    "Checks whether a string matches a regular expression represented by another string. "
                            + "The syntax of the regular expression pattern is according to java.util.regex.",
                    Collections.singleton(new StringValue("")),
                    Collections.singleton(new StringValue("")));

    private String realName;

    public JenaBuiltinRelation(
            String name,
            String realName,
            String description,
            Set<ActualObjectType> relatableSubjectTypes,
            Set<ActualObjectType> relatableObjectTypes) {
        super(name, description, relatableSubjectTypes, relatableObjectTypes);
        this.realName = realName;
    }

    public String getRealName() {
        return realName;
    }

    @Override
    public String transformToGui() {
        return transformToAdoc();
    }

    @Override
    public String transformToAdoc() {
        return getRealName();
    }

    public static JenaBuiltinRelation getRegexRelation() {
        return regexRelation;
    }
}
