package net.datafaker.transformations;


import java.util.Arrays;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schema<?, ?> schema)) return false;

        return Arrays.equals(fields, schema.fields);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(fields);
    }
}
