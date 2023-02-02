package net.datafaker.providers.sport;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SportBrandTest extends SportFakerTest {

    @Test
    void testBrand() {
        assertThat(faker.sportbrand().brand()).isNotEmpty();
    }

}
