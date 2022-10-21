package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HouseTest extends BaseFakerTest<BaseFaker> {

    @Test
    void testFurniture() {
        assertThat(faker.house().furniture()).matches("^[a-zA-Z ]+$");
    }

    @Test
    void testRoom() {
        assertThat(faker.house().room()).matches("^[a-zA-Z ]+$");
    }
}
