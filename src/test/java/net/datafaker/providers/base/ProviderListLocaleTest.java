package net.datafaker.providers.base;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class ProviderListLocaleTest<T extends BaseFaker> extends AbstractProviderListTest<T> {

    @ParameterizedTest
    @MethodSource("localeProviderListTest")
    protected void testProviderListFromLocale(TestSpec testSpec, T faker) {
        testProviderList(testSpec, faker);
    }

    protected abstract Stream<Arguments> localeProviderListTest();
}
