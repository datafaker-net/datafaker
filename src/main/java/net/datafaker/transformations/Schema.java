package net.datafaker.transformations;


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
}
