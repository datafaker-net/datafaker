package net.datafaker.transformations;

import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.datafaker.transformations.Casing.DEFAULT_CASING;
import static net.datafaker.transformations.SqlDialect.AuxiliaryConstants.DEFAULT_FIRST_ROW;
import static net.datafaker.transformations.SqlDialect.AuxiliaryConstants.DEFAULT_OTHER_ROWS;

public enum SqlDialect {
    ANSI("`"),
    BIGQUERY("`", Casing.UNCHANGED),
    CALCITE("\""),
    CLICKHOUSE("`"),
    EXASOL("\""),
    FIREBOLT("\""),
    H2("\""),
    INFOBRIGHT("`"),
    LUCIDDB("\""),
    MARIADB("`", Casing.TO_LOWER),
    MSSQL("[]"),
    MYSQL("`", Casing.UNCHANGED),
    NETEZZA("\""),
    ORACLE("\"", Casing.TO_UPPER,
        (columns, values, withKeywordsUppercase) -> {
            String insertAll = "INSERT ALL\n    INTO ";
            insertAll = withKeywordsUppercase ? insertAll.toUpperCase(Locale.ROOT) : insertAll.toLowerCase(Locale.ROOT);
            String valuesKeyWord = " VALUES ";
            valuesKeyWord = withKeywordsUppercase ? valuesKeyWord.toUpperCase(Locale.ROOT) : valuesKeyWord.toLowerCase(Locale.ROOT);
            return insertAll + columns.get() + valuesKeyWord + values.get();
        },
        (columns, values, withKeywordsUppercase) -> {
            String into = "    INTO ";
            into = withKeywordsUppercase ? into.toUpperCase(Locale.ROOT) : into.toLowerCase(Locale.ROOT);
            String valuesKeyWord = " VALUES ";
            valuesKeyWord = withKeywordsUppercase ? valuesKeyWord.toUpperCase(Locale.ROOT) : valuesKeyWord.toLowerCase(Locale.ROOT);
            return into + columns.get() + valuesKeyWord + values.get();
        }, withKeywordsUppercase -> "\n" + (withKeywordsUppercase ? "SELECT 1 FROM " : "select 1 from ") + "dual"),
    PARACCEL("\""),
    PHOENIX("\""),
    POSTGRES("\""),
    PRESTO("\"", Casing.UNCHANGED),
    REDSHIFT("\"", Casing.TO_LOWER),
    SNOWFLAKE("\""),
    TERADATA("\""),
    VERTICA("\"", Casing.UNCHANGED);
    private final String sqlQuoteIdentifier;
    private final Casing unquotedCasing;
    private final TriFunction<Supplier<String>, Supplier<String>, Boolean, String> batchFirstRow;
    private final TriFunction<Supplier<String>, Supplier<String>, Boolean, String> batchOtherRows;
    private final Function<Boolean, String> lastBatchRow;

    private static final String DEFAULT_BEFORE_EACH_BATCH_PREFIX = "       ";

    SqlDialect(String sqlQuoteIdentifier, Casing casing, TriFunction<Supplier<String>, Supplier<String>, Boolean, String> batchFirstRow,
        TriFunction<Supplier<String>, Supplier<String>, Boolean, String> batchOtherRows, Function<Boolean, String> lastBatchRow) {
        this.sqlQuoteIdentifier = sqlQuoteIdentifier;
        this.unquotedCasing = casing;
        this.batchFirstRow = batchFirstRow;
        this.batchOtherRows = batchOtherRows;
        this.lastBatchRow = lastBatchRow;
    }

    SqlDialect(String sqlQuoteIdentifier, TriFunction<Supplier<String>, Supplier<String>, Boolean, String> batchFirstRow,
        TriFunction<Supplier<String>, Supplier<String>, Boolean, String> batchOtherRows, Function<Boolean, String> lastBatchRow) {
        this(sqlQuoteIdentifier, DEFAULT_CASING, batchFirstRow, batchOtherRows, lastBatchRow);
    }

    SqlDialect(String sqlQuoteIdentifier, Casing casing) {
        this(sqlQuoteIdentifier, casing,
            DEFAULT_FIRST_ROW,
            DEFAULT_OTHER_ROWS,
            s -> "");
    }

    SqlDialect(String sqlQuoteIdentifier) {
        this(sqlQuoteIdentifier, DEFAULT_CASING);
    }

    public String getSqlQuoteIdentifier() {
        return sqlQuoteIdentifier;
    }

    public Casing getUnquotedCasing() {
        return unquotedCasing;
    }

    public static String getFirstRow(SqlDialect dialect, Supplier<String> input, Supplier<String> input2, boolean withKeywordUppercase) {
        return dialect == null
            ? DEFAULT_FIRST_ROW.apply(input, input2, withKeywordUppercase)
            : dialect.batchFirstRow.apply(input, input2, withKeywordUppercase);
    }

    public static String getOtherRow(SqlDialect dialect, Supplier<String> input, Supplier<String> input2, boolean withKeywordUppercase) {
        return dialect == null
            ? DEFAULT_OTHER_ROWS.apply(input, input2, withKeywordUppercase)
            : dialect.batchOtherRows.apply(input, input2, withKeywordUppercase);
    }

    public static String getLastRowSuffix(SqlDialect dialect, Boolean withKeywordUppercase) {
        return dialect == null
            ? ""
            : dialect.lastBatchRow.apply(withKeywordUppercase);
    }

    static class AuxiliaryConstants {
        static final TriFunction<Supplier<String>, Supplier<String>, Boolean, String> DEFAULT_FIRST_ROW = (supplier, supplier2, aBoolean) -> {
            String insertAll = "INSERT INTO ";
            insertAll = aBoolean ? insertAll.toUpperCase(Locale.ROOT) : insertAll.toLowerCase(Locale.ROOT);
            String values = "\nVALUES ";
            values = aBoolean ? values.toUpperCase(Locale.ROOT) : values.toLowerCase(Locale.ROOT);
            return insertAll + supplier.get() + values + supplier2.get();
        };
        static final TriFunction<Supplier<String>, Supplier<String>, Boolean, String> DEFAULT_OTHER_ROWS = (supplier, supplier2, aBoolean) -> DEFAULT_BEFORE_EACH_BATCH_PREFIX + supplier2.get();
    }
}
