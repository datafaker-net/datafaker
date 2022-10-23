package net.datafaker.formats;

import net.datafaker.Faker;
import net.datafaker.transformations.Schema;
import net.datafaker.transformations.SqlDialect;
import net.datafaker.transformations.SqlTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static net.datafaker.transformations.Field.field;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

class SqlTest {
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
                .batch(true).dialect(SqlDialect.POSTGRES).build();
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
                .keywordUpperCase(false)
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
                .batch(true)
                .keywordUpperCase(true)
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
                .batch(true)
                .keywordUpperCase(false)
                .dialect(SqlDialect.ORACLE).build();
        output = transformerLower.generate(schema, limit);
        assertThat(output.split("\\n")).hasSize(limit + 2);
        assertThat(output)
            .contains("insert all")
            .contains("into")
            .doesNotContain("insert into")
            .contains("select 1 from dual;");
    }
}
