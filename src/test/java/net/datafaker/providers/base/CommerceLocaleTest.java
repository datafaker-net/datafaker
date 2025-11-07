package net.datafaker.providers.base;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CommerceLocaleTest extends ProviderListLocaleTest {

    @Override
    protected Stream<Arguments> localeProviderListTest() {
        return internationalFakers().stream().flatMap(faker -> {
                Commerce commerce = faker.commerce();
                return Stream.of(
                    arguments(TestSpec.of(commerce::material, "commerce.product_name.material", "[\\p{L}\\p{M}\\p{Pi}\\p{Pf}]{2,}"), faker),
                    arguments(TestSpec.of(commerce::brand, "commerce.brand", "\\w{2,}"), faker),
                    arguments(TestSpec.of(commerce::vendor, "commerce.vendor", "[\\w\\s]{6,}"), faker)
                );
            }
        );
    }
}
