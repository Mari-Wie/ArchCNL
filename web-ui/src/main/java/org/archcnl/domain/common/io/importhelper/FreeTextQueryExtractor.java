package org.archcnl.domain.common.io.importhelper;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.domain.common.io.AdocIoUtils;
import org.archcnl.domain.input.exceptions.NoMatchFoundException;
import org.archcnl.domain.output.model.query.FreeTextQuery;

public class FreeTextQueryExtractor {

    private static final Logger LOG = LogManager.getLogger(FreeTextQueryExtractor.class);

    private static final Pattern FREE_TEXT_QUERY_BEGIN =
            Pattern.compile("(?<=\\[role=\"freeTextQuery\"\\](\r\n?|\n)).+: \\([0-9]+\\)");

    private FreeTextQueryExtractor() {}

    public static List<FreeTextQuery> extractFreeTextQueries(String fileContent) {
        List<FreeTextQuery> queries = new LinkedList<>();

        AdocIoUtils.getAllMatches(FreeTextQueryExtractor.FREE_TEXT_QUERY_BEGIN, fileContent)
                .stream()
                .forEach(
                        queryStart -> {
                            try {
                                String name = queryStart.split(":")[0];
                                int length =
                                        Integer.parseInt(
                                                AdocIoUtils.getFirstMatch(
                                                        Pattern.compile("(?<=: \\()[0-9]+(?=\\))"),
                                                        queryStart));
                                String query =
                                        fileContent.split(Pattern.quote(queryStart))[1].substring(
                                                1, length + 1);
                                queries.add(new FreeTextQuery(name, query));
                            } catch (NumberFormatException | NoMatchFoundException e) {
                                FreeTextQueryExtractor.LOG.warn(e.getMessage());
                            }
                        });

        return queries;
    }
}
