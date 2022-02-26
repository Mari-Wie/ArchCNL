package org.archcnl.domain.common.io.importhelper;

import java.util.LinkedHashSet;
import java.util.regex.Pattern;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.io.RegexUtils;
import org.archcnl.domain.common.io.exceptions.NoMatchFoundException;
import org.archcnl.domain.common.io.importhelper.exceptions.NoRelationException;

public class PredicateParser {

    private PredicateParser() {}

    public static Relation parsePredicate(
            String potentialPredicate, RelationManager relationManager) throws NoRelationException {
        try {
            if (potentialPredicate.contains(":")) {
                String predicateName =
                        RegexUtils.getFirstMatch(
                                Pattern.compile("(?<=\\w+:).+"), potentialPredicate);
                return relationManager
                        .getRelationByName(predicateName)
                        .orElse(
                                relationManager
                                        .getRelationByRealName(predicateName)
                                        .orElse(
                                                new CustomRelation(
                                                        predicateName,
                                                        "",
                                                        new LinkedHashSet<>(),
                                                        new LinkedHashSet<>())));
            } else {
                // has to be a SpecialRelation
                return relationManager
                        .getRelationByRealName(potentialPredicate)
                        .orElseThrow(() -> new NoRelationException(potentialPredicate));
            }
        } catch (NoMatchFoundException e) {
            throw new NoRelationException(potentialPredicate);
        }
    }
}
