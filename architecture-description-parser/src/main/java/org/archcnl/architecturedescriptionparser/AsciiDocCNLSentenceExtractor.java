package org.archcnl.architecturedescriptionparser;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.common.datatypes.ArchitectureRule;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.ast.Block;
import org.asciidoctor.ast.Document;
import org.asciidoctor.ast.StructuralNode;

public class AsciiDocCNLSentenceExtractor {

    private static final String ARCHITECTURE_RULE_TAG = "rule";
    private static final String MAPPING_RULE_TAG = "mapping";
    private static final String VALIDFROM_RULE_TAG = "validFrom";
    private static final String VALIDUNTIL_RULE_TAG = "validUntil";

    private static final Logger LOG = LogManager.getLogger(AsciiDocCNLSentenceExtractor.class);

    private final Document document;

    public AsciiDocCNLSentenceExtractor(Path pathToRuleFile) {
        Asciidoctor ascii = Asciidoctor.Factory.create();
        document = ascii.loadFile(pathToRuleFile.toFile(), new HashMap<String, Object>());
        ascii.close();
    }

    public List<ArchitectureRule> extractArchitectureRules() {
        return extractArchitectureRules(ARCHITECTURE_RULE_TAG);
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

    private List<ArchitectureRule> extractArchitectureRules(String ruleTag) {
        List<StructuralNode> nodes = parseAsciidocFile(ruleTag);
        return parseRulesWithValidity(nodes);
    }

    private List<StructuralNode> parseAsciidocFile(String tag) {
        Map<Object, Object> selector = new HashMap<>();
        selector.put("role", tag);
        return document.findBy(selector);
    }

    private List<ArchitectureRule> parseRulesWithValidity(List<StructuralNode> inputNodes) {
        List<ArchitectureRule> result = new ArrayList<>();

        for (StructuralNode structuralNode : inputNodes) {
            LocalDate validFrom = parseDateFromAttribute(structuralNode, VALIDFROM_RULE_TAG);
            LocalDate validUntil = parseDateFromAttribute(structuralNode, VALIDUNTIL_RULE_TAG);
            if (ruleIsValid(validFrom, validUntil)) {
                result.add(
                        ArchitectureRule.createArchRuleForParsing(
                                ((Block) structuralNode).getLines().get(0), validFrom, validUntil));
            }
        }
        return result;
    }

    private LocalDate parseDateFromAttribute(StructuralNode structuralNode, String ruleTag) {
        String dateValue = (String) structuralNode.getAttribute(ruleTag);
        if (dateValue != null && !dateValue.isEmpty()) {
            try {
                return LocalDate.parse((String) structuralNode.getAttribute(ruleTag));
            } catch (IllegalArgumentException e) {
                LOG.warn(
                        "Rule: "
                                + ((Block) structuralNode).getLines()
                                + " has an invalid validFrom date format.");
                return null;
            }
        }
        return null;
    }

    private boolean ruleIsValid(LocalDate validFrom, LocalDate validUntil) {
        /*
         * Now date is used here, because the connection between ruleValidity and GitMaxDates was not working as intended.
         * If there is a future way of relating them and check for the git dates, this check is irrelevant.
         */
        LocalDate now = LocalDate.now();
        if (validFrom != null && validUntil != null) {
            return validFrom.isBefore(now) && validUntil.isAfter(now);
        }
        if (validFrom != null) {
            return validFrom.isBefore(now);
        }
        if (validUntil != null) {
            return validUntil.isAfter(now);
        }
        return true;
    }
}
