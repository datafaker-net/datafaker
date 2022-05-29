package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DarkSoulTest extends AbstractFakerTest {

    @RepeatedTest(10)
    void testClasses() {
        assertThat(faker.darkSoul().classes()).matches("[A-Za-z ']+");
    }

    @RepeatedTest(10)
    void testCovenants() {
        assertThat(faker.darkSoul().covenants()).matches("[A-Za-z ']+");
    }

    @RepeatedTest(10)
    void testShield() {
        assertThat(faker.darkSoul().shield()).matches("[A-Za-z ']+");
    }

    @RepeatedTest(10)
    void testStats() {
        assertThat(faker.darkSoul().stats()).matches("[A-Za-z ']+");
    }
}
