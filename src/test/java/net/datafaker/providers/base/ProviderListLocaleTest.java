package net.datafaker.providers.base;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class ProviderListLocaleTest extends AbstractProviderListTest<BaseFaker> {

    protected final BaseFaker fakerUA = new BaseFaker(new Locale("uk", "UA"));
    protected final BaseFaker fakerIE = new BaseFaker(new Locale("en", "IE"));
    protected final BaseFaker fakerUZ = new BaseFaker(new Locale("uz", "UZ"));
    protected final BaseFaker fakerNZ = new BaseFaker(new Locale("en", "NZ"));

    @ParameterizedTest
    @MethodSource("localeProviderListTest")
    protected void testProviderListFromLocale(TestSpec testSpec, BaseFaker faker) {
        testProviderList(testSpec, faker);
    }

    protected abstract Stream<Arguments> localeProviderListTest();
}
