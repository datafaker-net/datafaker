package net.datafaker.transformations;

import net.datafaker.providers.base.AbstractProvider;

import java.util.List;
import java.util.Objects;

public class CompositeField<MyObject extends AbstractProvider<?>, MyType> extends Schema<MyObject, MyType> implements Field<MyObject, MyType> {
    private final String name;

    public CompositeField(String name, List<Field<MyObject, MyType>> fields) {
        super(fields);
        this.name = name;
    }

    @Deprecated
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompositeField<?, ?> that)) return false;
        if (!super.equals(o)) return false;

        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
