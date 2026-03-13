package net.datafaker.transformations.sql;

public class SqlTransformerBuilder<IN> {
    private static final char DEFAULT_QUOTE = '\'';
    private static final String DEFAULT_SQL_IDENTIFIER = "\"\"";

    private char quote = DEFAULT_QUOTE;
    private String sqlQuoteIdentifier = DEFAULT_SQL_IDENTIFIER;
    private String tableName = "MyTable";
    private String schemaName = "";
    private Casing casing = Casing.TO_UPPER;
    private boolean withBatchMode = false;
    private int batchSize = -1;
    private SqlTransformer.Case keywordCase = SqlTransformer.Case.UPPERCASE;
    private boolean forceSqlQuoteIdentifierUsage = false;
    private SqlDialect dialect;

    public SqlTransformerBuilder<IN> dialect(SqlDialect dialect) {
        this.dialect = dialect;
        return this;
    }

    public SqlTransformerBuilder<IN> casing(Casing casing) {
        this.casing = casing;
        this.dialect = null;
        return this;
    }

    public SqlTransformerBuilder<IN> quote(char quote) {
        this.quote = quote;
        return this;
    }

    public SqlTransformerBuilder<IN> sqlQuoteIdentifier(String sqlQuoteIdentifier) {
        this.sqlQuoteIdentifier = sqlQuoteIdentifier;
        this.dialect = null;
        return this;
    }

    public SqlTransformerBuilder<IN> tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public SqlTransformerBuilder<IN> schemaName(String schemaName) {
        this.schemaName = schemaName;
        return this;
    }

    public SqlTransformerBuilder<IN> batch() {
        this.withBatchMode = true;
        return this;
    }

    public SqlTransformerBuilder<IN> batch(int batchSize) {
        this.batchSize = batchSize;
        this.withBatchMode = true;
        return this;
    }

    public SqlTransformerBuilder<IN> keywordCase(SqlTransformer.Case caze) {
        this.keywordCase = caze;
        return this;
    }

    public SqlTransformerBuilder<IN> forceUseSqlQuoteIdentifier() {
        this.forceSqlQuoteIdentifierUsage = true;
        return this;
    }

    public SqlTransformer<IN> build() {
        if (dialect == null) {
            return SqlTransformer.create(
                schemaName, tableName, quote, null, sqlQuoteIdentifier,
                casing, withBatchMode, batchSize, keywordCase, forceSqlQuoteIdentifierUsage);
        } else {
            return SqlTransformer.create(
                schemaName, tableName, quote, dialect, dialect.getSqlQuoteIdentifier(),
                dialect.getUnquotedCasing(), withBatchMode, batchSize, keywordCase,
                forceSqlQuoteIdentifierUsage);
        }
    }
}