package net.datafaker.providers.base;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ColorLocaleTest extends ProviderListLocaleTest {

    @Override
    protected Stream<Arguments> localeProviderListTest() {
        return internationalFakers().stream()
            .map(faker -> arguments(TestSpec.of(() -> faker.color().name(), "color.name"), faker));
    }
}
