package net.datafaker.providers.base;

import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ColorLocaleTest extends BaseFakerLocaleTest {

    private final List<BaseFaker> fakers = List.of(
        fakerUA, fakerJP, fakerBY, fakerUZ, fakerArabic, fakerCA,
        fakerCZ, fakerDE, fakerFR, fakerHU, fakerAM, fakerKR,
        fakerPT, fakerRU, fakerSE, fakerTH, fakerTR, fakerCH,
        fakerBR, fakerDK);

    @Override
    protected Stream<Arguments> localeProviderListTest() {
        return fakers.stream()
            .map(faker -> arguments(TestSpec.of(() -> faker.color().name(), "color.name"), faker));
    }
}
