package net.datafaker.videogame;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;


class Battlefield1Test extends VideoGameFakerTest {

    @RepeatedTest(50)
    void classes() {
        Assertions.assertThat(faker.battlefield1().classes()).matches("[A-Za-z ]+");
    }

    @RepeatedTest(200)
    void weapon() {
        Assertions.assertThat(faker.battlefield1().weapon()).matches("[A-Za-z0-9,\\- .()/]+");
    }

    @RepeatedTest(200)
    void vehicle() {
        Assertions.assertThat(faker.battlefield1().vehicle()).matches("[A-Za-z0-9,\\- .()/]+");
    }

    @RepeatedTest(100)
    void map() {
        Assertions.assertThat(faker.battlefield1().map()).matches("[A-Za-z0-9,\\- .()']+");
    }

    @RepeatedTest(20)
    void faction() {
        Assertions.assertThat(faker.battlefield1().faction()).matches("[A-Za-z,\\- ]+");
    }
}
