package net.datafaker.providers.base;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;


class DungeonsAndDragonsTest extends net.datafaker.AbstractFakerTest {

    @Test
    void alignments() {
        AssertionsForClassTypes.assertThat(faker.dungeonsAndDragons().alignments()).isNotEmpty();
    }

    @Test
    void backgrounds() {
        AssertionsForClassTypes.assertThat(faker.dungeonsAndDragons().backgrounds()).isNotEmpty();
    }

    @Test
    void cities() {
        AssertionsForClassTypes.assertThat(faker.dungeonsAndDragons().cities()).isNotEmpty();
    }

    @Test
    void klasses() {
        AssertionsForClassTypes.assertThat(faker.dungeonsAndDragons().klasses()).isNotEmpty();
    }

    @Test
    void languages() {
        AssertionsForClassTypes.assertThat(faker.dungeonsAndDragons().languages()).isNotEmpty();
    }

    @Test
    void meleeWeapons() {
        AssertionsForClassTypes.assertThat(faker.dungeonsAndDragons().meleeWeapons()).isNotEmpty();
    }

    @Test
    void monsters() {
        AssertionsForClassTypes.assertThat(faker.dungeonsAndDragons().monsters()).isNotEmpty();
    }

    @Test
    void races() {
        AssertionsForClassTypes.assertThat(faker.dungeonsAndDragons().races()).isNotEmpty();
    }

    @Test
    void rangedWeapons() {
        AssertionsForClassTypes.assertThat(faker.dungeonsAndDragons().rangedWeapons()).isNotEmpty();
    }

}
