package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DarkSouleTest extends AbstractFakerTest {

    @Test
    void testClasses() {
        assertThat(faker.darkSoul().classes()).matches("[A-Za-z]+");
    }

    @Test
    void testCovenants() {
        assertThat(faker.darkSoul().covenants()).matches("[A-Za-z ]+");
    }
    @Test
    void testWeapon() {
        assertThat(faker.darkSoul().weapon()).matches("[A-Za-z ]+");
    }
    @Test
    void testShield() {
        assertThat(faker.darkSoul().shield()).matches("[A-Za-z ]+");
    }
    @Test
    void testStats() {
        assertThat(faker.darkSoul().stats()).matches("[A-Za-z ]+");
    }
}