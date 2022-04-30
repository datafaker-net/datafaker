package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class BloodTypeTest extends AbstractFakerTest {

    @Test
    void abo_types() {
        assertThat(faker.bloodtype().abo_types()).matches("[A-Za-z]+");
    }

    @Test
    void rh_types() {
        assertThat(faker.bloodtype().rh_types()).matches("[A-Za-z+-]+");
    }

    @Test
    void p_types() {
        assertThat(faker.bloodtype().p_types()).matches("[A-Za-z0-9]+");
    }

}
