package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class DungeonsAndDragonsTest extends net.datafaker.AbstractFakerTest {

    @Test
    void alignments() {
        assertThat(faker.dungeonsAndDragons().alignments()).isNotEmpty();
    }

    @Test
    void backgrounds() {
        assertThat(faker.dungeonsAndDragons().backgrounds()).isNotEmpty();
    }

    @Test
    void cities() {
        assertThat(faker.dungeonsAndDragons().cities()).isNotEmpty();
    }

    @Test
    void klasses() {
        assertThat(faker.dungeonsAndDragons().klasses()).isNotEmpty();
    }

    @Test
    void languages() {
        assertThat(faker.dungeonsAndDragons().languages()).isNotEmpty();
    }

    @Test
    void meleeWeapons() {
        assertThat(faker.dungeonsAndDragons().meleeWeapons()).isNotEmpty();
    }

    @Test
    void monsters() {
        assertThat(faker.dungeonsAndDragons().monsters()).isNotEmpty();
    }

    @Test
    void races() {
        assertThat(faker.dungeonsAndDragons().races()).isNotEmpty();
    }

    @Test
    void rangedWeapons() {
        assertThat(faker.dungeonsAndDragons().rangedWeapons()).isNotEmpty();
    }

}
