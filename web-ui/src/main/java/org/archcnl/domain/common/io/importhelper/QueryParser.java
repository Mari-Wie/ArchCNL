package org.archcnl.domain.common.io.importhelper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.common.io.RegexUtils;
import org.archcnl.domain.common.io.exceptions.NoMatchFoundException;
import org.archcnl.domain.common.io.importhelper.exceptions.NoTripletException;
import org.archcnl.domain.output.model.query.FreeTextQuery;
import org.archcnl.domain.output.model.query.Query;
import org.archcnl.domain.output.model.query.SelectClause;
import org.archcnl.domain.output.model.query.WhereClause;

public class QueryParser {

    private static final Logger LOG = LogManager.getLogger(QueryParser.class);

    private static final Pattern FREE_TEXT_QUERY_BEGIN =
            Pattern.compile("(?<=\\[role=\"freeTextQuery\"\\](\r\n?|\n)).+: \\([0-9]+\\)");

    private static final Pattern FREE_TEXT_QUERY_LENGTH =
            Pattern.compile("(?<=: \\()[0-9]+(?=\\))");

    private static final Pattern CUSTOM_QUERY =
            Pattern.compile(
                    "(?<=\\[role=\"customQuery\"\\](\r\n?|\n)).+: \\(SELECT( \\?\\w+)*\\)\\(WHERE .*\\)");

    private static final Pattern SELECT_CONTENT_PATTERN =
            Pattern.compile("(?<=\\(SELECT)( \\?\\w+)*(?=\\))");

    private static final Pattern WHERE_CONTENT_PATTERN = Pattern.compile("(?<=\\(WHERE ).*(?=\\))");

    private QueryParser() {}

    public static List<FreeTextQuery> extractFreeTextQueries(String fileContent) {
        List<FreeTextQuery> queries = new LinkedList<>();

        RegexUtils.getAllMatches(QueryParser.FREE_TEXT_QUERY_BEGIN, fileContent).stream()
                .forEach(
                        queryStart -> {
                            try {
                                String name = queryStart.split(":")[0];
                                int length =
                                        Integer.parseInt(
                                                RegexUtils.getFirstMatch(
                                                        QueryParser.FREE_TEXT_QUERY_LENGTH,
                                                        queryStart));
                                String query =
                                        fileContent.split(Pattern.quote(queryStart))[1].substring(
                                                1, length + 1);
                                queries.add(new FreeTextQuery(name, query));
                            } catch (NumberFormatException | NoMatchFoundException e) {
                                QueryParser.LOG.warn(e.getMessage());
                            }
                        });

        return queries;
    }

    public static List<Query> extractCustomQueries(
            final String fileContent,
            final RelationManager relationManager,
            final ConceptManager conceptManager) {
        List<Query> queries = new LinkedList<>();

        RegexUtils.getAllMatches(QueryParser.CUSTOM_QUERY, fileContent).stream()
                .forEach(
                        potentialCustomQuery -> {
                            String name = potentialCustomQuery.split(":")[0];
                            try {
                                Set<Variable> select =
                                        parseSelectVariables(
                                                RegexUtils.getFirstMatch(
                                                        QueryParser.SELECT_CONTENT_PATTERN,
                                                        potentialCustomQuery));
                                AndTriplets where =
                                        MappingParser.parseWhenPart(
                                                RegexUtils.getFirstMatch(
                                                        QueryParser.WHERE_CONTENT_PATTERN,
                                                        potentialCustomQuery),
                                                relationManager,
                                                conceptManager);
                                queries.add(
                                        new Query(
                                                name,
                                                new SelectClause(select),
                                                new WhereClause(where)));

                            } catch (NoMatchFoundException | NoTripletException e) {
                                QueryParser.LOG.warn(e.getMessage());
                            }
                        });

        return queries;
    }

    private static Set<Variable> parseSelectVariables(String selectContentString) {
        Set<Variable> variables = new HashSet<>();
        String[] variableNames = selectContentString.split(" ");
        variableNames = Arrays.copyOfRange(variableNames, 1, variableNames.length);
        for (String variableName : variableNames) {
            if (variableName.startsWith("?")) {
                variableName = variableName.substring(1);
            }
            variables.add(new Variable(variableName));
        }
        return variables;
    }
}
