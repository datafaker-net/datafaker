package net.datafaker.annotaions;

import java.lang.reflect.Field;

class EmbeddedFakeProvider implements Provider<Field> {


    EmbeddedFakeProvider() {
    }

    @Override
    public Object getValue(Field field) {
        Class<?> childClass = field.getType();
        var fakeFactory = new FakeResolver<>(childClass);
        return fakeFactory.get();
    }
}
