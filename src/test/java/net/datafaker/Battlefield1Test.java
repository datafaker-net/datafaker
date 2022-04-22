package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Battlefield1Test extends AbstractFakerTest {

    //CS304 (manually written) Issue link: https://github.com/DiUS/java-faker/issues/711
    @Test
    public void classes() {
        assertThat(faker.battlefield1().classes()).matches("[A-Za-z ]+");
    }

    //CS304 (manually written) Issue link: https://github.com/DiUS/java-faker/issues/711
    @Test
    public void weapon() {
        assertThat(faker.battlefield1().weapon()).matches("[A-Za-z0-9,\\- .()/]+");
    }

    //CS304 (manually written) Issue link: https://github.com/DiUS/java-faker/issues/711
    @Test
    public void vehicle() {
        assertThat(faker.battlefield1().vehicle()).matches("[A-Za-z0-9,\\- .()/]+");
    }

    //CS304 (manually written) Issue link: https://github.com/DiUS/java-faker/issues/711
    @Test
    public void map() {
        assertThat(faker.battlefield1().map()).matches("[A-Za-z0-9,\\- .()']+");
    }

    //CS304 (manually written) Issue link: https://github.com/DiUS/java-faker/issues/711
    @Test
    public void faction() {
        assertThat(faker.battlefield1().faction()).matches("[A-Za-z,\\- ]+");
    }
}
