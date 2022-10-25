package net.datafaker.transformations.sql;

public enum Casing {
    /** The case of identifiers is not changed. */
    UNCHANGED,

    /** Identifiers are converted to upper-case. */
    TO_UPPER,

    /** Identifiers are converted to lower-case. */
    TO_LOWER;

    public static final Casing DEFAULT_CASING = Casing.TO_UPPER;
}
