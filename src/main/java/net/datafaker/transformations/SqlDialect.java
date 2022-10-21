package net.datafaker.transformations;

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
    MSSQL("[]"),
    MYSQL("`", Casing.UNCHANGED),
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

    SqlDialect(String sqlQuoteIdentifier, Casing casing) {
        this.sqlQuoteIdentifier = sqlQuoteIdentifier;
        this.unquotedCasing = casing;
    }

    SqlDialect(String sqlQuoteIdentifier) {
        this(sqlQuoteIdentifier, Casing.TO_UPPER);
    }

    public String getSqlQuoteIdentifier() {
        return sqlQuoteIdentifier;
    }

    public Casing getUnquotedCasing() {
        return unquotedCasing;
    }
}
