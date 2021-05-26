package org.archcnl.kotlinparser.visitor;

import org.archcnl.kotlinparser.grammar.KotlinParserBaseVisitor;

public class NamedBaseVisitor extends KotlinParserBaseVisitor<Void> {
    private final String[] rulesNames;

    public NamedBaseVisitor(String[] rulesNames) {
        this.rulesNames = rulesNames;
    }
}
