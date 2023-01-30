package net.datafaker.providers.entertainment;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FamilyGuyTest extends EntertainmentFakerTest {

    @Test
    void characterTest() {
        assertThat(faker.familyGuy().character()).isNotEmpty();
    }

    @Test
    void locationTest() {
        assertThat(faker.familyGuy().location()).isNotEmpty();
    }

    @Test
    void quoteTest() {
        assertThat(faker.familyGuy().quote()).isNotEmpty();
    }

}
