package net.datafaker.providers.base;

import net.datafaker.Faker;
import net.datafaker.internal.helper.LazyEvaluated;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Base class with common test functionality for provider testing.
 * Contains shared assertion logic and test utilities.
 *
 * @param <T> the type of BaseFaker being tested
 */
public abstract class AbstractProviderListTest<T extends BaseFaker> {
    private static final LazyEvaluated<List<Locale>> locales = new LazyEvaluated<>(() ->
        new Faker().locality().allSupportedLocales().stream()
            .map(locale -> new Locale(locale))
            .toList());

    private static final LazyEvaluated<List<BaseFaker>> fakers = new LazyEvaluated<>(() ->
        locales.get().stream()
            .map(locale -> new BaseFaker(locale))
            .toList());

    protected List<BaseFaker> internationalFakers() {
        return fakers.get();
    }

    protected List<String> getBaseList(T faker, String key) {
        return faker.fakeValuesService().fetchObject(key, faker.getContext());
    }

    protected void testProviderList(ProviderListTest.TestSpec testSpec, T faker) {
        // Given
        Set<String> expected = new HashSet<>(getBaseList(faker, testSpec.key));
        // When
        String actual = (String) testSpec.supplier.get();
        // Then
        assertThat(actual).isNotEmpty();
        String collection = "\"" + testSpec.key + "\"";
        assertThat(expected)
            .as(() -> "Check expected list isn't empty and contains the actual for the key " + collection)
            .isNotEmpty()
            .contains(actual);
        assertThat(expected)
            .as(() -> "Actual should not have empty entries. " + collection)
            .noneMatch(String::isBlank);
        if (!testSpec.regex.isEmpty()) {
            assertThat(actual).matches(Pattern.compile(testSpec.regex));
        }
    }

    protected static class TestSpec {
        private final Supplier<?> supplier;
        private final String key;
        private final String regex;

        private TestSpec(Supplier<?> supplier, String key, String regex) {
            this.supplier = supplier;
            this.key = key;
            this.regex = regex;
        }

        public static TestSpec of(Supplier<?> supplier, String key) {
            return new TestSpec(supplier, key, "");
        }

        public static TestSpec of(Supplier<?> supplier, String key, String regex) {
            return new TestSpec(supplier, key, regex);
        }

        @Override
        public String toString() {
            // The result of this toString will be used by IDE in test report
            return "Key: " + key;
        }
    }
}
