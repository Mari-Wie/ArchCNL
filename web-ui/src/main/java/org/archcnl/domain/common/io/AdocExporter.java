package org.archcnl.domain.common.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.io.exporthelper.ConceptsToStringTransformer;
import org.archcnl.domain.common.io.exporthelper.QueryToStringTransformer;
import org.archcnl.domain.common.io.exporthelper.RelationsToStringTransformer;
import org.archcnl.domain.common.io.exporthelper.RulesToStringTransformer;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRuleManager;
import org.archcnl.domain.output.model.query.FreeTextQuery;
import org.archcnl.domain.output.model.query.Query;

public class AdocExporter {

    public void writeToAdoc(
            File file,
            ArchitectureRuleManager ruleManager,
            ConceptManager conceptManager,
            RelationManager relationManager,
            List<Query> customQueries,
            List<FreeTextQuery> fullTextQueries)
            throws IOException {

        String rulesString =
                RulesToStringTransformer.constructArchRuleString(
                        ruleManager.getArchitectureRules());

        String conceptsString =
                ConceptsToStringTransformer.constructConceptString(
                        conceptManager.getCustomConcepts());

        String relationsString =
                RelationsToStringTransformer.constructRelationString(
                        relationManager.getCustomRelations());

        String customQueriesString =
                QueryToStringTransformer.constructCustomQueryString(customQueries);

        String fullTextQueriesString =
                QueryToStringTransformer.constructFullTextQueryString(fullTextQueries);

        FileUtils.writeStringToFile(
                file,
                rulesString
                        + conceptsString
                        + relationsString
                        + customQueriesString
                        + fullTextQueriesString,
                StandardCharsets.UTF_8);
    }
}
