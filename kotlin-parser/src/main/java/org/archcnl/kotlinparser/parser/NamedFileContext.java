package org.archcnl.kotlinparser.parser;

import org.archcnl.kotlinparser.grammar.KotlinParser;

public class NamedFileContext {
    private final KotlinParser.KotlinFileContext fileContext;
    private final String[] rulesNames;

    public NamedFileContext(KotlinParser.KotlinFileContext fileContext, String[] rulesNames) {
        this.fileContext = fileContext;
        this.rulesNames = rulesNames;
    }

    public KotlinParser.KotlinFileContext getFileContext() {
        return fileContext;
    }

    public String[] getRulesNames() {
        return rulesNames;
    }
}
