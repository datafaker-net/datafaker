package net.datafaker.transformations;

import java.util.List;

public class SqlTransformer<IN> implements Transformer<IN, CharSequence> {
    private static final String INSERT_INTO_UP = "INSERT INTO ";
    private static final String INSERT_INTO_LW = "insert into ";
    private static final String VALUES_UP = " VALUES ";
    private static final String VALUES_LW = " values ";
    private static final char DEFAULT_QUOTE = '\'';
    private static final char DEFAULT_CATALOG_SEPARATOR = '.';
    private static final String DEFAULT_SQL_IDENTIFIER = "\"\"";

    private final Casing casing;
    private final char quote;
    private final char openSqlIdentifier;
    private final char closeSqlIdentifier;
    private final String tableName;
    private final String schemaName;

    private final boolean withBatchMode;
    private final boolean keywordUpperCase;

    private final SqlDialect dialect;

    private SqlTransformer(String schemaName, String tableName, char quote, SqlDialect dialect, String sqlIdentifier, Casing casing, boolean withBatchMode, boolean keywordUpperCase) {
        this.schemaName = schemaName;
        this.quote = quote;
        this.dialect = dialect;
        this.openSqlIdentifier = sqlIdentifier.charAt(0);
        this.closeSqlIdentifier = sqlIdentifier.length() == 1 ? sqlIdentifier.charAt(0) : sqlIdentifier.charAt(1);
        this.tableName = tableName;
        this.casing = casing;
        this.withBatchMode = withBatchMode;
        this.keywordUpperCase = keywordUpperCase;
    }

    private boolean isSqlQuoteIdentifierRequiredFor(String name) {
        for (int i = 0; i < name.length(); i++) {
            if (casing == Casing.TO_UPPER && Character.isLowerCase(name.charAt(i))
                || casing == Casing.TO_LOWER && Character.isUpperCase(name.charAt(i))
                || name.charAt(i) == openSqlIdentifier
                || name.charAt(i) == closeSqlIdentifier) {
                return true;
            }
        }
        return false;
    }

    @Override
    public CharSequence apply(IN input, Schema<IN, ?> schema, int rowId) {
        //noinspection unchecked
        Field<?, ? extends CharSequence>[] fields = (Field<?, ? extends CharSequence>[]) schema.getFields();
        if (fields.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if (withBatchMode) {
            if (rowId == 0) {
                sb.append(
                    SqlDialect.getFirstRow(
                        dialect, () -> appendTableInfo(fields), () -> addValues(input, fields), keywordUpperCase));
            } else {
                sb.append(",\n")
                    .append(
                        SqlDialect.getOtherRow(
                            dialect, () -> appendTableInfo(fields), () -> addValues(input, fields), keywordUpperCase));
            }
        } else {
            sb.append(keywordUpperCase ? INSERT_INTO_UP : INSERT_INTO_LW)
                .append(appendTableInfo(fields)).append(keywordUpperCase ? VALUES_UP : VALUES_LW)
                .append(addValues(input, fields));
        }

        return sb.toString();
    }

    private String addValues(IN input, Field<?, ? extends CharSequence>[] fields) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] instanceof SimpleField) {
                //noinspection unchecked
                Object value = ((SimpleField<Object, ? extends CharSequence>) fields[i]).transform(input);
                Class<?> clazz = value == null ? null : value.getClass();
                if (value == null
                    || value instanceof Number
                    || value instanceof Boolean
                    || clazz.isPrimitive()) {
                    result.append(value);
                } else {
                    String strValue = value.toString();
                    result.append(quote);
                    for (int j = 0; j < strValue.length(); j++) {
                        if (strValue.charAt(j) == quote) {
                            result.append(quote);
                        }
                        result.append(strValue.charAt(j));
                    }
                    result.append(quote);
                }
            }
            if (i < fields.length - 1) {
                result.append(", ");
            }
        }
        return result.length() > 0 ? "(" + result + ")" : result.toString();
    }

    private String appendTableInfo(Field<?, ? extends CharSequence>[] fields) {
        StringBuilder result = new StringBuilder();
        appendNameToQuery(result, schemaName);
        if (schemaName != null && !schemaName.isEmpty()) {
            result.append(DEFAULT_CATALOG_SEPARATOR);
        }
        appendNameToQuery(result, tableName);
        result.append(" (");
        for (int i = 0; i < fields.length; i++) {
            final String fieldName = fields[i].getName();
            final boolean sqlIdentifierRequired = isSqlQuoteIdentifierRequiredFor(fieldName);
            if (sqlIdentifierRequired) {
                result.append(openSqlIdentifier);
            }
            for (int j = 0; j < fieldName.length(); j++) {
                if (openSqlIdentifier == fieldName.charAt(j)
                  || closeSqlIdentifier == fieldName.charAt(j)) {
                    result.append(openSqlIdentifier);
                }
                result.append(fieldName.charAt(j));
            }
            if (sqlIdentifierRequired) {
                result.append(closeSqlIdentifier);
            }
            if (i < fields.length - 1) {
                result.append(", ");
            }
        }
        result.append(")");
        return result.toString();
    }

    private void appendNameToQuery(StringBuilder sb, String name) {
        if (name == null || name.isEmpty()) return;
        boolean sqlIdentifierRequired = isSqlQuoteIdentifierRequiredFor(name);

        if (sqlIdentifierRequired) {
            sb.append(openSqlIdentifier);
        }
        sb.append(name);
        if (sqlIdentifierRequired) {
            sb.append(closeSqlIdentifier);
        }
    }

    @Override
    public String generate(List<IN> input, Schema<IN, ?> schema) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.size(); i++) {
            sb.append(apply(input.get(i), schema, i));
            if (i == input.size() - 1 && sb.length() > 0) {
                if (withBatchMode) {
                    sb.append(SqlDialect.getLastRowSuffix(dialect, keywordUpperCase));
                }
                sb.append(";");
            }
        }
        return sb.toString();
    }

    @Override
    public String generate(Schema<IN, ?> schema, int limit) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < limit; i++) {
            sb.append(apply(null, schema, i));
            if (i == limit - 1 && sb.length() > 0) {
                if (withBatchMode) {
                    sb.append(SqlDialect.getLastRowSuffix(dialect, keywordUpperCase));
                }
                sb.append(";");
            }
        }
        return sb.toString();
    }

    public static class SqlTransformerBuilder<IN> {
        private char quote = DEFAULT_QUOTE;
        private String sqlQuoteIdentifier = DEFAULT_SQL_IDENTIFIER;
        private String tableName = "MyTable";
        private String schemaName = "";
        private Casing casing = Casing.TO_UPPER;
        private boolean withBatchMode = false;
        private boolean keywordUpperCase = true;

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

        public SqlTransformerBuilder<IN> batch(boolean withBatchMode) {
            this.withBatchMode = withBatchMode;
            return this;
        }

        public SqlTransformerBuilder<IN> keywordUpperCase(boolean keywordUpperCase) {
            this.keywordUpperCase = keywordUpperCase;
            return this;
        }

        public SqlTransformer<IN> build() {
            if (dialect == null) {
                return new SqlTransformer<>(
                    schemaName, tableName, quote, dialect, sqlQuoteIdentifier, casing, withBatchMode, keywordUpperCase);
            }
            return new SqlTransformer<>(
                schemaName, tableName, quote, dialect, dialect.getSqlQuoteIdentifier(), dialect.getUnquotedCasing(),
                withBatchMode, keywordUpperCase);
        }
    }
}
