package net.datafaker.transformations.sql;

import java.util.function.Function;
import java.util.function.Supplier;

import static net.datafaker.transformations.Transformer.LINE_SEPARATOR;
import static net.datafaker.transformations.sql.Casing.DEFAULT_CASING;
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
    TERADATA("\""),
    VERTICA("\"", Casing.UNCHANGED),
    SPARKSQL("`",
        (columns, values, caze) -> { throw SqlDialect.AuxiliaryConstants.BATCH_UNSUPPORTED; },
        (columns, values, caze) -> { throw SqlDialect.AuxiliaryConstants.BATCH_UNSUPPORTED; },
        (caze) -> { throw SqlDialect.AuxiliaryConstants.BATCH_UNSUPPORTED; }
    ) {

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
    };

    private final String sqlQuoteIdentifier;
    private final Casing unquotedCasing;
    private final TriFunction<Supplier<String>, Supplier<String>, SqlTransformer.Case, String> batchFirstRow;
    private final TriFunction<Supplier<String>, Supplier<String>, SqlTransformer.Case, String> batchOtherRows;
    private final Function<SqlTransformer.Case, String> lastBatchRow;

    private static final String DEFAULT_BEFORE_EACH_BATCH_PREFIX = "       ";


    SqlDialect(String sqlQuoteIdentifier, Casing casing, TriFunction<Supplier<String>, Supplier<String>, SqlTransformer.Case, String> batchFirstRow,
               TriFunction<Supplier<String>, Supplier<String>, SqlTransformer.Case, String> batchOtherRows, Function<SqlTransformer.Case, String> lastBatchRow) {
        this.sqlQuoteIdentifier = sqlQuoteIdentifier;
        this.unquotedCasing = casing;
        this.batchFirstRow = batchFirstRow;
        this.batchOtherRows = batchOtherRows;
        this.lastBatchRow = lastBatchRow;
    }

    SqlDialect(String sqlQuoteIdentifier, TriFunction<Supplier<String>, Supplier<String>, SqlTransformer.Case, String> batchFirstRow,
               TriFunction<Supplier<String>, Supplier<String>, SqlTransformer.Case, String> batchOtherRows, Function<SqlTransformer.Case, String> lastBatchRow) {
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

    public static String getFirstRow(SqlDialect dialect, Supplier<String> input, Supplier<String> input2, SqlTransformer.Case keywordCase) {
        return dialect == null
            ? DEFAULT_FIRST_ROW.apply(input, input2, keywordCase) : dialect.batchFirstRow.apply(input, input2, keywordCase);
    }

    public static String getOtherRow(SqlDialect dialect, Supplier<String> input, Supplier<String> input2, SqlTransformer.Case keywordCase) {
        return dialect == null
            ? DEFAULT_OTHER_ROWS.apply(input, input2, keywordCase) : dialect.batchOtherRows.apply(input, input2, keywordCase);
    }

    public static String getLastRowSuffix(SqlDialect dialect, SqlTransformer.Case caze) {
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

    static class AuxiliaryConstants {
        static final UnsupportedOperationException BATCH_UNSUPPORTED =
            new UnsupportedOperationException("This dialect not support batch insert.");

        static final TriFunction<Supplier<String>, Supplier<String>, SqlTransformer.Case, String> DEFAULT_FIRST_ROW = (supplier, supplier2, caze) -> {
            final String insertAll = INSERT_INTO.getValue(caze) + " ";
            final String values = LINE_SEPARATOR + VALUES.getValue(caze) + " ";
            return insertAll + supplier.get() + values + supplier2.get();
        };
        static final TriFunction<Supplier<String>, Supplier<String>, SqlTransformer.Case, String> DEFAULT_OTHER_ROWS =
            (supplier, supplier2, caze) -> DEFAULT_BEFORE_EACH_BATCH_PREFIX + supplier2.get();
    }
}
