package org.archcnl.domain.common.io;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.archcnl.domain.common.io.exceptions.NoMatchFoundException;

public class RegexUtils {

    private RegexUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String getFirstMatch(Pattern pattern, String text) throws NoMatchFoundException {
        Matcher matcher = pattern.matcher(text);
        matcher.find();
        try {
            return matcher.group();
        } catch (IllegalStateException e) {
            throw new NoMatchFoundException(text, pattern);
        }
    }

    public static List<String> getAllMatches(Pattern pattern, String text) {
        List<String> allMatches = new LinkedList<>();
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            allMatches.add(matcher.group());
        }
        return allMatches;
    }
}
