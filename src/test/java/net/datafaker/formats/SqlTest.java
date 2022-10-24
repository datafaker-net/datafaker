package net.datafaker.formats;

import net.datafaker.Faker;
import net.datafaker.providers.base.BaseFaker;
import net.datafaker.transformations.Schema;
import net.datafaker.transformations.SqlDialect;
import net.datafaker.transformations.SqlTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Stream;

import static net.datafaker.transformations.Field.field;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

class SqlTest {
    @Test
    void testGenerateFromSchemaWithLimitSeparatedStatements() {
        BaseFaker faker = new BaseFaker(new Random(10L));
        Schema<Object, ?> schema = Schema.of(
            field("Text", () -> faker.name().firstName()),
            field("Bool", () -> faker.bool().bool())
        );

        SqlTransformer<Object> transformer = new SqlTransformer.SqlTransformerBuilder<>().build();
        String sql = transformer.generate(schema, 2);

        String expected =
            "INSERT INTO \"MyTable\" (\"Text\", \"Bool\") VALUES ('Willis', false);" +
            System.lineSeparator() +
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

        SqlTransformer<Object> transformer = new SqlTransformer.SqlTransformerBuilder<>()
            .batch()
            .build();
        String sql = transformer.generate(schema, 2);

        String expected =
            "INSERT INTO \"MyTable\" (\"Text\", \"Bool\")\n" +
            "VALUES ('Willis', false),\n" +
            "       ('Carlena', true);";

        assertThat(sql).isEqualTo(expected);
    }

    @Test
    void testForceQuotedWithSqlIdentifiers() {
        BaseFaker faker = new BaseFaker(new Random(10L));
        Schema<Object, ?> schema = Schema.of(
            field("TEXT", () -> faker.name().firstName()),
            field("BOOL", () -> faker.bool().bool()));

        SqlTransformer<Object> forceQuotedTransformer = new SqlTransformer.SqlTransformerBuilder<>()
            .tableName("MY_TABLE")
            .forceUseSqlQuoteIdentifier()
            .batch()
            .build();
        String sql = forceQuotedTransformer.generate(schema, 2);

        String expected = "INSERT INTO \"MY_TABLE\" (\"TEXT\", \"BOOL\")\n" +
            "VALUES ('Willis', false),\n" +
            "       ('Carlena', true);";

        assertThat(sql).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("generateTestSchema")
    void simpleSqlTestForSqlTransformer(Schema<String, String> schema, String tableSchemaName, String expected) {
        SqlTransformer<String> transformer =
            new SqlTransformer.SqlTransformerBuilder<String>()
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
            of(Schema.of(field("nullValue", () -> null)), "MY_SCHEMA", "INSERT INTO MY_SCHEMA.MY_TABLE (`nullValue`) VALUES (null);"));
    }

    @ParameterizedTest
    @MethodSource("generateTestSchemaForOracle")
    void simpleSqlTestForSqlTransformerOracle(Schema<String, String> schema, String tableSchemaName, String expected) {
        SqlTransformer<String> transformer =
            new SqlTransformer.SqlTransformerBuilder<String>()
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
            new SqlTransformer.SqlTransformerBuilder<String>()
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
            new SqlTransformer.SqlTransformerBuilder<String>()
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
            new SqlTransformer.SqlTransformerBuilder<String>()
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
            new SqlTransformer.SqlTransformerBuilder<String>()
                .batch()
                .dialect(SqlDialect.POSTGRES)
                .build();
        final int limit = 5;
        String output = transformer.generate(schema, limit);
        assertThat(output.split("\\n")).hasSize(limit + 1);
    }

    @Test
    void sqlKeywordCaseCheck() {
        Faker faker = new Faker();
        Schema<String, String> schema =
            Schema.of(field("firstName", () -> faker.name().firstName()),
                field("lastName", () -> faker.name().lastName()));
        SqlTransformer<String> transformer =
            new SqlTransformer.SqlTransformerBuilder<String>()
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
    void batchSqlTestForSqlTransformerOracle() {
        Faker faker = new Faker();
        Schema<String, String> schema =
            Schema.of(field("firstName", () -> faker.name().firstName()),
                field("lastName", () -> faker.name().lastName()));
        SqlTransformer<String> transformerUpper =
            new SqlTransformer.SqlTransformerBuilder<String>()
                .batch()
                .dialect(SqlDialect.ORACLE).build();
        final int limit = 5;
        String output = transformerUpper.generate(schema, limit);
        assertThat(output.split("\\n")).hasSize(limit + 2);
        assertThat(output)
            .contains("INSERT ALL")
            .contains("INTO")
            .doesNotContain("INSERT INTO")
            .contains("SELECT 1 FROM dual;");
        SqlTransformer<String> transformerLower =
            new SqlTransformer.SqlTransformerBuilder<String>()
                .batch()
                .keywordCase(SqlTransformer.Case.LOWERCASE)
                .dialect(SqlDialect.ORACLE).build();
        output = transformerLower.generate(schema, limit);
        assertThat(output.split("\\n")).hasSize(limit + 2);
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
            new SqlTransformer.SqlTransformerBuilder<String>()
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
            new SqlTransformer.SqlTransformerBuilder<String>()
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
            new SqlTransformer.SqlTransformerBuilder<String>()
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
            of(Schema.of(field("names_list", () -> Arrays.asList("hello", "world"))),
                "", "INSERT INTO \"MyTable\" (\"names_list\") VALUES (ARRAY['hello', 'world']);"),
            of(Schema.of(field("names_multiset", () -> Collections.singleton("hello"))),
                "", "INSERT INTO \"MyTable\" (\"names_multiset\") VALUES (MULTISET['hello']);"));
    }
}
