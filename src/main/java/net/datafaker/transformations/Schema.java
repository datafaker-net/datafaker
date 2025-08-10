package net.datafaker.transformations;


import java.util.Arrays;
import java.util.List;

public class Schema<IN, OUT> {
    private final List<Field<IN, OUT>> fields;

    protected Schema(List<Field<IN, OUT>> fields) {
        this.fields = fields;
    }

    protected Schema(Field<IN, OUT>[] fields) {
        this(Arrays.asList(fields));
    }

    @Deprecated
    @SuppressWarnings("unchecked")
    public Field<IN, OUT>[] getFields() {
        return fields.toArray(new Field[0]);
    }

    public List<Field<IN, OUT>> fields() {
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

        return fields.equals(schema.fields);
    }

    @Override
    public int hashCode() {
        return fields.hashCode();
    }
}
