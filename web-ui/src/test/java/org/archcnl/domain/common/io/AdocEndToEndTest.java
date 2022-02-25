package org.archcnl.domain.common.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.apache.commons.io.FileUtils;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.exceptions.ConceptDoesNotExistException;
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
            throws IOException {
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
        String expectedContent =
                FileUtils.readFileToString(ruleFile, StandardCharsets.UTF_8).replaceAll("\r", "");
        String actualContent = FileUtils.readFileToString(writtenFile, StandardCharsets.UTF_8);

        assertEquals(expectedContent, actualContent);
    }
}
