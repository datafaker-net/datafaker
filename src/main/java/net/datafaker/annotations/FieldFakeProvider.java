package net.datafaker.annotations;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.Locale;
import java.util.concurrent.Callable;

import net.datafaker.providers.base.BaseFaker;
import net.datafaker.providers.base.BaseProviders;

class FieldFakeProvider<R extends AnnotatedElement> implements Provider<R> {

    protected final BaseFaker faker;

    FieldFakeProvider(BaseFaker faker) {
        this.faker = faker;
    }

    @Override
    public Object getValue(R field) {
        try {
            return getValueByProviderAnnotation(getAnnotation(field));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private FieldFake getAnnotation(R field) {
        if (field instanceof RecordComponent recordComponent) {
            return recordComponent.getAnnotation(FieldFake.class);
        }
        return ((Field) field).getAnnotation(FieldFake.class);
    }

    private Object getValueByProviderAnnotation(FieldFake fieldFakeAnnotation) throws Exception {
        if (providerWithEmptyProperties(fieldFakeAnnotation)) {
            throw new IllegalArgumentException("Expression and method parameters were not defined.");
        } else if (providerHasIncompatibleConfiguration(fieldFakeAnnotation)) {
            throw new IllegalArgumentException("Expression and method parameters cannot be defined at the same time.");
        } else if (fieldFakeAnnotation.method().isEmpty()) {
            Callable<String> expressionCallable = () -> faker.expression(fieldFakeAnnotation.expression());
            String languageTag = fieldFakeAnnotation.languageTag();
            long[] seed = fieldFakeAnnotation.seed();

            return generateValue(faker, fieldFakeAnnotation, expressionCallable, languageTag, seed);
        } else {
            String languageTag = fieldFakeAnnotation.languageTag();
            long[] seed = fieldFakeAnnotation.seed();

            String[] classAndMethod = fieldFakeAnnotation.method().split("#");
            Class<?> clazzProvider = Class.forName(classAndMethod[0]);
            Constructor<?> declaredConstructor = clazzProvider.getDeclaredConstructor(BaseProviders.class);
            declaredConstructor.setAccessible(true);
            Callable<Object> methodCallable = () -> {
                Object provider = declaredConstructor.newInstance(faker);
                Method method = clazzProvider.getMethod(classAndMethod[1]);
                Object result = method.invoke(provider);
                return result;
            };
            return generateValue(faker, fieldFakeAnnotation, methodCallable, languageTag, seed);
        }
    }

    private static boolean providerWithEmptyProperties(FieldFake fieldFakeAnnotation) {
        return fieldFakeAnnotation.expression().isEmpty() && fieldFakeAnnotation.method().isEmpty();
    }

    private static boolean providerHasIncompatibleConfiguration(FieldFake fieldFakeAnnotation) {
        return !fieldFakeAnnotation.expression().isEmpty() && !fieldFakeAnnotation.method().isEmpty();
    }

    private <R> R generateValue(BaseFaker faker, FieldFake fieldFakeAnnotation, Callable<R> expressionCallable, String languageTag, long[] seed) throws Exception {
        if (!languageTag.isEmpty() && seed.length != 0) {
            return faker.doWith(expressionCallable, Locale.forLanguageTag(languageTag), fieldFakeAnnotation.seed()[0]);
        } else if (!languageTag.isEmpty()) {
            return faker.doWith(expressionCallable, Locale.forLanguageTag(languageTag));
        } else if (seed.length != 0) {
            return faker.doWith(expressionCallable, fieldFakeAnnotation.seed()[0]);
        } else {
            return expressionCallable.call();
        }
    }
}
