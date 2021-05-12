package org.archcnl.kotlinparser.parser;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.owlify.famix.kotlin.grammar.KotlinLexer;
import org.archcnl.owlify.famix.kotlin.grammar.KotlinParser;

public class KtParser {
	private static final Logger LOG = LogManager.getLogger(KtParser.class);

	public void parse(String content) {
		var charStream = CharStreams.fromString(content);
		var lexer = new KotlinLexer(charStream);
		var tokenStream = new CommonTokenStream(lexer);
		var parser = new KotlinParser(tokenStream);
		var parserTree = parser.kotlinFile();
		
		LOG.debug("Done parsing this file: {}", parserTree.toStringTree(parser));
	}
}
