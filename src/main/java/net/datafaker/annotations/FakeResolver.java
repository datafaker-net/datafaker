package net.datafaker.annotations;

import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import net.datafaker.providers.base.BaseFaker;

public class FakeResolver<T> {

    private final Class<T> clazz;

    public FakeResolver(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T get() {
        checkFakeAnnotation(clazz);

        Fake fakeAnnotation = clazz.getAnnotation(Fake.class);

        String classLanguageTag = fakeAnnotation.languageTag();
        long[] classSeed = fakeAnnotation.seed();
        BaseFaker faker = createFaker(fakeAnnotation, classLanguageTag, classSeed);

        if (clazz.isRecord()) {
            return new FakeRecordProvider(faker).createObject(clazz);
        }

        return new FakeProvider(faker).createObject(clazz);
    }

    private void checkFakeAnnotation(Class<T> clazz) {
        if (Objects.isNull(clazz)) {
            throw new RuntimeException("The class is null.");
        }

        if (!clazz.isAnnotationPresent(Fake.class)) {
            throw new RuntimeException("The class %s is not annotated with Fake".formatted(clazz.getSimpleName()));
        }
    }

    private static BaseFaker createFaker(Fake fakeAnnotation, String classLanguageTag, long[] classSeed) {
        if (!classLanguageTag.isEmpty() && classSeed.length != 0) {
            return new BaseFaker(new Locale(fakeAnnotation.languageTag()), new Random(classSeed[0]));
        } else if (!classLanguageTag.isEmpty()) {
            return new BaseFaker(new Locale(fakeAnnotation.languageTag()));
        } else if (classSeed.length != 0) {
            return new BaseFaker(new Random(classSeed[0]));
        } else {
            return new BaseFaker();
        }
    }
}
