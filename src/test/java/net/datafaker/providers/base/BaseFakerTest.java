package net.datafaker.providers.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseFakerTest<T extends BaseFaker> {

    private static final Logger LOG = Logger.getLogger(BaseFakerTest.class.getCanonicalName());
    protected final T faker = getFaker();

    @BeforeEach
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
        assertThat(actual).as("Check actual list isn't empty and contains the item for the key \"" + testSpec.key + "\"").isNotEmpty()
            .anyMatch(item::equals);
    }

    protected Collection<TestSpec> providerListTest() {
        // dummy test since parameterized test requires non-empty collection
        return Collections.singleton(new TestSpec(null, null));
    }

    protected static class TestSpec {
        private final Supplier<?> supplier;
        private final String key;
        private final boolean isDummy;

        private TestSpec(Supplier<?> supplier, String key) {
            this.supplier = supplier;
            this.key = key;
            this.isDummy = key == null || supplier == null;
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
