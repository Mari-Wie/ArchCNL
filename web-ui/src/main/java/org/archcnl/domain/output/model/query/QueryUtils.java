package org.archcnl.domain.output.model.query;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.archcnl.domain.output.model.query.attribute.QueryNamespace;

public class QueryUtils {

    private QueryUtils() {}

    private static final Path VIOLATIONS_SUBJECT_PREDICATE_OBJECT =
            Path.of("violationsSubjectPredicateObject.sparql");
    private static final Path rootDir = Path.of("./src/main/resources/queries/");

    private static final String EXCEPTION_TEXT =
            "# An error occured while trying to load the query.\r\n" + "SELECT * WHERE {}";

    public static String getDefaultQuery() {

        Path queryPath = rootDir.resolve(VIOLATIONS_SUBJECT_PREDICATE_OBJECT);
        try {
            return Files.readString(queryPath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return EXCEPTION_TEXT;
        }
    }

    public static Set<QueryNamespace> getNamespaces() {
        final QueryNamespace rdf =
                new QueryNamespace("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns");
        final QueryNamespace owl = new QueryNamespace("owl", "http://www.w3.org/2002/07/owl");
        final QueryNamespace rdfs =
                new QueryNamespace("rdfs", "http://www.w3.org/2000/01/rdf-schema");
        final QueryNamespace xsd = new QueryNamespace("xsd", "http://www.w3.org/2001/XMLSchema");
        final QueryNamespace conformance =
                new QueryNamespace(
                        "conformance", "http://arch-ont.org/ontologies/architectureconformance");
        final QueryNamespace famix =
                new QueryNamespace("famix", "http://arch-ont.org/ontologies/famix.owl");
        final QueryNamespace architecture =
                new QueryNamespace(
                        "architecture", "http://www.arch-ont.org/ontologies/architecture.owl");
        final QueryNamespace main =
                new QueryNamespace("main", "http://arch-ont.org/ontologies/main.owl");
        return new LinkedHashSet<>(
                Arrays.asList(rdf, owl, rdfs, xsd, conformance, famix, architecture, main));
    }
}
