package net.datafaker.transformations;

import net.datafaker.providers.base.AbstractProvider;

import java.util.List;

public class SqlTransformer<IN extends AbstractProvider<?>>
    implements Transformer<IN, CharSequence> {
    private static final char DEFAULT_QUOTE = '\'';
    private static final String DEFAULT_SQL_IDENTIFIER = "\"\"";
    private final char quote;
    private final char openSqlIdentifier;
    private final char closeSqlIdentifier;
    private final String tableName;

    private SqlTransformer(char quote, String sqlIdentifier, String tableName) {
        this.quote = quote;
        this.openSqlIdentifier = sqlIdentifier.charAt(0);
        this.closeSqlIdentifier = sqlIdentifier.length() == 1 ? sqlIdentifier.charAt(0) : sqlIdentifier.charAt(1);
        this.tableName = tableName;
    }

    @Override
    public CharSequence apply(Object input, Schema<?, ? extends CharSequence> schema) {
        Field<? extends Object, ? extends CharSequence>[] fields = schema.getFields();
        if (fields.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder("insert into ");
        sb.append(openSqlIdentifier).append(tableName).append(closeSqlIdentifier).append(" (");
        for (int i = 0; i < fields.length; i++) {
            sb.append(openSqlIdentifier);
            for (int j = 0; j < fields[i].getName().length(); j++) {
                if (openSqlIdentifier == fields[i].getName().charAt(j)
                || closeSqlIdentifier == fields[i].getName().charAt(j)) {
                    sb.append(openSqlIdentifier);
                }
                sb.append(fields[i].getName().charAt(j));
            }
            sb.append(closeSqlIdentifier);
            if (i < fields.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(") values (");
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] instanceof SimpleField) {
                Object value = ((SimpleField<Object, ? extends CharSequence>) fields[i]).transform(input);
                Class<?> clazz = value == null ? null : value.getClass();
                if (value == null
                    || value instanceof java.lang.Number
                    || value instanceof java.lang.Boolean
                    || clazz.isPrimitive()) {
                    sb.append(value);
                } else {
                    String strValue = value.toString();
                    sb.append(quote);
                    for (int j = 0; j < strValue.length(); j++) {
                        if (strValue.charAt(j) == quote) {
                            sb.append(quote);
                        }
                        sb.append(strValue.charAt(j));
                    }
                    sb.append(quote);
                }
            }
            if (i < fields.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(");");
        return sb.toString();
    }

    @Override
    public String generate(List<IN> input, Schema<IN, ? extends CharSequence> schema) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.size(); i++) {
            sb.append(apply(input.get(i), schema));
            if (i < input.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public String generate(Schema<?, ? extends CharSequence> schema, int limit) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < limit; i++) {
            sb.append(apply(null, schema));
            if (i < limit - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public static class SqlTransformerBuilder {
        private char quote = DEFAULT_QUOTE;
        private String sqlQuoteIdentifier = DEFAULT_SQL_IDENTIFIER;
        private String tableName = "MyTable";

        public SqlTransformerBuilder quote(char quote) {
            this.quote = quote;
            return this;
        }

        public SqlTransformerBuilder sqlQuoteIdentifier(String sqlQuoteIdentifier) {
            this.sqlQuoteIdentifier = sqlQuoteIdentifier;
            return this;
        }

        public SqlTransformerBuilder tableName(String tableName) {
            this.tableName = tableName;
            return this;
        }

        public SqlTransformer build() {
            return new SqlTransformer(quote, sqlQuoteIdentifier, tableName);
        }
    }
}
