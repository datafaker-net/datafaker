package net.datafaker.annotaions;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Callable;


import net.datafaker.providers.base.BaseFaker;
import net.datafaker.providers.base.BaseProviders;

public class FakeFactory<T> {

    private final Class<T> clazz;

    public FakeFactory(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T get() {
        checkFakeAnnotation(clazz);

        Fake fakeAnnotation = clazz.getAnnotation(Fake.class);

        String classLanguageTag = fakeAnnotation.languageTag();
        long[] classSeed = fakeAnnotation.seed();

        BaseFaker faker = createFaker(fakeAnnotation, classLanguageTag, classSeed);

        T object = createObject(clazz);

        fillFields(clazz, faker, object);
        return object;
    }

    private T createObject(Class<T> clazz) {
        try {
            Constructor<T> declaredConstructor = clazz.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void checkFakeAnnotation(Class<T> clazz) {
        if (Objects.isNull(clazz)) {
            throw new RuntimeException("The class is null.");
        }

        if (!clazz.isAnnotationPresent(Fake.class)) {
            throw new RuntimeException("The class "
                + clazz.getSimpleName()
                + " is not annotated with Fake");
        }
    }

    private void fillFields(Class<T> clazz, BaseFaker faker, T object) {
        try {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Provider.class)) {
                    handleProviderAnnotation(faker, object, field);
                } else if (field.isAnnotationPresent(EmbeddedFake.class)) {
                    Class<?> childClass = field.getType();
                    handleEmbeddedFakeAnnotation(object, childClass, field);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <K> void handleEmbeddedFakeAnnotation(T parentObject, Class<K> childClazz, Field field) throws IllegalAccessException {
        var fakeFactory = new FakeFactory<>(childClazz);
        field.setAccessible(true);
        field.set(parentObject, fakeFactory.get());
    }

    private void handleProviderAnnotation(BaseFaker faker, T object, Field field) throws Exception {
        Provider providerAnnotation = field.getAnnotation(Provider.class);
        if (providerWithEmptyProperties(providerAnnotation)) {
            throw new IllegalArgumentException("Expression and method parameters were not defined.");
        } else if (providerHasIncompatibleConfiguration(providerAnnotation)) {
            throw new IllegalArgumentException("Expression and method parameters cannot be defined at the same time.");
        } else if (providerAnnotation.method().isEmpty()) {
            Callable<String> expressionCallable = () -> faker.expression(providerAnnotation.expression());
            String languageTag = providerAnnotation.languageTag();
            long[] seed = providerAnnotation.seed();

            String fieldValue = generateValue(faker, providerAnnotation, expressionCallable, languageTag, seed);
            setFieldValue(object, field, fieldValue);
        } else {
            String languageTag = providerAnnotation.languageTag();
            long[] seed = providerAnnotation.seed();

            String[] classAndMethod = providerAnnotation.method().split("#");
            Class<?> clazzProvider = Class.forName(classAndMethod[0]);
            Constructor<?> declaredConstructor = clazzProvider.getDeclaredConstructor(BaseProviders.class);
            declaredConstructor.setAccessible(true);
            Callable<Object> methodCallable = () -> {
                Object provider = declaredConstructor.newInstance(faker);
                Method method = clazzProvider.getMethod(classAndMethod[1]);
                Object result = method.invoke(provider);
                return result;
            };
            Object fieldValue = generateValue(faker, providerAnnotation, methodCallable, languageTag, seed);

            field.setAccessible(true);
            field.set(object, fieldValue);
        }
    }

    private static boolean providerWithEmptyProperties(Provider providerAnnotation) {
        return providerAnnotation.expression().isEmpty() && providerAnnotation.method().isEmpty();
    }

    private static boolean providerHasIncompatibleConfiguration(Provider providerAnnotation) {
        return !providerAnnotation.expression().isEmpty() && !providerAnnotation.method().isEmpty();
    }

    private <R> R generateValue(BaseFaker faker, Provider providerAnnotation, Callable<R> expressionCallable, String languageTag, long[] seed) throws Exception {
        if (!languageTag.isEmpty() && seed.length != 0) {
            return faker.doWith(expressionCallable, Locale.forLanguageTag(languageTag), providerAnnotation.seed()[0]);
        } else if (!languageTag.isEmpty()) {
            return faker.doWith(expressionCallable, Locale.forLanguageTag(languageTag));
        } else if (seed.length != 0) {
            return faker.doWith(expressionCallable, providerAnnotation.seed()[0]);
        } else {
            return expressionCallable.call();
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

    private void setFieldValue(T object, Field field, String fieldValue) throws IllegalAccessException {
        field.setAccessible(true);
        if (field.getType() == Integer.TYPE) {
            field.setInt(object, Integer.parseInt(fieldValue));
        } else {
            field.set(object, fieldValue);
        }
    }
}
