package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DungeonsAndDragonsTest extends net.datafaker.AbstractFakerTest {

    @Test
    public void alignments() {
        assertThat(faker.dungeonsAndDragons().alignments()).isNotEmpty();
    }

    @Test
    public void backgrounds() {
        assertThat(faker.dungeonsAndDragons().backgrounds()).isNotEmpty();
    }

    @Test
    public void cities() {
        assertThat(faker.dungeonsAndDragons().cities()).isNotEmpty();
    }

    @Test
    public void klasses() {
        assertThat(faker.dungeonsAndDragons().klasses()).isNotEmpty();
    }

    @Test
    public void languages() {
        assertThat(faker.dungeonsAndDragons().languages()).isNotEmpty();
    }

    @Test
    public void meleeWeapons() {
        assertThat(faker.dungeonsAndDragons().meleeWeapons()).isNotEmpty();
    }

    @Test
    public void monsters() {
        assertThat(faker.dungeonsAndDragons().monsters()).isNotEmpty();
    }

    @Test
    public void races() {
        assertThat(faker.dungeonsAndDragons().races()).isNotEmpty();
    }

    @Test
    public void rangedWeapons() {
        assertThat(faker.dungeonsAndDragons().rangedWeapons()).isNotEmpty();
    }

}
