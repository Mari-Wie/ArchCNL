package org.archcnl.kotlinparser.visitor;

import java.util.Arrays;
import java.util.List;
import org.archcnl.kotlinparser.grammar.KotlinParserBaseVisitor;

public class NamedBaseVisitor extends KotlinParserBaseVisitor<Void> {
    private final String[] rulesNames;

    public NamedBaseVisitor(String[] rulesNames) {
        this.rulesNames = rulesNames;
    }

    protected List<String> getRuleNamesAsList() {
        return Arrays.asList(rulesNames);
    }

    public String[] getRulesNames() {
        return rulesNames;
    }
}
