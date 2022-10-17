package net.datafaker.formats;

import net.datafaker.transformations.Schema;
import net.datafaker.transformations.SqlTransformer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static net.datafaker.transformations.Field.field;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

public class SqlTest {
    @ParameterizedTest
    @MethodSource("generateTestSchema")
    void simpleJsonTestForJsonTransformer(Schema<String, String> schema, String expected) {
        SqlTransformer<?> transformer = new SqlTransformer.SqlTransformerBuilder().sqlQuoteIdentifier("`").build();
        assertThat(transformer.generate(schema, 1)).isEqualTo(expected);
    }

    private static Stream<Arguments> generateTestSchema() {
        return Stream.of(
            of(Schema.of(), ""),
            of(Schema.of(field("key", () -> "value")), "insert into `MyTable` (`key`) values ('value');"),
            of(Schema.of(field("number", () -> 123)), "insert into `MyTable` (`number`) values (123);"),
            of(Schema.of(field("number", () -> 123.0)), "insert into `MyTable` (`number`) values (123.0);"),
            of(Schema.of(field("number", () -> 123.123)), "insert into `MyTable` (`number`) values (123.123);"),
            of(Schema.of(field("boolean", () -> true)), "insert into `MyTable` (`boolean`) values (true);"),
            of(Schema.of(field("nullValue", () -> null)), "insert into `MyTable` (`nullValue`) values (null);"));
    }
}
