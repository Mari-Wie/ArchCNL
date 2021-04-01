package org.archcnl.architecturedescriptionparser;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.ast.Block;
import org.asciidoctor.ast.Document;
import org.asciidoctor.ast.StructuralNode;

public class AsciiDocCNLSentenceExtractor {

    private static final String ARCHITECTURE_RULE_TAG = "rule";
    private static final String MAPPING_RULE_TAG = "mapping";

    private final Document document;

    public AsciiDocCNLSentenceExtractor(Path pathToRuleFile) {
        Asciidoctor ascii = Asciidoctor.Factory.create();
        document = ascii.loadFile(pathToRuleFile.toFile(), new HashMap<String, Object>());
        ascii.close();
    }

    public List<String> extractArchitectureRules() {
        return extractRules(ARCHITECTURE_RULE_TAG);
    }

    public List<String> extractMappingRules() {
        return extractRules(MAPPING_RULE_TAG);
    }

    private List<String> extractRules(String ruleTag) {
        List<StructuralNode> nodes = parseAsciidocFile(ruleTag);
        List<String> sentences = new ArrayList<>();

        nodes.stream()
                .map(
                        node -> {
                            return ((Block) node).getLines();
                        })
                .forEach(sentences::addAll);

        return sentences;
    }

    private List<StructuralNode> parseAsciidocFile(String tag) {
        Map<Object, Object> selector = new HashMap<>();
        selector.put("role", tag);
        return document.findBy(selector);
    }
}
