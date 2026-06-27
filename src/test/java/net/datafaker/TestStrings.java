package net.datafaker;

public final class TestStrings {

    private TestStrings() {
    }

    /** Maps text-block newlines to the platform line separator used by transformers. */
    public static String lines(String textBlock) {
        return textBlock.replace("\n", System.lineSeparator());
    }

    /** Like {@link #lines(String)}, but strips one trailing platform line separator. */
    public static String linesTrimTrailing(String textBlock) {
        String result = lines(textBlock);
        String sep = System.lineSeparator();
        return result.endsWith(sep) ? result.substring(0, result.length() - sep.length()) : result;
    }
}
