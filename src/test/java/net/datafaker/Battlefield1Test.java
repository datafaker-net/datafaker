package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class Battlefield1Test extends AbstractFakerTest {

    @RepeatedTest(50)
    void classes() {
        assertThat(faker.battlefield1().classes()).matches("[A-Za-z ]+");
    }

    @RepeatedTest(200)
    void weapon() {
        assertThat(faker.battlefield1().weapon()).matches("[A-Za-z0-9,\\- .()/]+");
    }

    @RepeatedTest(200)
    void vehicle() {
        assertThat(faker.battlefield1().vehicle()).matches("[A-Za-z0-9,\\- .()/]+");
    }

    @RepeatedTest(100)
    void map() {
        assertThat(faker.battlefield1().map()).matches("[A-Za-z0-9,\\- .()']+");
    }

    @RepeatedTest(20)
    void faction() {
        assertThat(faker.battlefield1().faction()).matches("[A-Za-z,\\- ]+");
    }
}
