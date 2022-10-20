package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BloodTypeTest extends BaseFakerTest<BaseFaker> {

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
    void bloodGroup() {
        assertThat(faker.bloodtype().bloodGroup()).matches("(A|B|AB|O)[+-]");
    }

}
