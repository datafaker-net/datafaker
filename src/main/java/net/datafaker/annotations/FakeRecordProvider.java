package net.datafaker.annotations;

import java.lang.reflect.Constructor;
import java.lang.reflect.RecordComponent;
import java.util.ArrayList;
import java.util.List;

import net.datafaker.providers.base.BaseFaker;

class FakeRecordProvider implements ObjectProvider {

    private final BaseFaker faker;

    FakeRecordProvider(BaseFaker faker) {
        this.faker = faker;
    }

    @Override
    public <K> K createObject(Class<K> clazz) {
        try {
            FieldFakeProvider<RecordComponent> fieldFakeProvider = new FieldFakeProvider<>(faker);
            List<Object> recordComponentValue = new ArrayList<>();
            for (RecordComponent recordComponent : clazz.getRecordComponents()) {
                if (recordComponent.isAnnotationPresent(FieldFake.class)) {
                    recordComponentValue.add(fieldFakeProvider.getValue(recordComponent));
                }
            }

            Constructor<K> declaredConstructor = clazz.getDeclaredConstructor(recordComponentValue.stream().map(Object::getClass).toArray(Class[]::new));
            return declaredConstructor.newInstance(recordComponentValue.toArray(Object[]::new));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
