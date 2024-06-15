package net.datafaker.transformations.sql;

import net.datafaker.sequence.FakeSequence;
import net.datafaker.sequence.FakeStream;
import net.datafaker.transformations.CompositeField;
import net.datafaker.transformations.Field;
import net.datafaker.transformations.Schema;
import net.datafaker.transformations.SimpleField;
import net.datafaker.transformations.Transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Stream;

import static net.datafaker.transformations.sql.SqlTransformer.SQLKeyWords.ARRAY;
import static net.datafaker.transformations.sql.SqlTransformer.SQLKeyWords.INSERT_INTO;
import static net.datafaker.transformations.sql.SqlTransformer.SQLKeyWords.MAP;
import static net.datafaker.transformations.sql.SqlTransformer.SQLKeyWords.MULTISET;
import static net.datafaker.transformations.sql.SqlTransformer.SQLKeyWords.NULL;
import static net.datafaker.transformations.sql.SqlTransformer.SQLKeyWords.VALUES;

public class SqlTransformer<IN> implements Transformer<IN, CharSequence> {
    private static final char DEFAULT_QUOTE = '\'';
    private static final char DEFAULT_CATALOG_SEPARATOR = '.';
    private static final String DEFAULT_SQL_IDENTIFIER = "\"\"";
    private static final String EMPTY_RESULT = "";

    private final Casing casing;
    private final char quote;
    private final char openSqlIdentifier;
    private final char closeSqlIdentifier;
    private final String tableName;
    private final String schemaName;

    private final boolean withBatchMode;
    private final int batchSize;
    private final Case keywordCase;
    private final boolean forceSqlQuoteIdentifierUsage;

    private final SqlDialect dialect;

    public static <IN> SqlTransformer.SqlTransformerBuilder<IN> builder() {
        return new SqlTransformer.SqlTransformerBuilder<>();
    }

    private SqlTransformer(String schemaName, String tableName, char quote, SqlDialect dialect, String sqlIdentifier,
                           Casing casing, boolean withBatchMode, int batchSize, Case keywordCase, boolean forceSqlQuoteIdentifierUsage) {
        this.schemaName = schemaName;
        this.quote = quote;
        this.dialect = dialect;
        this.openSqlIdentifier = sqlIdentifier.charAt(0);
        this.closeSqlIdentifier = sqlIdentifier.length() == 1 ? sqlIdentifier.charAt(0) : sqlIdentifier.charAt(1);
        this.tableName = tableName;
        this.casing = casing;
        this.withBatchMode = withBatchMode;
        this.batchSize = batchSize;
        this.keywordCase = keywordCase;
        this.forceSqlQuoteIdentifierUsage = forceSqlQuoteIdentifierUsage;
    }

    private boolean isSqlQuoteIdentifierRequiredFor(String name) {
        if (forceSqlQuoteIdentifierUsage) return true;
        for (int i = 0; i < name.length(); i++) {
            if (casing == Casing.TO_UPPER && Character.isLowerCase(name.charAt(i))
                || casing == Casing.TO_LOWER && Character.isUpperCase(name.charAt(i))
                || name.charAt(i) == openSqlIdentifier
                || name.charAt(i) == closeSqlIdentifier
                || name.charAt(i) == DEFAULT_CATALOG_SEPARATOR) {
                return true;
            }
        }
        return false;
    }

    @Override
    public CharSequence apply(IN input, Schema<IN, ?> schema) {
        return apply(input, schema, 0);
    }

    @Override
    public CharSequence apply(IN input, Schema<IN, ?> schema, long rowId) {
        //noinspection unchecked
        Field<?, ? extends CharSequence>[] fields = (Field<?, ? extends CharSequence>[]) schema.getFields();
        if (fields.length == 0) {
            return EMPTY_RESULT;
        }
        if (withBatchMode) {
            if (rowId == 0 || batchSize > 0 && rowId % batchSize == 0) {
                return SqlDialect.getFirstRow(
                    dialect, () -> appendTableInfo(fields), () -> addValues(input, fields), keywordCase);
            } else {
                return String.join(LINE_SEPARATOR, ",",
                    SqlDialect.getOtherRow(
                        dialect, () -> appendTableInfo(fields), () -> addValues(input, fields), keywordCase));
            }
        } else {
            return String.join(" ", INSERT_INTO.getValue(keywordCase),
                appendTableInfo(fields),
                VALUES.getValue(keywordCase),
                addValues(input, fields));
        }
    }

    private String addValues(IN input, Field<?, ? extends CharSequence>[] fields) {
        StringJoiner result = new StringJoiner(", ");
        for (int i = 0; i < fields.length; i++) {

            if(dialect != null) {
                String fieldPrefix =
                    dialect.getFieldPrefix(fields[i].getName());

                if (!fieldPrefix.isEmpty()) {
                    result.add(dialect.getFieldPrefix(fields[i].getName()));
                }
            }

            if (fields[i] instanceof SimpleField) {
                //noinspection unchecked
                Object value = ((SimpleField<Object, ? extends CharSequence>) fields[i]).transform(input);
                Class<?> clazz = value == null ? null : value.getClass();
                if (value == null
                    || value instanceof Number
                    || value instanceof Boolean
                    || clazz.isPrimitive()) {
                    result.add(String.valueOf(value));
                } else if (clazz.isArray()) {
                    final Class<?> componentType = clazz.getComponentType();
                    result.add(ARRAY.getValue(keywordCase) + dialect.getArrayStart() +
                        (componentType.isPrimitive()
                            ? handlePrimitivesInArray(componentType, value)
                            : handleObjectInArray(value)) + dialect.getArrayEnd());
                } else if (value instanceof Map) {
                    result.add(MAP.getValue(keywordCase) + "(" + handeObjectInMap(value) + ")");
                } else if (value instanceof Collection) {
                    result.add(MULTISET.getValue(keywordCase) + "[" +
                        handleObjectInCollection(value) + "]");
                } else {
                    result.add(handleObject(value));
                }
            } else if (fields[i] instanceof CompositeField) {
                result.add(dialect.getCompositePrefix(keywordCase) + addValues(input, ((CompositeField) fields[i]).getFields()));
            } else {
                throw new IllegalArgumentException(fields[i] + " not supported");
            }
        }
        String res = result.toString();
        return !res.isEmpty() ? "(" + res + ")" : res;
    }

    private String handleObjectInArray(Object value) {
        StringBuilder result = new StringBuilder();
        Object[] array = (Object[]) value;
        for (int j = 0; j < array.length; j++) {
            result.append(handleObject(array[j]));
            if (j < array.length - 1) {
                result.append(", ");
            }
        }
        return result.toString();
    }

    private String handleObjectInCollection(Object value) {
        StringBuilder result = new StringBuilder();
        Collection collection = (Collection) value;
        int i = 0;
        for (Object elem : collection) {
            result.append(handleObject(elem));
            if (i < collection.size() - 1) {
                result.append(", ");
            }
            i++;
        }
        return result.toString();
    }

    private String handeObjectInMap(Object value) {
        StringBuilder result = new StringBuilder();
        Map<Object, Object> map = (Map<Object, Object>) value;
        int i = 0;
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            result.append(handleObject(entry.getKey()));
            result.append(", ");
            result.append(handleObject(entry.getValue()));

            if (i < map.size() - 1) {
                result.append(", ");
            }
            i++;
        }
        return result.toString();
    }

    private String handleObject(Object value) {
        if (value == null) {
            return NULL.getValue(keywordCase);
        } else {
            if (value.getClass().isArray()) {
                final Class<?> componentType = value.getClass().getComponentType();
                String array = componentType.isPrimitive()
                    ? handlePrimitivesInArray(componentType, value)
                    : handleObjectInArray(value);
                return ARRAY.getValue(keywordCase) + "[" + array + "]";
            } else if (value instanceof Map) {
                return MAP.getValue(keywordCase) + "(" + handeObjectInMap(value) + ")";
            } else if (value instanceof Collection) {
                return MULTISET.getValue(keywordCase)
                    + "[" + handleObjectInCollection(value) + "]";
            } else {
                String strValue = value.toString();
                final int length = strValue.length();
                final boolean quoteRequired = !(value instanceof Number) && !(value instanceof Boolean);
                String res = handledObjectToString(length, strValue);
                return quoteRequired ? quote + res + quote : res;
            }
        }
    }

    private String handledObjectToString(int length, String strValue) {
        StringJoiner joiner = null;

        int j = 0;
        for (int k = 0; k < length; k++) {
            if (strValue.charAt(k) == quote) {
                if (joiner == null) {
                    joiner = new StringJoiner("" + quote);
                }
                joiner.add(strValue.substring(j, k + 1));
                j = k + 1;
            }
        }
        return joiner == null ? strValue : joiner.toString();
    }

    private String handlePrimitivesInArray(Class<?> componentType, Object value) {
        StringJoiner joiner = new StringJoiner(", ");
        if (componentType == byte.class) {
            byte[] array = (byte[]) value;
            for (byte b : array) {
                joiner.add(String.valueOf(b));
            }
        }
        if (componentType == short.class) {
            short[] array = (short[]) value;
            for (short i : array) {
                joiner.add(String.valueOf(i));
            }
        }
        if (componentType == boolean.class) {
            boolean[] array = (boolean[]) value;
            for (boolean b : array) {
                joiner.add(String.valueOf(b));
            }
        } else if (componentType == int.class) {
            int[] array = (int[]) value;
            for (int i : array) {
                joiner.add(String.valueOf(i));
            }
        } else if (componentType == long.class) {
            long[] array = (long[]) value;
            for (long l : array) {
                joiner.add(String.valueOf(l));
            }
        } else if (componentType == float.class) {
            float[] array = (float[]) value;
            for (float v : array) {
                joiner.add(String.valueOf(v));
            }
        } else if (componentType == double.class) {
            double[] array = (double[]) value;
            for (double v : array) {
                joiner.add(String.valueOf(v));
            }
        }
        return joiner.toString();
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
    public String generate(Iterable<IN> input, Schema<IN, ?> schema) {
        if (schema.getFields().length == 0) {
            return EMPTY_RESULT;
        }
        if (input instanceof FakeSequence<?> fakeSequence && fakeSequence.isInfinite()) {
            throw new IllegalArgumentException("The sequence should be finite of size: " + input);
        }

        final List<IN> inputs;
        if (input instanceof FakeStream<IN> fakeStream) {
            Stream<IN> stream = fakeStream.get();
            inputs = stream.toList();
        } else if (input instanceof FakeSequence<?> fakeSequence) {
            inputs = fakeSequence.get();
        } else if (input instanceof List<IN> list) {
            inputs = list;
        } else {
            inputs = new ArrayList<>();
            for (IN o : input) {
                inputs.add(o);
            }
        }

        int limit = inputs.size();
        if (withBatchMode) {
            return generateBatchModeStatements(schema, inputs, limit);
        } else {
            return generateSeparatedStatements(schema, inputs, limit);
        }
    }

    @Override
    public String generate(Schema<IN, ?> schema, int limit) {
        if (schema.getFields().length == 0) {
            return EMPTY_RESULT;
        }

        if (withBatchMode) {
            return generateBatchModeStatements(schema, null, limit);
        } else {
            return generateSeparatedStatements(schema, null, limit);
        }
    }

    @Override
    public String getStartStream(Schema<IN, ?> schema) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getEndStream() {
        throw new UnsupportedOperationException();
    }

    private String generateBatchModeStatements(Schema<IN, ?> schema, List<IN> inputs, int limit) {
        StringBuilder sb = new StringBuilder();
        limit = inputs != null ? Math.min(limit, inputs.size()) : limit;
        for (int i = 0; i < limit; i++) {
            IN input = inputs != null ? inputs.get(i) : null;
            sb.append(apply(input, schema, i));
            if (i == limit - 1 && !sb.isEmpty() || batchSize > 0 && (i + 1) % batchSize == 0) {
                sb.append(SqlDialect.getLastRowSuffix(dialect, keywordCase));
                sb.append(";");
                if (i < limit - 1 && !sb.isEmpty()) {
                    sb.append(LINE_SEPARATOR);
                }
            }
        }
        return sb.toString();
    }

    private String generateSeparatedStatements(Schema<IN, ?> schema, List<IN> inputs, int limit) {
        StringJoiner data = new StringJoiner(LINE_SEPARATOR);
        limit = inputs != null ? Math.min(limit, inputs.size()) : limit;
        for (int i = 0; i < limit; i++) {
            IN input = inputs != null ? inputs.get(i) : null;
            data.add(apply(input, schema) + ";");
        }
        return data.toString();
    }

    public static class SqlTransformerBuilder<IN> {
        private char quote = DEFAULT_QUOTE;
        private String sqlQuoteIdentifier = DEFAULT_SQL_IDENTIFIER;
        private String tableName = "MyTable";
        private String schemaName = "";
        private Casing casing = Casing.TO_UPPER;
        private boolean withBatchMode = false;
        private int batchSize = -1; // no limit
        private Case keywordCase = Case.UPPERCASE;
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

        public SqlTransformerBuilder<IN> keywordCase(Case caze) {
            this.keywordCase = caze;
            return this;
        }

        public SqlTransformerBuilder<IN> forceUseSqlQuoteIdentifier() {
            this.forceSqlQuoteIdentifierUsage = true;
            return this;
        }

        public SqlTransformer<IN> build() {
            if (dialect == null) {
                return new SqlTransformer<>(
                    schemaName, tableName, quote, null, sqlQuoteIdentifier, casing, withBatchMode, batchSize, keywordCase, forceSqlQuoteIdentifierUsage);
            } else {
                return new SqlTransformer<>(
                    schemaName, tableName, quote, dialect, dialect.getSqlQuoteIdentifier(), dialect.getUnquotedCasing(),
                    withBatchMode, batchSize, keywordCase, forceSqlQuoteIdentifierUsage);
            }
        }
    }

    public enum Case {
        CAPITAL,
        LOWERCASE,
        UPPERCASE
    }

    enum SQLKeyWords {
        ARRAY("ARRAY", "array", "Array"),
        MAP("MAP", "map", "Map"),
        NAMED_STRUCT("NAMED_STRUCT", "named_struct", "Named_Struct"),
        INSERT_ALL("INSERT ALL", "insert all", "Insert All"),
        INSERT_INTO("INSERT INTO", "insert into", "Insert Into"),
        INTO("INTO", "into", "Into"),
        MULTISET("MULTISET", "multiset", "Multiset"),
        NULL("NULL", "null", "Null"),
        ROW("ROW", "row", "Row"),
        SELECT_1_FROM_DUAL("SELECT 1 FROM dual", "select 1 from dual", "Select 1 From dual"),
        VALUES("VALUES", "values", "Values");
        private final String upperCaseValue;
        private final String lowerCaseValue;
        private final String capitalValue;

        SQLKeyWords(String upperCaseValue, String lowerCaseValue, String capitalValue) {
            this.upperCaseValue = upperCaseValue;
            this.lowerCaseValue = lowerCaseValue;
            this.capitalValue = capitalValue;
        }

        public String getValue(Case caze) {
            return switch (caze) {
                case UPPERCASE -> upperCaseValue;
                case LOWERCASE -> lowerCaseValue;
                case CAPITAL -> capitalValue;
            };
        }
    }
}
