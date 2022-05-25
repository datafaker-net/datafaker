package net.datafaker.internal.helper;

import java.util.Locale;

public class WordUtils {

    public static String capitalize(String input) {
        if (input == null) return null;
        if (input.equals("")) return "";
        return input.substring(0, 1).toUpperCase(Locale.ROOT) + input.substring(1);
    }
}
