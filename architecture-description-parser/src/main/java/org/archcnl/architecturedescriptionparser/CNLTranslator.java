package org.archcnl.architecturedescriptionparser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.common.datatypes.ArchitectureRule;
import org.archcnl.common.datatypes.RuleType;
import org.architecture.cnl.CNL2OWLGenerator;

public class CNLTranslator {
    private static final Logger LOG = LogManager.getLogger(CNLTranslator.class);

    private CNL2OWLGenerator generator;

    public CNLTranslator() {
        generator = new CNL2OWLGenerator();
    }

    public List<ArchitectureRule> translate(List<String> cnlSentences, String outputDirectory) {
        ArrayList<ArchitectureRule> rules = new ArrayList<>();

        for (int i = 0; i < cnlSentences.size(); i++) {
            String line = cnlSentences.get(i);
            String rulePath = "tmp_" + i + ".architecture";
            String ontologyPath = outputDirectory + "/architecture" + i + ".owl";

            LOG.debug("Writing the rule to a seperate file: " + rulePath);

            try {
                writeSentenceToFile(line, i, rulePath);
                addRule(line, i, rulePath, ontologyPath, rules);
            } catch (IOException e) {
                LOG.error("Cannot access the temporary file \"" + rulePath + "\"!");
                LOG.error("Ignoring the following rule: " + line);
            } finally {
                cleanUp(rulePath);
            }
        }

        return rules;
    }

    private void writeSentenceToFile(String line, int index, String path) throws IOException {
        File f = new File(path);
        FileUtils.writeStringToFile(f, line + "\n", (Charset) null, true);
    }

    private void addRule(
            String sentence,
            int index,
            String rulePath,
            String ontologyPath,
            List<ArchitectureRule> rules) {
        LOG.debug("Transforming the rule from CNL to an OWL constraint ...");
        RuleType typeOfParsedRule = generator.transformCNLFile(rulePath, ontologyPath);

        if (typeOfParsedRule == null) {
            LOG.error("The parser could not parse the following rule: " + sentence);
        } else {
            LOG.info("Added an architecture rule:");
            LOG.debug("Rule Id      : " + index);
            LOG.debug("Rule         : " + sentence);
            LOG.debug("Type         : " + typeOfParsedRule.name());
            LOG.debug("OntologyPath : " + ontologyPath);

            rules.add(new ArchitectureRule(index, sentence, typeOfParsedRule, ontologyPath));
        }
    }

    private void cleanUp(String path) {
        File f = new File(path);
        if (f.exists()) {
            f.delete();
        }
    }
}
