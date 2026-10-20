package net.datafaker.transformations.sql;

import org.jspecify.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

import static net.datafaker.transformations.Transformer.LINE_SEPARATOR;
import static net.datafaker.transformations.sql.Casing.DEFAULT_CASING;
import static net.datafaker.transformations.sql.SqlDialect.AuxiliaryConstants.BACKSLASH_ESCAPE;
import static net.datafaker.transformations.sql.SqlDialect.AuxiliaryConstants.DEFAULT_FIRST_ROW;
import static net.datafaker.transformations.sql.SqlDialect.AuxiliaryConstants.DEFAULT_OTHER_ROWS;
import static net.datafaker.transformations.sql.SqlTransformer.SQLKeyWords.INSERT_ALL;
import static net.datafaker.transformations.sql.SqlTransformer.SQLKeyWords.INSERT_INTO;
import static net.datafaker.transformations.sql.SqlTransformer.SQLKeyWords.INTO;
import static net.datafaker.transformations.sql.SqlTransformer.SQLKeyWords.ROW;
import static net.datafaker.transformations.sql.SqlTransformer.SQLKeyWords.SELECT_1_FROM_DUAL;
import static net.datafaker.transformations.sql.SqlTransformer.SQLKeyWords.NAMED_STRUCT;
import static net.datafaker.transformations.sql.SqlTransformer.SQLKeyWords.VALUES;

public enum SqlDialect {
    ANSI("`"),
    BIGQUERY("`", Casing.UNCHANGED, BACKSLASH_ESCAPE),
    CALCITE("\""),
    CLICKHOUSE("`", BACKSLASH_ESCAPE),
    EXASOL("\""),
    FIREBOLT("\""),
    H2("\""),
    INFOBRIGHT("`", BACKSLASH_ESCAPE),
    LUCIDDB("\""),
    MARIADB("`", Casing.TO_LOWER, BACKSLASH_ESCAPE),
    MSSQL("[]"),
    MYSQL("`", Casing.UNCHANGED, BACKSLASH_ESCAPE),
    NETEZZA("\""),
    ORACLE("\"", Casing.TO_UPPER,
        (columns, values, caze) -> {
            final String insertAll = INSERT_ALL.getValue(caze) + System.lineSeparator() + "    " + INTO.getValue(caze) + " ";
            final String valuesKeyWord = " " + VALUES.getValue(caze) + " ";
            return insertAll + columns.get() + valuesKeyWord + values.get();
        },
        (columns, values, caze) -> {
            final String into = "    " + INTO.getValue(caze) + " ";
            final String valuesKeyWord = " " + VALUES.getValue(caze) + " ";
            return into + columns.get() + valuesKeyWord + values.get();
        }, caze -> LINE_SEPARATOR + SELECT_1_FROM_DUAL.getValue(caze)),
    PARACCEL("\""),
    PHOENIX("\""),
    POSTGRES("\""),
    PRESTO("\"", Casing.UNCHANGED),
    REDSHIFT("\"", Casing.TO_LOWER),
    SNOWFLAKE("\""),
    SPARKSQL("`", BACKSLASH_ESCAPE) {

        @Override
        public String getCompositePrefix(SqlTransformer.Case caze) {
            return NAMED_STRUCT.getValue(caze);
        }

        @Override
        public String getArrayStart() {
            return "(";
        }

        @Override
        public String getArrayEnd() {
            return ")";
        }

        @Override
        public String getFieldPrefix(String fieldName) {
            return fieldName;
        }
    },
    TERADATA("\""),
    VERTICA("\"", Casing.UNCHANGED);
    private final String sqlQuoteIdentifier;
    private final Casing unquotedCasing;
    private final TriFunction<Supplier<String>, Supplier<String>, SqlTransformer.Case, String> batchFirstRow;
    private final TriFunction<Supplier<String>, Supplier<String>, SqlTransformer.Case, String> batchOtherRows;
    private final Function<SqlTransformer.Case, String> lastBatchRow;
    private final boolean backslashEscape;

    private static final String DEFAULT_BEFORE_EACH_BATCH_PREFIX = "       ";

    SqlDialect(String sqlQuoteIdentifier, Casing casing, boolean backslashEscape,
               TriFunction<Supplier<String>, Supplier<String>, SqlTransformer.Case, String> batchFirstRow,
               TriFunction<Supplier<String>, Supplier<String>, SqlTransformer.Case, String> batchOtherRows, Function<SqlTransformer.Case, String> lastBatchRow) {
        this.sqlQuoteIdentifier = sqlQuoteIdentifier;
        this.unquotedCasing = casing;
        this.backslashEscape = backslashEscape;
        this.batchFirstRow = batchFirstRow;
        this.batchOtherRows = batchOtherRows;
        this.lastBatchRow = lastBatchRow;
    }

    SqlDialect(String sqlQuoteIdentifier, Casing casing, TriFunction<Supplier<String>, Supplier<String>, SqlTransformer.Case, String> batchFirstRow,
               TriFunction<Supplier<String>, Supplier<String>, SqlTransformer.Case, String> batchOtherRows, Function<SqlTransformer.Case, String> lastBatchRow) {
        this(sqlQuoteIdentifier, casing, false, batchFirstRow, batchOtherRows, lastBatchRow);
    }

    SqlDialect(String sqlQuoteIdentifier, TriFunction<Supplier<String>, Supplier<String>, SqlTransformer.Case, String> batchFirstRow,
               TriFunction<Supplier<String>, Supplier<String>, SqlTransformer.Case, String> batchOtherRows, Function<SqlTransformer.Case, String> lastBatchRow) {
        this(sqlQuoteIdentifier, DEFAULT_CASING, batchFirstRow, batchOtherRows, lastBatchRow);
    }

    SqlDialect(String sqlQuoteIdentifier, Casing casing, boolean backslashEscape) {
        this(sqlQuoteIdentifier, casing, backslashEscape,
            DEFAULT_FIRST_ROW,
            DEFAULT_OTHER_ROWS,
            s -> "");
    }

    SqlDialect(String sqlQuoteIdentifier, Casing casing) {
        this(sqlQuoteIdentifier, casing, false);
    }

    SqlDialect(String sqlQuoteIdentifier, boolean backslashEscape) {
        this(sqlQuoteIdentifier, DEFAULT_CASING, backslashEscape);
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

    public static String getFirstRow(@Nullable SqlDialect dialect, Supplier<String> input, Supplier<String> input2, SqlTransformer.Case keywordCase) {
        return dialect == null
            ? DEFAULT_FIRST_ROW.apply(input, input2, keywordCase) : dialect.batchFirstRow.apply(input, input2, keywordCase);
    }

    public static String getOtherRow(@Nullable SqlDialect dialect, Supplier<String> input, Supplier<String> input2, SqlTransformer.Case keywordCase) {
        return dialect == null
            ? DEFAULT_OTHER_ROWS.apply(input, input2, keywordCase) : dialect.batchOtherRows.apply(input, input2, keywordCase);
    }

    public static String getLastRowSuffix(@Nullable SqlDialect dialect, SqlTransformer.Case caze) {
        return dialect == null ? "" : dialect.lastBatchRow.apply(caze);
    }

    public String getCompositePrefix(SqlTransformer.Case caze) {
        return ROW.getValue(caze);
    }

    /**
     * Fields might have a prefix such as field name.
     * Default "" works for most dialects.
     */
    public String getFieldPrefix(String fieldName) {
        return "";
    }

    public String getArrayStart() {
        return "[";
    }

    public String getArrayEnd() {
        return "]";
    }

    /**
     * Escapes a value placed inside a string literal delimited by {@code quote}.
     * Most dialects double the quote ({@code 'It''s'}) as standard SQL requires. Dialects created with
     * {@code BACKSLASH_ESCAPE} put a backslash before the quote and before a literal backslash
     * ({@code 'It\'s'}, {@code 'C:\\temp'}).
     */
    public String escapeStringLiteral(String value, char quote) {
        return escape(value, quote, backslashEscape ? '\\' : quote);
    }

    /**
     * Puts {@code escape} before every {@code quote} and every {@code escape} in the value.
     * When both are the same character, this doubles it.
     */
    static String escape(String value, char quote, char escape) {
        StringBuilder result = new StringBuilder(value.length());
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == quote || c == escape) {
                result.append(escape);
            }
            result.append(c);
        }
        return result.toString();
    }

    static class AuxiliaryConstants {
        static final boolean BACKSLASH_ESCAPE = true;

        static final TriFunction<Supplier<String>, Supplier<String>, SqlTransformer.Case, String> DEFAULT_FIRST_ROW = (supplier, supplier2, caze) -> {
            final String insertAll = INSERT_INTO.getValue(caze) + " ";
            final String values = LINE_SEPARATOR + VALUES.getValue(caze) + " ";
            return insertAll + supplier.get() + values + supplier2.get();
        };
        static final TriFunction<Supplier<String>, Supplier<String>, SqlTransformer.Case, String> DEFAULT_OTHER_ROWS =
            (supplier, supplier2, caze) -> DEFAULT_BEFORE_EACH_BATCH_PREFIX + supplier2.get();
    }
}
