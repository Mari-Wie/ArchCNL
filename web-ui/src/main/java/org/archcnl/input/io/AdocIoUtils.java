package org.archcnl.input.io;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdocIoUtils {

    private AdocIoUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String getFirstMatch(Pattern pattern, String text) {
        Matcher matcher = pattern.matcher(text);
        matcher.find();
        return matcher.group();
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
