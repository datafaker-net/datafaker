package net.datafaker.internal.helper;

import org.jspecify.annotations.Nullable;

public class WordUtils {

    /**
     * @deprecated Use {@link #capitalizeWord(String)} instead -
     *      it doesn't accept nulls, and doesn't return nulls.
     */
    @Deprecated(forRemoval = true)
    @Nullable
    @SuppressWarnings("DeprecatedIsStillUsed")
    public static String capitalize(@Nullable String input) {
        if (input == null) return null;
        return capitalizeWord(input);
    }

    @Deprecated(forRemoval = true)
    public static String capitalizeWords(String input) {
        return capitalizeWord(input);
    }

    /**
     * Replace the first letter by capital letter
     * @param input e.g. "hello, world"
     * @return e.g. "Hello, world"
     */
    public static String capitalizeWord(String input) {
        if (input.isEmpty()) return input;
        final char ch0 = input.charAt(0);
        if (Character.isUpperCase(ch0)) return input;
        return Character.toUpperCase(ch0) + input.substring(1);
    }
}
