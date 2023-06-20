package net.datafaker.integration;

import net.datafaker.providers.base.AbstractProvider;
import net.datafaker.providers.base.Address;
import net.datafaker.providers.base.BaseFaker;
import net.datafaker.Faker;
import net.datafaker.providers.base.Name;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.reflections.ReflectionUtils.getAllMethods;
import static org.reflections.ReflectionUtils.withModifier;
import static org.reflections.ReflectionUtils.withParametersCount;
import static org.reflections.ReflectionUtils.withReturnType;

/**
 * The purpose of these tests is to ensure that the Locales have been properly configured
 * and that methods return values. The unit tests should ensure what the values returned
 * are correct. These tests just ensure that the methods can be invoked.
 */
class FakerIntegrationTest {

    /**
     * a collection of Locales -> Exceptions.
     * In the case of 'pt', city_prefix is '' by design. This test fails because it's testing that all string returning
     * methods return a non blank string. But pt city_prefix is blank ,but the test shouldn't fail. So we add put
     * exceptions like this into this collection.
     */
    private static final Map<Locale, SkippedMethods> exceptions = new HashMap<>();

    static {
        // 'it' has an empty suffix list so it never returns a value
        exceptions.put(new Locale("it"), SkippedMethods.of(Name.class, "suffix"));
        exceptions.put(new Locale("es", "mx"), SkippedMethods.of(Address.class, "cityPrefix", "citySuffix"));
        exceptions.put(new Locale("pt"), SkippedMethods.of(Address.class, "cityPrefix", "citySuffix"));
        exceptions.put(new Locale("uk"), SkippedMethods.of(Address.class, "cityPrefix", "citySuffix", "stateAbbr", "streetSuffix"));
        exceptions.put(new Locale("pt-BR"), SkippedMethods.of(Address.class, "cityPrefix", "citySuffix"));
        exceptions.put(new Locale("pt-br"), SkippedMethods.of(Address.class, "cityPrefix", "citySuffix"));
        exceptions.put(new Locale("Pt_br"), SkippedMethods.of(Address.class, "cityPrefix", "citySuffix"));
        exceptions.put(new Locale("pT_Br"), SkippedMethods.of(Address.class, "cityPrefix", "citySuffix"));
        exceptions.put(new Locale("pt", "Br", "x2"), SkippedMethods.of(Address.class, "cityPrefix", "citySuffix"));
    }

    private static class SkippedMethods {
        private final Map<Class, Set<String>> class2methodNames = new HashMap<>();

        public static SkippedMethods of(Class clazz, String... methodNames) {
            SkippedMethods sm = new SkippedMethods();
            sm.class2methodNames.putIfAbsent(clazz, new HashSet<>());
            sm.class2methodNames.get(clazz).addAll(List.of(methodNames));
            return sm;
        }
    }

    private Faker init(Locale locale, Random random) {
        if (locale != null && random != null) {
            return new Faker(locale, random);
        } else if (locale != null) {
            return new Faker(locale);
        } else if (random != null) {
            return new Faker(random);
        } else {
            return new Faker();
        }
    }

    @ParameterizedTest
    @MethodSource("dataParameters")
    void testAllFakerMethodsThatReturnStrings(Locale locale, Random random) throws Exception {
        final Faker faker = init(locale, random);

        Method[] methods = faker.getClass().getMethods();
        for (Method provider : methods) {
            if (AbstractProvider.class.isAssignableFrom(provider.getReturnType()) && provider.getParameterCount() == 0) {
                AbstractProvider providerImpl = (AbstractProvider) provider.invoke(faker);

                testAllMethodsThatReturnStringsActuallyReturnStrings(providerImpl);
            }
        }
    }

    private void testAllMethodsThatReturnStringsActuallyReturnStrings(Object object) throws Exception {
        final Locale locale;
        if (object instanceof BaseFaker) {
            locale = ((BaseFaker) object).getContext().getLocale();
        } else {
            locale = ((AbstractProvider) object).getFaker().getContext().getLocale();
        }
        @SuppressWarnings("unchecked")
        Set<Method> methodsThatReturnStrings = getAllMethods(object.getClass(),
            withModifier(Modifier.PUBLIC),
            withReturnType(String.class),
            withParametersCount(0));

        for (Method method : methodsThatReturnStrings) {
            if (isExcepted(object, method, locale)) {
                continue;
            }
            final Object returnValue = method.invoke(object);
            assertThat(returnValue).as("For method " + object.getClass() + "#" + method.getName() + "value is '" + returnValue + "'").isInstanceOf(String.class);
            final String returnValueAsString = (String) returnValue;
            assertThat(returnValueAsString).as("For method " + object.getClass() + "#" + method.getName()).isNotEmpty();
        }
    }

    private boolean isExcepted(Object object, Method method, Locale locale) {
        if (exceptions.containsKey(locale) && exceptions.get(locale).class2methodNames.containsKey(object.getClass())) {
            return exceptions.get(locale).class2methodNames.get(object.getClass()).contains(method.getName());
        }
        return false;
    }

    @ParameterizedTest
    @MethodSource("dataParameters")
    void testExceptionsNotCoveredInAboveTest(Locale locale, Random random) {
        final BaseFaker faker = init(locale, random);
        assertThat(faker.bothify("####???")).isNotNull();
        assertThat(faker.letterify("????")).isNotNull();
        assertThat(faker.numerify("####")).isNotNull();

        assertThat(faker.lorem().paragraph(1)).isNotNull();
        assertThat(faker.lorem().paragraphs(1)).isNotNull();

        assertThat(faker.lorem().sentence(1)).isNotNull();
        assertThat(faker.lorem().sentences(1)).isNotNull();

        assertThat(faker.address().streetAddress()).isNotNull();

        assertThat(faker.lorem().words()).isNotNull();
        assertThat(faker.lorem().words(1)).isNotNull();
    }

    private static Stream<Arguments> dataParameters() {
        List<Arguments> arguments = new ArrayList<>();
        arguments.add(Arguments.of(Locale.ENGLISH, new Random()));
        arguments.add(Arguments.of(new Locale("pt-BR"), null));
        arguments.add(Arguments.of(new Locale("pt-br"), null));
        arguments.add(Arguments.of(new Locale("Pt_br"), null));
        arguments.add(Arguments.of(new Locale("pt", "Br", "x2"), null));
        arguments.add(Arguments.of(null, new Random()));
        arguments.add(Arguments.of(null, null));

        String[] ymlFiles = new File("./src/main/resources").list();
        for (String ymlFileName : ymlFiles) {
            arguments.add(Arguments.of(new Locale(StringUtils.substringBefore(ymlFileName, ".")), null));
        }

        return arguments.stream();
    }
}
