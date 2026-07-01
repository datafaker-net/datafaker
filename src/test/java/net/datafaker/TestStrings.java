package net.datafaker;

public final class TestStrings {

    private TestStrings() {
    }

    /** Maps text-block newlines to the platform line separator used by transformers. */
    public static String lines(String textBlock) {
        return textBlock.replace("\n", System.lineSeparator());
    }
}
