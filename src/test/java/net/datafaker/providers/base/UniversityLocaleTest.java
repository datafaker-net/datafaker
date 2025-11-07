package net.datafaker.providers.base;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class UniversityLocaleTest extends ProviderListLocaleTest {

    @Override
    protected Stream<Arguments> localeProviderListTest() {
        return Stream.of(fakerUA, fakerUZ, fakerNZ)
            .map(faker -> arguments(TestSpec.of(() -> faker.university().name(), "university.name", ".{15,120}"), faker));
    }
}
