package net.datafaker.transformations;

import static net.datafaker.transformations.Casing.DEFAULT_CASING;

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
    MYSQL("`", Casing.UNCHANGED, true),
    NETEZZA("\""),
    ORACLE("\""),
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
    private final boolean supportBulkInsert;

    SqlDialect(String sqlQuoteIdentifier, Casing casing, boolean supportBulkInsert) {
        this.sqlQuoteIdentifier = sqlQuoteIdentifier;
        this.unquotedCasing = casing;
        this.supportBulkInsert = supportBulkInsert;
    }

    SqlDialect(String sqlQuoteIdentifier, boolean supportBulkInsert) {
        this(sqlQuoteIdentifier, DEFAULT_CASING, supportBulkInsert);
    }

    SqlDialect(String sqlQuoteIdentifier, Casing casing) {
        this(sqlQuoteIdentifier, casing, false);
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
}
