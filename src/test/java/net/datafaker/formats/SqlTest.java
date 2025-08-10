package net.datafaker.formats;

import net.datafaker.Faker;
import net.datafaker.providers.base.BaseFaker;
import net.datafaker.sequence.FakeSequence;
import net.datafaker.transformations.Field;
import net.datafaker.transformations.Schema;
import net.datafaker.transformations.sql.SqlDialect;
import net.datafaker.transformations.sql.SqlTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.datafaker.transformations.Field.compositeField;
import static net.datafaker.transformations.Field.field;
import static net.datafaker.transformations.Transformer.LINE_SEPARATOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.of;

class SqlTest {

    @Test
    void generateFromFakeSequenceSeparated() {
        BaseFaker faker = new BaseFaker(new Random(10L));
        Schema<Integer, ?> schema = Schema.of(
            field("Number", integer -> integer),
            field("Password", integer -> faker.credentials().password(integer, integer))
        );

        SqlTransformer<Integer> transformer = SqlTransformer.<Integer>builder().build();
        FakeSequence<Integer> fakeSequence = faker.<Integer>collection()
            .suppliers(() -> faker.number().randomDigit())
            .len(5)
            .build();

        String sql = transformer.generate(fakeSequence, schema);

        String expected = "INSERT INTO \"MyTable\" (\"Number\", \"Password\") VALUES (3, 'nf3');" + LINE_SEPARATOR +
            "INSERT INTO \"MyTable\" (\"Number\", \"Password\") VALUES (6, '4b0v69');" + LINE_SEPARATOR +
            "INSERT INTO \"MyTable\" (\"Number\", \"Password\") VALUES (7, '00827v2');" + LINE_SEPARATOR +
            "INSERT INTO \"MyTable\" (\"Number\", \"Password\") VALUES (1, '5');" + LINE_SEPARATOR +
            "INSERT INTO \"MyTable\" (\"Number\", \"Password\") VALUES (3, 'p6x');";

        assertThat(sql).isEqualTo(expected);
    }

    @Test
    void generateFromFakeSequenceBatch() {
        BaseFaker faker = new BaseFaker(new Random(10L));
        Schema<Integer, ?> schema = Schema.of(
            field("Number", integer -> integer),
            field("Password", integer -> faker.credentials().password(integer, integer))
        );

        SqlTransformer<Integer> transformer = SqlTransformer.<Integer>builder()
            .batch()
            .build();
        FakeSequence<Integer> fakeSequence = faker.<Integer>collection()
            .suppliers(() -> faker.number().randomDigit())
            .len(5)
            .build();

        String sql = transformer.generate(fakeSequence, schema);

        String expected =
            "INSERT INTO \"MyTable\" (\"Number\", \"Password\")" + LINE_SEPARATOR +
                "VALUES (3, 'nf3')," + LINE_SEPARATOR +
                "       (6, '4b0v69')," + LINE_SEPARATOR +
                "       (7, '00827v2')," + LINE_SEPARATOR +
                "       (1, '5')," + LINE_SEPARATOR +
                "       (3, 'p6x');";

        assertThat(sql).isEqualTo(expected);
    }

    @Test
    void generateFromEmptySchema() {
        BaseFaker faker = new BaseFaker();
        Schema<Integer, Object> schema = Schema.of();

        SqlTransformer<Integer> transformer = SqlTransformer.<Integer>builder()
            .build();

        FakeSequence<Integer> fakeSequence = faker.<Integer>stream()
            .suppliers(() -> faker.number().randomDigit())
            .build();

        assertThat(transformer.generate(fakeSequence, schema)).isEmpty();
    }

    @Test
    void generateFromInfiniteFakeSequenceBatch() {
        BaseFaker faker = new BaseFaker(new Random(10L));
        Schema<Integer, ?> schema = Schema.of(
            field("Number", integer -> integer),
            field("Password", integer -> faker.credentials().password(integer, integer))
        );

        SqlTransformer<Integer> transformer = SqlTransformer.<Integer>builder()
            .batch()
            .build();
        FakeSequence<Integer> fakeSequence = faker.<Integer>stream()
            .suppliers(() -> faker.number().randomDigit())
            .build();

        assertThatThrownBy(() -> transformer.generate(fakeSequence, schema))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("The sequence should be finite of size: FakeStream{minLength=-1, maxLength=-1, nullRate=0.0}");
    }

    @Test
    void testGenerateFromSchemaWithLimitSeparatedStatements() {
        BaseFaker faker = new BaseFaker(new Random(10L));
        Schema<Object, ?> schema = Schema.of(
            field("Text", () -> faker.name().firstName()),
            field("Bool", () -> faker.bool().bool())
        );

        SqlTransformer<Object> transformer = SqlTransformer.builder().build();
        String sql = transformer.generate(schema, 2);

        String expected =
            "INSERT INTO \"MyTable\" (\"Text\", \"Bool\") VALUES ('Willis', false);" + LINE_SEPARATOR +
            "INSERT INTO \"MyTable\" (\"Text\", \"Bool\") VALUES ('Carlena', true);";

        assertThat(sql).isEqualTo(expected);
    }

    @Test
    void testGenerateFromSchemaWithLimitBatchModeStatements() {
        BaseFaker faker = new BaseFaker(new Random(10L));
        Schema<Object, ?> schema = Schema.of(
            field("Text", () -> faker.name().firstName()),
            field("Bool", () -> faker.bool().bool())
        );

        SqlTransformer<Object> transformer = SqlTransformer.builder()
            .batch()
            .build();
        String sql = transformer.generate(schema, 2);

        String expected =
            "INSERT INTO \"MyTable\" (\"Text\", \"Bool\")" + LINE_SEPARATOR +
            "VALUES ('Willis', false)," + LINE_SEPARATOR +
            "       ('Carlena', true);";

        assertThat(sql).isEqualTo(expected);
    }

    @Test
    void testForceQuotedWithSqlIdentifiers() {
        BaseFaker faker = new BaseFaker(new Random(10L));
        Schema<Object, ?> schema = Schema.of(
            field("TEXT", () -> faker.name().firstName()),
            field("BOOL", () -> faker.bool().bool()));

        SqlTransformer<Object> forceQuotedTransformer = SqlTransformer.builder()
            .tableName("MY_TABLE")
            .forceUseSqlQuoteIdentifier()
            .batch()
            .build();
        String sql = forceQuotedTransformer.generate(schema, 2);

        String expected = "INSERT INTO \"MY_TABLE\" (\"TEXT\", \"BOOL\")" + LINE_SEPARATOR +
            "VALUES ('Willis', false)," + LINE_SEPARATOR +
            "       ('Carlena', true);";

        assertThat(sql).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("generateTestSchema")
    void simpleSqlTestForSqlTransformer(Schema<String, String> schema, String tableSchemaName, String expected) {
        SqlTransformer<String> transformer =
            SqlTransformer.<String>builder()
                .sqlQuoteIdentifier("`").schemaName(tableSchemaName).tableName("MY_TABLE").build();
        assertThat(transformer.generate(schema, 1)).isEqualTo(expected);
    }

    private static Stream<Arguments> generateTestSchema() {
        return Stream.of(
            of(Schema.of(), null, ""),
            of(Schema.of(field("key", () -> "value")), "", "INSERT INTO MY_TABLE (`key`) VALUES ('value');"),
            of(Schema.of(field("number", () -> 123)), "", "INSERT INTO MY_TABLE (`number`) VALUES (123);"),
            of(Schema.of(field("number", () -> 123.0)), null, "INSERT INTO MY_TABLE (`number`) VALUES (123.0);"),
            of(Schema.of(field("number", () -> 123.123)), null, "INSERT INTO MY_TABLE (`number`) VALUES (123.123);"),
            of(Schema.of(field("boolean", () -> true)), "", "INSERT INTO MY_TABLE (`boolean`) VALUES (true);"),
            of(Schema.of(field("nullValue", () -> null)), null, "INSERT INTO MY_TABLE (`nullValue`) VALUES (null);"),
            of(Schema.of(field("nullValue", () -> null)), "My.SCHEMA", "INSERT INTO `My.SCHEMA`.MY_TABLE (`nullValue`) VALUES (null);"),
            of(Schema.of(field("nullValue", () -> null)), "MY_SCHEMA", "INSERT INTO MY_SCHEMA.MY_TABLE (`nullValue`) VALUES (null);"));
    }

    @ParameterizedTest
    @MethodSource("generateTestSchemaForOracle")
    void simpleSqlTestForSqlTransformerOracle(Schema<String, String> schema, String tableSchemaName, String expected) {
        SqlTransformer<String> transformer =
            SqlTransformer.<String>builder()
                .schemaName(tableSchemaName).dialect(SqlDialect.ORACLE).build();
        assertThat(transformer.generate(schema, 1)).isEqualTo(expected);
    }

    private static Stream<Arguments> generateTestSchemaForOracle() {
        return Stream.of(
            of(Schema.of(), null, ""),
            of(Schema.of(field("key", () -> "value")), "", "INSERT INTO \"MyTable\" (\"key\") VALUES ('value');"),
            of(Schema.of(field("number", () -> 123)), null, "INSERT INTO \"MyTable\" (\"number\") VALUES (123);"),
            of(Schema.of(field("number", () -> 123.0)), null, "INSERT INTO \"MyTable\" (\"number\") VALUES (123.0);"),
            of(Schema.of(field("number", () -> 123.123)), "", "INSERT INTO \"MyTable\" (\"number\") VALUES (123.123);"),
            of(Schema.of(field("boolean", () -> true)), null, "INSERT INTO \"MyTable\" (\"boolean\") VALUES (true);"),
            of(Schema.of(field("nullValue", () -> null)), null, "INSERT INTO \"MyTable\" (\"nullValue\") VALUES (null);"),
            of(Schema.of(field("nullValue", () -> null)), "MySchema", "INSERT INTO \"MySchema\".\"MyTable\" (\"nullValue\") VALUES (null);"));
    }

    @ParameterizedTest
    @MethodSource("generateTestSchemaForPostgres")
    void simpleSqlTestForSqlTransformerPostgres(Schema<String, String> schema, String tableSchemaName, String expected) {
        SqlTransformer<String> transformer =
            SqlTransformer.<String>builder()
                .schemaName(tableSchemaName).dialect(SqlDialect.POSTGRES).build();
        assertThat(transformer.generate(schema, 1)).isEqualTo(expected);
    }

    private static Stream<Arguments> generateTestSchemaForPostgres() {
        return Stream.of(
            of(Schema.of(), null, ""),
            of(Schema.of(field("key", () -> "value")), "", "INSERT INTO \"MyTable\" (\"key\") VALUES ('value');"),
            of(Schema.of(field("number", () -> 123)), null, "INSERT INTO \"MyTable\" (\"number\") VALUES (123);"),
            of(Schema.of(field("number", () -> 123.0)), null, "INSERT INTO \"MyTable\" (\"number\") VALUES (123.0);"),
            of(Schema.of(field("number", () -> 123.123)), "", "INSERT INTO \"MyTable\" (\"number\") VALUES (123.123);"),
            of(Schema.of(field("boolean", () -> true)), null, "INSERT INTO \"MyTable\" (\"boolean\") VALUES (true);"),
            of(Schema.of(field("nullValue", () -> null)), null, "INSERT INTO \"MyTable\" (\"nullValue\") VALUES (null);"),
            of(Schema.of(field("nullValue", () -> null)), "MySchema", "INSERT INTO \"MySchema\".\"MyTable\" (\"nullValue\") VALUES (null);"));
    }

    @ParameterizedTest
    @MethodSource("generateTestSchemaForMSSQL")
    void simpleSqlTestForSqlTransformerMSSQL(Schema<String, String> schema, String tableSchemaName, String expected) {
        SqlTransformer<String> transformer =
            SqlTransformer.<String>builder()
                .schemaName(tableSchemaName).dialect(SqlDialect.MSSQL).build();
        assertThat(transformer.generate(schema, 1)).isEqualTo(expected);
    }

    private static Stream<Arguments> generateTestSchemaForMSSQL() {
        return Stream.of(
            of(Schema.of(), null, ""),
            of(Schema.of(field("key", () -> "value")), "", "INSERT INTO [MyTable] ([key]) VALUES ('value');"),
            of(Schema.of(field("number", () -> 123)), null, "INSERT INTO [MyTable] ([number]) VALUES (123);"),
            of(Schema.of(field("number", () -> 123.0)), null, "INSERT INTO [MyTable] ([number]) VALUES (123.0);"),
            of(Schema.of(field("number", () -> 123.123)), "", "INSERT INTO [MyTable] ([number]) VALUES (123.123);"),
            of(Schema.of(field("boolean", () -> true)), "", "INSERT INTO [MyTable] ([boolean]) VALUES (true);"),
            of(Schema.of(field("nullValue", () -> null)), null, "INSERT INTO [MyTable] ([nullValue]) VALUES (null);"),
            of(Schema.of(field("nullValue", () -> null)), "MySchema", "INSERT INTO [MySchema].[MyTable] ([nullValue]) VALUES (null);"));
    }

    @ParameterizedTest
    @MethodSource("generateTestSchemaForMySQL")
    void simpleSqlTestForSqlTransformerMySQL(Schema<String, String> schema, String tableSchemaName, String expected) {
        SqlTransformer<String> transformer =
            SqlTransformer.<String>builder()
                .schemaName(tableSchemaName).dialect(SqlDialect.MYSQL).build();
        assertThat(transformer.generate(schema, 1)).isEqualTo(expected);
    }

    private static Stream<Arguments> generateTestSchemaForMySQL() {
        return Stream.of(
            of(Schema.of(), null, ""),
            of(Schema.of(field("key", () -> "value")), "", "INSERT INTO MyTable (key) VALUES ('value');"),
            of(Schema.of(field("number", () -> 123)), "", "INSERT INTO MyTable (number) VALUES (123);"),
            of(Schema.of(field("number", () -> 123.0)), null, "INSERT INTO MyTable (number) VALUES (123.0);"),
            of(Schema.of(field("number", () -> 123.123)), null, "INSERT INTO MyTable (number) VALUES (123.123);"),
            of(Schema.of(field("boolean", () -> true)), null, "INSERT INTO MyTable (boolean) VALUES (true);"),
            of(Schema.of(field("nullValue", () -> null)), null, "INSERT INTO MyTable (nullValue) VALUES (null);"),
            of(Schema.of(field("nullValue", () -> null)), "MySchema", "INSERT INTO MySchema.MyTable (nullValue) VALUES (null);"));
    }

    @Test
    void batchSqlTestForSqlTransformerPostgres() {
        Faker faker = new Faker();
        Schema<String, String> schema =
            Schema.of(field("firstName", () -> faker.name().firstName()),
                field("lastName", () -> faker.name().lastName()));
        SqlTransformer<String> transformer =
            SqlTransformer.<String>builder()
                .batch()
                .dialect(SqlDialect.POSTGRES)
                .build();
        final int limit = 5;
        String output = transformer.generate(schema, limit);
        assertThat(output.split(LINE_SEPARATOR)).hasSize(limit + 1);
    }

    @Test
    void sqlKeywordCaseCheck() {
        Faker faker = new Faker();
        Schema<String, String> schema =
            Schema.of(field("firstName", () -> faker.name().firstName()),
                field("lastName", () -> faker.name().lastName()));
        SqlTransformer<String> transformer =
            SqlTransformer.<String>builder()
                .keywordCase(SqlTransformer.Case.LOWERCASE)
                .dialect(SqlDialect.POSTGRES).build();
        final int limit = 1;
        assertThat(transformer.generate(schema, limit))
            .contains("insert into ")
            .doesNotContain("INSERT INTO ")
            .contains("values ")
            .doesNotContain("VALUES");
    }

    @Test
    void sqlKeywordCapitalCaseCheck() {
        Faker faker = new Faker();
        Schema<String, String> schema =
            Schema.of(field("firstName", () -> faker.name().firstName()),
                field("lastName", () -> faker.name().lastName()));
        SqlTransformer<String> transformer =
            SqlTransformer.<String>builder()
                .keywordCase(SqlTransformer.Case.CAPITAL)
                .dialect(SqlDialect.POSTGRES).build();
        final int limit = 1;
        assertThat(transformer.generate(schema, limit))
            .contains("Insert Into ")
            .doesNotContain("INSERT INTO ")
            .doesNotContain("insert into")
            .contains("Values ")
            .doesNotContain("VALUES")
            .doesNotContain("values");
    }

    @Test
    void batchSqlTestForSqlTransformerOracle() {
        Faker faker = new Faker();
        Schema<String, String> schema =
            Schema.of(field("firstName", () -> faker.name().firstName()),
                field("lastName", () -> faker.name().lastName()));
        SqlTransformer<String> transformerUpper =
            SqlTransformer.<String>builder()
                .batch()
                .dialect(SqlDialect.ORACLE).build();
        final int limit = 5;
        String output = transformerUpper.generate(schema, limit);
        assertThat(output.split(LINE_SEPARATOR)).hasSize(limit + 2);
        assertThat(output)
            .contains("INSERT ALL")
            .contains("INTO")
            .doesNotContain("INSERT INTO")
            .contains("SELECT 1 FROM dual;");
        SqlTransformer<String> transformerLower =
            SqlTransformer.<String>builder()
                .batch()
                .keywordCase(SqlTransformer.Case.LOWERCASE)
                .dialect(SqlDialect.ORACLE).build();
        output = transformerLower.generate(schema, limit);
        assertThat(output.split(LINE_SEPARATOR)).hasSize(limit + 2);
        assertThat(output)
            .contains("insert all")
            .contains("into")
            .doesNotContain("insert into")
            .contains("select 1 from dual;");
    }

    @Test
    void batchSizeSqlTestForSqlTransformerOracle() {
        Faker faker = new Faker();
        Schema<String, String> schema =
            Schema.of(field("firstName", () -> faker.name().firstName()),
                field("lastName", () -> faker.name().lastName()));
        SqlTransformer<String> transformerUpper =
            SqlTransformer.<String>builder()
                .batch(2)
                .dialect(SqlDialect.ORACLE).build();
        final int limit = 5;
        String output = transformerUpper.generate(schema, limit);
        assertThat(output.split("INSERT ALL")).hasSize(4);
        assertThat(output.split("SELECT 1 FROM dual")).hasSize(4);
    }

    @Test
    void batchSizeSqlTestForSqlTransformerPostgres() {
        Faker faker = new Faker();
        Schema<String, String> schema =
            Schema.of(field("firstName", () -> faker.name().firstName()),
                field("lastName", () -> faker.name().lastName()));
        SqlTransformer<String> transformerUpper =
            SqlTransformer.<String>builder()
                .batch(2)
                .dialect(SqlDialect.POSTGRES).build();
        final int limit = 5;
        String output = transformerUpper.generate(schema, limit);
        assertThat(output.split("INSERT INTO")).hasSize(4);
        assertThat(output.split("VALUES ")).hasSize(4);
    }

    @ParameterizedTest
    @MethodSource("generateTestSchemaForCalcite")
    void arrayAndMultisetSqlTestForSqlTransformerCalcite(Schema<String, String> schema, String tableSchemaName, String expected) {
        SqlTransformer<String> transformer =
            SqlTransformer.<String>builder()
                .schemaName(tableSchemaName).dialect(SqlDialect.CALCITE).build();
        assertThat(transformer.generate(schema, 1)).isEqualTo(expected);
    }

    private static Stream<Arguments> generateTestSchemaForCalcite() {
        return Stream.of(
            of(Schema.of(), null, ""),
            of(Schema.of(field("ints", () -> new int[]{1, 2, 3})), "", "INSERT INTO \"MyTable\" (\"ints\") VALUES (ARRAY[1, 2, 3]);"),
            of(Schema.of(field("longs", () -> new long[]{23L, 45L})), null, "INSERT INTO \"MyTable\" (\"longs\") VALUES (ARRAY[23, 45]);"),
            of(Schema.of(field("bytes", () -> new byte[]{1, 0})), null, "INSERT INTO \"MyTable\" (\"bytes\") VALUES (ARRAY[1, 0]);"),
            of(Schema.of(field("shorts", () -> new short[]{1, 0, 3})), null, "INSERT INTO \"MyTable\" (\"shorts\") VALUES (ARRAY[1, 0, 3]);"),
            of(Schema.of(field("booleans", () -> new boolean[]{true, false})), null, "INSERT INTO \"MyTable\" (\"booleans\") VALUES (ARRAY[true, false]);"),
            of(Schema.of(field("floats", () -> new float[]{1f, 0f, 3f})), null, "INSERT INTO \"MyTable\" (\"floats\") VALUES (ARRAY[1.0, 0.0, 3.0]);"),
            of(Schema.of(field("doubles", () -> new double[]{1d, 5d, 3d})), null, "INSERT INTO \"MyTable\" (\"doubles\") VALUES (ARRAY[1.0, 5.0, 3.0]);"),
            of(Schema.of(field("names", () -> new String[]{"hello", "world"})),
                null, "INSERT INTO \"MyTable\" (\"names\") VALUES (ARRAY['hello', 'world']);"),
            of(Schema.of(field("names_list", () -> List.of("hello", "hello"))),
                "", "INSERT INTO \"MyTable\" (\"names_list\") VALUES (MULTISET['hello', 'hello']);"),
            of(Schema.of(field("names_multiset", () -> Set.of("hello"))),
                "", "INSERT INTO \"MyTable\" (\"names_multiset\") VALUES (MULTISET['hello']);"),
            of(Schema.of(field("ints_ints", () -> new int[][]{new int[]{1}, null, new int[] {3, 4, 5}})),
                "", "INSERT INTO \"MyTable\" (\"ints_ints\") VALUES (ARRAY[ARRAY[1], NULL, ARRAY[3, 4, 5]]);"),
            of(Schema.of(field("ints_ints", () -> new int[][]{new int[]{1}, new int[]{2}, new int[] {3, 4, 5}})),
                "", "INSERT INTO \"MyTable\" (\"ints_ints\") VALUES (ARRAY[ARRAY[1], ARRAY[2], ARRAY[3, 4, 5]]);"),
            of(Schema.of(field("multiset", () -> Set.of(Set.of(Set.of("value"))))),
                "", "INSERT INTO \"MyTable\" (\"multiset\") VALUES (MULTISET[MULTISET[MULTISET['value']]]);"),
            of(Schema.of(field("multiset_array", () -> Set.of(new int[]{1, 2}))),
                "", "INSERT INTO \"MyTable\" (\"multiset_array\") VALUES (MULTISET[ARRAY[1, 2]]);"),
            of(Schema.of(field("array_multiset", () -> new Object[]{Set.of("value")})),
                "", "INSERT INTO \"MyTable\" (\"array_multiset\") VALUES (ARRAY[MULTISET['value']]);"),
            of(Schema.of(compositeField("row", field("name", () -> "2"))),
                null, "INSERT INTO \"MyTable\" (\"row\") VALUES (ROW('2'));"),
            of(Schema.of(compositeField("row_row",
                    field("name1", () -> "1"), compositeField("row", field("name", () -> "2")))),
                    null, "INSERT INTO \"MyTable\" (\"row_row\") VALUES (ROW('1', ROW('2')));"),
            of(Schema.of(compositeField("row_array",
                    field("name1", () -> "1"),
                    compositeField("row", field("name", () -> new int[]{1, 2, 3})))),
                null, "INSERT INTO \"MyTable\" (\"row_array\") VALUES (ROW('1', ROW(ARRAY[1, 2, 3])));")
        );
    }

    @Test
    void batchTestForSqlTransformerSparkSql() {
        int batchSize =  5;
        int records = 20;
        Faker faker = new Faker();

        SqlTransformer<String> transformer =
            SqlTransformer.<String>builder()
                .dialect(SqlDialect.SPARKSQL)
                .batch(batchSize)
                .build();

        String generation =
            transformer
                .generate(
                    Schema.of(
                        field("name", () -> faker.cat().name()),
                        field("breed", () -> faker.cat().breed())
                    ), records
                );

        assertThat(generation.split("INSERT INTO")).hasSize((records / batchSize) + 1);
        assertThat(generation.split("VALUES")).hasSize((records / batchSize) + 1);
        assertThat(generation.split(";")).hasSize((records / batchSize));
    }

    @ParameterizedTest
    @MethodSource("generateTestSchemaForSparkSql")
    void simpleSqlTestForSqlTransformerSparkSql(Schema<String, String> schema, String tableSchemaName, String expected) {
        SqlTransformer<String> transformer =
            SqlTransformer.<String>builder()
                .schemaName(tableSchemaName)
                .dialect(SqlDialect.SPARKSQL)
                .build();

        assertThat(transformer.generate(schema, 1)).isEqualTo(expected);
    }

    private static Stream<Arguments> generateTestSchemaForSparkSql() {

        /*
         * Maps must be ordered in order to have deterministic SQL statement.
         */
        Supplier<Map<String, String>> supplySmallMap =
            () -> new TreeMap<>(Map.of("k1", "v1"));

        Supplier<Map<String, Object>> supplyBigMap =
            () -> new TreeMap<>(Map.of("k1", supplySmallMap.get(), "k2",  supplySmallMap.get()));

        return Stream.of(
            of(Schema.of(), null, ""),
            of(Schema.of(field("bytes", () -> new byte[]{1, 0})), null,
                "INSERT INTO `MyTable` (`bytes`) VALUES (ARRAY(1, 0));"),
            of(Schema.of(field("booleans", () -> new boolean[]{true, false})), null,
                "INSERT INTO `MyTable` (`booleans`) VALUES (ARRAY(true, false));"),
            of(Schema.of(field("ints", () -> new int[]{1, 2, 3})), "",
                "INSERT INTO `MyTable` (`ints`) VALUES (ARRAY(1, 2, 3));"),
            of(Schema.of(field("longs", () -> new long[]{23L, 45L})), null,
                "INSERT INTO `MyTable` (`longs`) VALUES (ARRAY(23, 45));"),
            of(Schema.of(field("empty_map", Map::of)), null,
                "INSERT INTO `MyTable` (`empty_map`) VALUES (MAP());"),
            of(Schema.of(field("maps", supplyBigMap)), null,
                "INSERT INTO `MyTable` (`maps`) VALUES (MAP('k1', MAP('k1', 'v1'), 'k2', MAP('k1', 'v1')));"),
            of(Schema.of(
                compositeField("struct_array", field("name1", () -> "1"), compositeField("struct", field("name", () -> new int[]{1, 2, 3})))), null,
                "INSERT INTO `MyTable` (`struct_array`) VALUES (NAMED_STRUCT('name1', '1', 'struct', NAMED_STRUCT('name', ARRAY(1, 2, 3))));"),
            of(Schema.of(
                compositeField("struct_struct", field("name1", () -> "1"), compositeField("struct", field("name", () -> "2")))), null,
                "INSERT INTO `MyTable` (`struct_struct`) VALUES (NAMED_STRUCT('name1', '1', 'struct', NAMED_STRUCT('name', '2')));")
        );
    }


    @ParameterizedTest
    @MethodSource("generateTestSchemaForSparkSql")
    void simpleStreamTestForSqlTransformerSparkSql(Schema<String, String> schema, String tableSchemaName, String expected) {
        SqlTransformer<String> transformer =
            SqlTransformer
                .<String>builder()
                .schemaName(tableSchemaName)
                .dialect(SqlDialect.SPARKSQL).build();

        String sql =
            transformer
                .generateStream(schema, 1)
                .collect(Collectors.joining(LINE_SEPARATOR));

        assertThat(sql).isEqualTo(expected);
    }

    @Test
    void testSqlBatch() {
        BaseFaker faker = new BaseFaker(new Random(10L));
        Schema<Integer, ?> schema = Schema.of(
            field("Number", () -> faker.number().digit()),
            field("Password", () -> faker.internet().uuidv3())
        );

        SqlTransformer<Integer> transformer =
            SqlTransformer
                .<Integer>builder()
                .batch(3)
                .build();

        String sql =
            transformer
                .generateStream(schema, 4)
                .collect(Collectors.joining(LINE_SEPARATOR));

        String expected =
            "INSERT INTO \"MyTable\" (\"Number\", \"Password\")" + LINE_SEPARATOR +
                "VALUES ('6', '09fd4007-40ba-39df-8cb1-65926bf7b8a9')," + LINE_SEPARATOR +
                "       ('8', '96c19757-1f18-3051-9acb-f56f0b5555ae')," + LINE_SEPARATOR +
                "       ('2', '8a4a0365-cd39-33c1-a52a-279b1076cf2d');" + LINE_SEPARATOR +
                "INSERT INTO \"MyTable\" (\"Number\", \"Password\")" + LINE_SEPARATOR +
                "VALUES ('6', 'e807efdd-b6db-319d-8342-a044274d3417');";

        assertThat(sql).isEqualTo(expected);
    }

    @Test
    void testWordTruncated() {
        BaseFaker faker = new BaseFaker(new Locale("test"),new Random(10L));
        Schema<Integer, ?> schema = Schema.of(
            field("Address", () -> faker.address().country()),
            field("Book", () -> faker.book().title())
        );

        SqlTransformer<Integer> transformer =
            SqlTransformer
                .<Integer>builder()
                .batch(4)
                .build();

        String sql = transformer.generateStream(schema,3).collect(Collectors.joining(LINE_SEPARATOR));

        String expected = "INSERT INTO \"MyTable\" (\"Address\", \"Book\")" + LINE_SEPARATOR +
            "VALUES ('Cote d\\'Ivoire', 'All the King\\'s Men')," + LINE_SEPARATOR +
            "       ('Cote d\\'Ivoire', 'Blood\\'s a Rover')," + LINE_SEPARATOR +
            "       ('Cote d\\'Ivoire', 'Blood\\'s a Rover');";

        assertThat(sql).isEqualTo(expected);
    }
}
