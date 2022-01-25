package org.archcnl.domain.common.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.apache.commons.io.FileUtils;
import org.archcnl.domain.TestUtils;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.io.importhelper.MappingExtractor;
import org.archcnl.domain.common.io.importhelper.RuleExtractor;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRuleManager;
import org.archcnl.domain.output.model.query.FreeTextQuery;
import org.archcnl.domain.output.model.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdocEndToEndTest {

    private ArchitectureRuleManager ruleManager;
    private ConceptManager conceptManager;
    private RelationManager relationManager;
    private Queue<FreeTextQuery> freeTextQueryQueue;
    private Queue<Query> customQueryQueue;

    @BeforeEach
    private void setup() throws ConceptDoesNotExistException {
        ruleManager = new ArchitectureRuleManager();
        conceptManager = new ConceptManager();
        relationManager = new RelationManager(conceptManager);
        freeTextQueryQueue = new LinkedList<>();
        customQueryQueue = new LinkedList<>();
    }

    @SuppressWarnings("unchecked")
    @Test
    void givenRuleFile_whenImportingAndExportingFile_thenWrittenFileIsAsExpected()
            throws IOException, RelationDoesNotExistException {
        // given
        final File ruleFile = new File("src/test/resources/architecture-documentation.adoc");

        // when
        AdocImporter adocImporter = new AdocImporter();
        adocImporter.readFromAdoc(
                ruleFile,
                ruleManager,
                conceptManager,
                relationManager,
                freeTextQueryQueue,
                customQueryQueue);

        final File writtenFile = new File("src/test/resources/EndToEndTest.adoc");
        AdocExporter adocExporter = new AdocExporter();
        adocExporter.writeToAdoc(
                writtenFile,
                ruleManager,
                conceptManager,
                relationManager,
                (List<Query>) customQueryQueue,
                (List<FreeTextQuery>) freeTextQueryQueue);

        // then
        String expectedContent = FileUtils.readFileToString(ruleFile, StandardCharsets.UTF_8);
        String actualContent = FileUtils.readFileToString(writtenFile, StandardCharsets.UTF_8);

        assertEquals(
                TestUtils.numberOfMatches(RuleExtractor.getRuleContentPattern(), expectedContent),
                TestUtils.numberOfMatches(RuleExtractor.getRuleContentPattern(), actualContent));
        assertEquals(
                TestUtils.numberOfMatches(
                        MappingExtractor.getConceptMappingPattern(), expectedContent),
                TestUtils.numberOfMatches(
                        MappingExtractor.getConceptMappingPattern(), actualContent));
        assertEquals(
                TestUtils.numberOfMatches(
                        MappingExtractor.getRelationMappingPattern(), expectedContent),
                TestUtils.numberOfMatches(
                        MappingExtractor.getRelationMappingPattern(), actualContent));

        assertTrue(
                TestUtils.doAllMatchesExistInSecondString(
                        RuleExtractor.getRuleContentPattern(), expectedContent, actualContent));
        assertTrue(
                TestUtils.doAllMatchesExistInSecondString(
                        MappingExtractor.getConceptMappingPattern(),
                        expectedContent,
                        actualContent));
        assertTrue(
                TestUtils.doAllMatchesExistInSecondString(
                        MappingExtractor.getRelationMappingPattern(),
                        expectedContent,
                        actualContent));
    }
}
