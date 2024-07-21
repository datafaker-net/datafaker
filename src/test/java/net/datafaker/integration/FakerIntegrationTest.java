package net.datafaker.integration;

import net.datafaker.Faker;
import net.datafaker.providers.base.AbstractProvider;
import net.datafaker.providers.base.Address;
import net.datafaker.providers.base.App;
import net.datafaker.providers.base.BaseFaker;
import net.datafaker.providers.base.Name;
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
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.lang.Thread.currentThread;
import static org.apache.commons.lang3.StringUtils.substringBefore;
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
    private static final Logger log = Logger.getLogger(FakerIntegrationTest.class.getName());

    /**
     * a collection of Locales -> Exceptions.
     * In the case of 'pt', city_prefix is '' by design. This test fails because it's testing that all string returning
     * methods return a non-blank string. But pt city_prefix is blank ,but the test shouldn't fail. So we add put
     * exceptions like this into this collection.
     */
    private static final Map<Locale, SkippedMethods> exceptions = new HashMap<>();

    static {
        // 'it' has an empty suffix list, so it never returns a value
        exceptions.put(new Locale("it"), SkippedMethods.of(Name.class, "suffix"));
        exceptions.put(new Locale("es", "mx"), SkippedMethods.of(Address.class, "cityPrefix", "citySuffix"));
        exceptions.put(new Locale("pt"), SkippedMethods.of(Address.class, "cityPrefix", "citySuffix"));
        exceptions.put(new Locale("uk"), SkippedMethods.of(Address.class, "cityPrefix", "citySuffix", "stateAbbr", "streetSuffix"));
        exceptions.put(new Locale("uk", "UA"), SkippedMethods.of(Address.class, "cityPrefix", "citySuffix", "stateAbbr", "streetSuffix"));
        exceptions.put(new Locale("id"), SkippedMethods.of(App.class, "author"));
        exceptions.put(new Locale("id", "ID"), SkippedMethods.of(App.class, "author"));
        exceptions.put(new Locale("pt", "BR"), SkippedMethods.of(Address.class, "cityPrefix", "citySuffix"));
        exceptions.put(new Locale("pt", "Br", "x2"), SkippedMethods.of(Address.class, "cityPrefix", "citySuffix"));
    }

    private static class SkippedMethods {
        private final Map<Class<?>, Set<String>> class2methodNames = new HashMap<>();

        public static SkippedMethods of(Class<?> clazz, String... methodNames) {
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
        log.fine(() -> "  (%s, %s)".formatted(locale, random));
        final Faker faker = init(locale, random);

        Method[] methods = faker.getClass().getMethods();
        for (Method provider : methods) {
            if (AbstractProvider.class.isAssignableFrom(provider.getReturnType()) && provider.getParameterCount() == 0) {
                log.fine(() -> "    (%s), method: %s.%s()".formatted(locale, provider.getDeclaringClass().getSimpleName(), provider.getName()));

                AbstractProvider<?> providerImpl = (AbstractProvider<?>) provider.invoke(faker);
                testAllMethodsThatReturnStringsActuallyReturnStrings(providerImpl);
            }
        }
    }

    private void testAllMethodsThatReturnStringsActuallyReturnStrings(AbstractProvider<?> provider) {
        final Locale locale = provider.getFaker().getContext().getLocale();
        @SuppressWarnings("unchecked")
        Set<Method> methodsThatReturnStrings = getAllMethods(provider.getClass(),
            withModifier(Modifier.PUBLIC),
            withReturnType(String.class),
            withParametersCount(0));

        for (Method method : methodsThatReturnStrings) {
            if (isExcepted(provider, method, locale)) {
                continue;
            }
            final Object returnValue;
            try {
                log.fine(() -> "        (%s), method: %s.%s()".formatted(locale, method.getDeclaringClass(), method.getName()));
                returnValue = method.invoke(provider);
            } catch (Exception e) {
                throw new RuntimeException("Test for method %s and object %s was failed for locale %s [thread: %s]".formatted(
                    method, provider, locale, currentThread().getName()), e);
            }
            Supplier<String> description = () -> "For method %s.%s(), value is '%s'".formatted(provider.getClass().getSimpleName(), method.getName(), returnValue);
            assertThat(returnValue).as(description).isInstanceOf(String.class);
            assertThat((String) returnValue).as(description).isNotEmpty();
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
        arguments.add(Arguments.of(new Locale("en", "US"), new Random()));
        arguments.add(Arguments.of(new Locale("en", "GB"), new Random()));
        arguments.add(Arguments.of(new Locale("pt", "BR"), null));
        arguments.add(Arguments.of(new Locale("pt", "br"), null));
        arguments.add(Arguments.of(new Locale("Pt", "br"), null));
        arguments.add(Arguments.of(new Locale("pt", "Br", "x2"), null));
        arguments.add(Arguments.of(null, new Random()));
        arguments.add(Arguments.of(null, null));

        String[] ymlFiles = new File("./src/main/resources").list();
        for (String ymlFileName : ymlFiles) {
            if (ymlFileName.endsWith(".yml")) {
                String locale = substringBefore(ymlFileName, ".").replace("-", "_");
                arguments.add(Arguments.of(new Locale(locale), null));
            }
        }

        return arguments.stream();
    }
}
