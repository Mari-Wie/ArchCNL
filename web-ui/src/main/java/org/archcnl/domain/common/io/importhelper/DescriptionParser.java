package org.archcnl.domain.common.io.importhelper;

import com.google.common.annotations.VisibleForTesting;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.domain.common.io.RegexUtils;
import org.archcnl.domain.common.io.exceptions.NoMatchFoundException;

public class DescriptionParser {

    private static final Logger LOG = LogManager.getLogger(DescriptionParser.class);

    private static final Pattern CONCEPT_DESCRIPTION_PATTERN =
            Pattern.compile("\\[role=\"description\"\\](\r\n?|\n)(is\\w+:)[\\w\\. ]+(\r\n?|\n)");
    private static final Pattern RELATION_DESCRIPTION_PATTERN =
            Pattern.compile("\\[role=\"description\"\\](\r\n?|\n)(.+Mapping:)[\\w\\. ]+(\r\n?|\n)");

    private static final Pattern CONCEPT_DESCRIPTION_CONTENT =
            Pattern.compile(
                    "(?<=\\[role=\"description\"\\](\r\n?|\n)(is\\w+: ))[\\w\\. ]+((?=\r\n?|\n))");

    private static final Pattern RELATION_DESCRIPTION_CONTENT =
            Pattern.compile(
                    "(?<=\\[role=\"description\"\\](\r\n?|\n)(.+Mapping: ))[\\w\\. ]+((?=\r\n?|\n))");

    private DescriptionParser() {}

    public static Map<String, String> extractConceptDescriptions(
            String fileContent, Pattern conceptMappingName) {
        final Map<String, String> conceptDescriptions = new HashMap<>();

        RegexUtils.getAllMatches(DescriptionParser.CONCEPT_DESCRIPTION_PATTERN, fileContent)
                .stream()
                .forEach(
                        conceptDescription -> {
                            try {
                                final String name =
                                        RegexUtils.getFirstMatch(
                                                conceptMappingName, conceptDescription);
                                final String description =
                                        RegexUtils.getFirstMatch(
                                                DescriptionParser.CONCEPT_DESCRIPTION_CONTENT,
                                                conceptDescription);
                                conceptDescriptions.put(name, description);
                            } catch (final NoMatchFoundException e) {
                                DescriptionParser.LOG.warn(e.getMessage());
                            }
                        });

        return conceptDescriptions;
    }

    public static Map<String, String> extractRelationDescriptions(
            String fileContent, Pattern relationMappingName) {
        final Map<String, String> relationDescriptions = new HashMap<>();

        RegexUtils.getAllMatches(DescriptionParser.RELATION_DESCRIPTION_PATTERN, fileContent)
                .stream()
                .forEach(
                        relationDescription -> {
                            try {
                                final String name =
                                        RegexUtils.getFirstMatch(
                                                relationMappingName, relationDescription);
                                final String description =
                                        RegexUtils.getFirstMatch(
                                                DescriptionParser.RELATION_DESCRIPTION_CONTENT,
                                                relationDescription);
                                relationDescriptions.put(name, description);
                            } catch (final NoMatchFoundException e) {
                                DescriptionParser.LOG.warn(e.getMessage());
                            }
                        });

        return relationDescriptions;
    }

    @VisibleForTesting
    public static Pattern getConceptDescriptionPattern() {
        return CONCEPT_DESCRIPTION_PATTERN;
    }

    @VisibleForTesting
    public static Pattern getRelationDescriptionPattern() {
        return RELATION_DESCRIPTION_PATTERN;
    }
}
