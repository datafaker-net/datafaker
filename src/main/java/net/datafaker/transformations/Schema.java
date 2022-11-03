package net.datafaker.transformations;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Schema<IN, OUT> {
    private final Field<IN, OUT>[] fields;

    protected Schema(Field<IN, OUT>[] fields) {
        this.fields = fields;
    }

    public Field<IN, OUT>[] getFields() {
        return fields;
    }

    @SafeVarargs
    public static <IN, OUT> Schema<IN, OUT> of(Field<IN, OUT>... fields) {
        return new Schema<>(fields);
    }

    public static class SchemaBuilder<IN, OUT> {
        private final List<Field<IN, OUT>> fieldList = new ArrayList<>();

        public SchemaBuilder<IN, OUT> of(SimpleField<IN, OUT> field) {
            fieldList.add(field);
            return this;
        }

        @SafeVarargs
        public final SchemaBuilder<IN, OUT> of(Field<IN, OUT>... fields) {
            fieldList.addAll(Arrays.asList(fields));
            return this;
        }

        public Schema<IN, OUT> build() {
            return new Schema<>(fieldList.toArray(new Field[0]));
        }
    }
}
