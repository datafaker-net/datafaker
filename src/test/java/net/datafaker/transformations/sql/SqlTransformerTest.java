package net.datafaker.transformations.sql;

import net.datafaker.transformations.Schema;
import org.junit.jupiter.api.Test;

import static net.datafaker.transformations.Field.field;
import static org.assertj.core.api.Assertions.assertThat;

class SqlTransformerTest {

    @Test
    void testBasicInsertStatement() {
        SqlTransformer<Object> transformer = SqlTransformer.<Object>builder()
            .tableName("person")
            .build();

        Schema<Object, ?> schema = Schema.of(
            field("name", () -> "John"),
            field("age", () -> 30)
        );

        String result = transformer.generate(schema, 1);
        assertThat(result).containsIgnoringCase("INSERT INTO");
        assertThat(result).containsIgnoringCase("person");
        assertThat(result).contains("John");
        assertThat(result).contains("30");
    }

    @Test
    void testQuoteIdentifierForLowerCaseField() {
        SqlTransformer<Object> transformer = SqlTransformer.<Object>builder()
            .tableName("test")
            .casing(Casing.TO_UPPER)
            .build();

        Schema<Object, ?> schema = Schema.of(
            field("myField", () -> "value")
        );

        String result = transformer.generate(schema, 1);
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
    }

    @Test
    void testNoQuoteIdentifierForMatchingCase() {
        SqlTransformer<Object> transformer = SqlTransformer.<Object>builder()
            .tableName("TEST")
            .casing(Casing.TO_UPPER)
            .build();

        Schema<Object, ?> schema = Schema.of(
            field("MYFIELD", () -> "value")
        );

        String result = transformer.generate(schema, 1);
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
    }

    @Test
    void testForceQuoteIdentifier() {
        SqlTransformer<Object> transformer = SqlTransformer.<Object>builder()
            .tableName("test")
            .forceUseSqlQuoteIdentifier()
            .build();

        Schema<Object, ?> schema = Schema.of(
            field("name", () -> "value")
        );

        String result = transformer.generate(schema, 1);
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
    }

    @Test
    void testLowercaseKeywords() {
        SqlTransformer<Object> transformer = SqlTransformer.<Object>builder()
            .tableName("person")
            .keywordCase(SqlTransformer.Case.LOWERCASE)
            .build();

        Schema<Object, ?> schema = Schema.of(
            field("name", () -> "John")
        );

        String result = transformer.generate(schema, 1);
        assertThat(result).contains("insert into");
        assertThat(result).contains("values");
    }

    @Test
    void testCapitalKeywords() {
        SqlTransformer<Object> transformer = SqlTransformer.<Object>builder()
            .tableName("person")
            .keywordCase(SqlTransformer.Case.CAPITAL)
            .build();

        Schema<Object, ?> schema = Schema.of(
            field("name", () -> "John")
        );

        String result = transformer.generate(schema, 1);
        assertThat(result).contains("Insert Into");
        assertThat(result).contains("Values");
    }

    @Test
    void testBatchMode() {
        SqlTransformer<Object> transformer = SqlTransformer.<Object>builder()
            .tableName("person")
            .batch(2)
            .build();

        Schema<Object, ?> schema = Schema.of(
            field("name", () -> "John")
        );

        String result = transformer.generate(schema, 2);
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
    }

    @Test
    void testSchemaName() {
        SqlTransformer<Object> transformer = SqlTransformer.<Object>builder()
            .tableName("person")
            .schemaName("myschema")
            .build();

        Schema<Object, ?> schema = Schema.of(
            field("name", () -> "John")
        );

        String result = transformer.generate(schema, 1);
        assertThat(result).containsIgnoringCase("myschema");
        assertThat(result).containsIgnoringCase("person");
    }

    @Test
    void testEmptySchema() {
        SqlTransformer<Object> transformer = SqlTransformer.<Object>builder()
            .tableName("person")
            .build();

        Schema<Object, ?> schema = Schema.of();
        String result = transformer.generate(schema, 1);
        assertThat(result).isEmpty();
    }

    @Test
    void testBuilderWithQuote() {
        SqlTransformer<Object> transformer = SqlTransformer.<Object>builder()
            .tableName("person")
            .quote('"')
            .build();

        Schema<Object, ?> schema = Schema.of(
            field("name", () -> "John")
        );

        String result = transformer.generate(schema, 1);
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
    }
}