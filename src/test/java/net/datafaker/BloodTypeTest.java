package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BloodTypeTest extends AbstractFakerTest {

    @Test
    void aboTypes() {
        assertThat(faker.bloodtype().aboTypes()).matches("[A-Za-z]+");
    }

    @Test
    void rhTypes() {
        assertThat(faker.bloodtype().rhTypes()).matches("[A-Za-z+-]+");
    }

    @Test
    void pTypes() {
        assertThat(faker.bloodtype().pTypes()).matches("[A-Za-z\\d]+");
    }

    @Test
    void testBloodGroup() {
        assertThat(faker.bloodtype().bloodGroup()).matches("(A|B|AB|O)[+-]");
    }

}
