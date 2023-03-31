package net.datafaker.internal.helper;


public class WordUtils {

    public static String capitalize(String input) {
        if (input == null) return null;
        if (input.isEmpty()) return input;
        final char ch0 = input.charAt(0);
        if (Character.isUpperCase(ch0)) return input;
        return Character.toUpperCase(ch0) + input.substring(1);
    }
}
