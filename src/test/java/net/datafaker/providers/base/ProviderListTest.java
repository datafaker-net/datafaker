package net.datafaker.providers.base;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class ProviderListTest<T extends BaseFaker> extends AbstractProviderListTest<T> {
    protected abstract T getFaker();


    @ParameterizedTest
    @MethodSource("providerListTest")
    protected void testProviderList(TestSpec testSpec) {
        testProviderList(testSpec, getFaker());
    }

    protected abstract Collection<TestSpec> providerListTest();
}
