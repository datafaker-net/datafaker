package net.datafaker.transformations;

import net.datafaker.providers.base.AbstractProvider;

public class CompositeField<MyObject extends AbstractProvider<?>, MyType> extends Schema<MyObject, MyType> implements Field<MyObject, MyType> {
    private final String name;

    public CompositeField(String name, Field<MyObject, MyType>[] fields) {
        super(fields);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public MyType transform(MyObject input) {
        return null;
    }
}
