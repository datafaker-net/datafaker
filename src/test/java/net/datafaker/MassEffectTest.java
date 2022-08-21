package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;


class MassEffectTest extends AbstractFakerTest {

    @RepeatedTest(10)
    void character() {
        assertThat(faker.massEffect().character()).isNotEmpty();
    }

    @RepeatedTest(10)
    void species() {
        assertThat(faker.massEffect().specie()).isNotEmpty();
    }

    @RepeatedTest(10)
    void cluster() {
        assertThat(faker.massEffect().cluster()).isNotEmpty();
    }

    @RepeatedTest(10)
    void planets() {
        assertThat(faker.massEffect().planet()).isNotEmpty();
    }

    @RepeatedTest(10)
    void quote(){
        assertThat(faker.massEffect().quote()).isNotEmpty();
    }
}
