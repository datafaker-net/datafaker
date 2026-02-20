package net.datafaker.internal.helper;

import org.jspecify.annotations.Nullable;

public class WordUtils {

    /**
     * @deprecated Use {@link #capitalizeWords(String)} instead -
     *      it doesn't accept nulls, and doesn't return nulls.
     */
    @Deprecated
    @Nullable
    public static String capitalize(@Nullable String input) {
        if (input == null) return null;
        return capitalizeWords(input);
    }

    public static String capitalizeWords(String input) {
        if (input.isEmpty()) return input;
        final char ch0 = input.charAt(0);
        if (Character.isUpperCase(ch0)) return input;
        return Character.toUpperCase(ch0) + input.substring(1);
    }
}
