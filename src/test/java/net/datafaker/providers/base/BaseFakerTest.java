package net.datafaker.providers.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseFakerTest<T extends BaseFaker> {

    private static final Logger LOG = Logger.getLogger(BaseFakerTest.class.getCanonicalName());
    protected final T faker = getFaker();

    @BeforeEach
    @SuppressWarnings("unchecked")
    protected void before() {
        try (AutoCloseable ignored = MockitoAnnotations.openMocks(this)) {

            Logger rootLogger = LogManager.getLogManager().getLogger("");
            Handler[] handlers = rootLogger.getHandlers();
            rootLogger.setLevel(Level.INFO);
            for (Handler h : handlers) {
                h.setLevel(Level.INFO);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    protected T getFaker() {
        return (T) new BaseFaker();
    }

    protected <U extends ProviderRegistration> AbstractProvider<U> getProvider() {
        return null;
    }

    @SuppressWarnings("unchecked")
    protected List<String> getBaseList(String key) {
        return (List<String>) faker.fakeValuesService().fetchObject(key, faker.getContext());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("providerListTest")
    protected void testProviderList(TestSpec testSpec, TestInfo testInfo) {
        if (testSpec.isDummy) {
            // skip and log dummy
            LOG.log(Level.WARNING, "Dummy test for " + testInfo.getTestClass().get());
            return;
        }
        // Given
        List<String> actual = getBaseList(testSpec.key);
        // When
        String item = (String) testSpec.supplier.get();
        // Then
        assertThat(item).as("Check item isn't empty").isNotEmpty();
        String collection = "\"" + testSpec.key + "\"";
        assertThat(actual).as("Check actual list isn't empty and contains the item for the key " + collection).isNotEmpty()
            .anyMatch(item::equals);
        assertThat(actual).as("Actual should not have empty entries. " + collection).noneMatch(single -> single.isBlank());
        if (!testSpec.regex.isEmpty()) {
            assertThat(item).as("Check item matches regex").matches(Pattern.compile(testSpec.regex));
        }
    }

    protected Collection<TestSpec> providerListTest() {
        // dummy test since parameterized test requires non-empty collection
        return Set.of(new TestSpec(null, null, null));
    }

    protected static class TestSpec {
        private final Supplier<?> supplier;
        private final String key;
        private final boolean isDummy;
        @SuppressWarnings("unused")
        private final String regex;

        private TestSpec(Supplier<?> supplier, String key, String regex) {
            this.supplier = supplier;
            this.key = key;
            this.isDummy = key == null || supplier == null;
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
