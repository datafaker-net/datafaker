package net.datafaker.providers.base;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractBasicProviderTest<T extends BaseFaker> extends BaseFakerTest<BaseFaker> {
    @SuppressWarnings("unchecked")
    protected List<String> getBaseList(String key) {
        return (List<String>) faker.fakeValuesService().fetchObject(key, faker.getContext());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("providerListTest")
    protected void testProviderList(TestSpec testSpec) {
        // Given
        List<String> actual = getBaseList(testSpec.key);
        // When
        String item = (String) testSpec.supplier.get();
        // Then
        assertThat(item).as("Check item isn't empty").isNotEmpty();
        assertThat(actual).as("Check actual list isn't empty and contains the item").isNotEmpty()
                .anyMatch(item::equals);
    }

    protected abstract Collection<TestSpec> providerListTest();

    protected static class TestSpec {
        private final Supplier<?> supplier;
        private final String key;

        private TestSpec(Supplier<?> supplier, String key) {
            this.supplier = supplier;
            this.key = key;
        }

        public static TestSpec of(Supplier<?> supplier, String key) {
            return new TestSpec(supplier, key);
        }

        @Override
        public String toString() {
            // The result of this toString will be used by IDE in test report
            return "Key: " + key;
        }
    }
}
