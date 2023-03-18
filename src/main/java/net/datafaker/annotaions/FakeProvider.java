package net.datafaker.annotaions;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Optional;

import net.datafaker.providers.base.BaseFaker;

class FakeProvider implements ObjectProvider {

    private final BaseFaker faker;

    FakeProvider(BaseFaker faker) {
        this.faker = faker;
    }

    @Override
    public <K> K createObject(Class<K> clazz) {
        return Optional.of(create(clazz))
            .map(object -> fillObject(object, clazz))
            .orElseThrow();
    }

    private <K> K create(Class<K> clazz) {
        try {
            Constructor<K> declaredConstructor = clazz.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <K> K fillObject(K object, Class<K> clazz) {
        FieldFakeProvider<Field> fieldFakeProvider = new FieldFakeProvider<>(faker);
        EmbeddedFakeProvider embeddedFakeProvider = new EmbeddedFakeProvider();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(FieldFake.class)) {
                var fieldValue = fieldFakeProvider.getValue(field);
                setField(object, field, fieldValue);
            } else if (field.isAnnotationPresent(EmbeddedFake.class)) {
                var childObject = embeddedFakeProvider.getValue(field);
                setChildObject(object, field, childObject);
            }
        }

        return object;
    }

    private <K> void setField(K object, Field field, Object fieldValue) {
        try {
            if (fieldValue instanceof String fieldValueString) {
                setFieldValue(object, field, fieldValueString);
            } else {
                field.setAccessible(true);
                field.set(object, fieldValue);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <K, C> void setChildObject(K parentObject, Field field, C childObject) {
        field.setAccessible(true);
        try {
            field.set(parentObject, childObject);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private <K> void setFieldValue(K object, Field field, String fieldValue) throws IllegalAccessException {
        field.setAccessible(true);
        if (field.getType() == Integer.TYPE) {
            field.setInt(object, Integer.parseInt(fieldValue));
        } else {
            field.set(object, fieldValue);
        }
    }

}
