package net.datafaker.providers.base;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class ProviderListTest<T extends BaseFaker> {
    protected abstract T getFaker();

    protected List<String> getBaseList(T faker, String key) {
        return faker.fakeValuesService().fetchObject(key, faker.getContext());
    }

    @ParameterizedTest
    @MethodSource("providerListTest")
    protected void testProviderList(TestSpec testSpec) {
        T faker = getFaker();
        // Given
        Set<String> actual = new HashSet<>(getBaseList(faker, testSpec.key));
        // When
        String item = (String) testSpec.supplier.get();
        // Then
        assertThat(item).isNotEmpty();
        String collection = "\"" + testSpec.key + "\"";
        assertThat(actual)
            .as(() -> "Check actual list isn't empty and contains the item for the key " + collection)
            .isNotEmpty()
            .contains(item);
        assertThat(actual)
            .as(() -> "Actual should not have empty entries. " + collection)
            .noneMatch(single -> single.isBlank());
        if (!testSpec.regex.isEmpty()) {
            assertThat(item).matches(Pattern.compile(testSpec.regex));
        }
    }

    protected abstract Collection<TestSpec> providerListTest();

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
