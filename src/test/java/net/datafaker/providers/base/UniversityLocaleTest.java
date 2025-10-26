package net.datafaker.providers.base;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class UniversityLocaleTest extends BaseFakerLocaleTest {
    private final University universityUA = fakerUA.university();
    private final University universityUZ = fakerUZ.university();

    @Override
    protected Stream<Arguments> localeProviderListTest() {
        return Stream.of(
            arguments(TestSpec.of(universityUA::name, "university.name"), fakerUA),
            arguments(TestSpec.of(universityUZ::name, "university.name"), fakerUZ)
        );
    }
}
