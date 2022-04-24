package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

public class WeaponTest extends AbstractFakerTest {


    @RepeatedTest(500)
    public void getWeaponTest() {
        assertThat(faker.weapon().getWeapon()).matches("[a-zA-Z0-9\\-\\.\\ \\/]+");
    }


    @RepeatedTest(500)
    public void provenanceTest() {
        assertThat(faker.weapon().provenance()).matches("[a-zA-Z0-9\\-\\.\\ \\/]+");
    }


    @RepeatedTest(500)
    public void userTest() {
        assertThat(faker.weapon().user()).matches("[a-zA-Z0-9\\-\\.\\ \\/]+");
    }


    @RepeatedTest(500)
    public void functionTest() {
        assertThat(faker.weapon().function()).matches("[a-zA-Z0-9\\-\\.\\ \\/]+");
    }


    @RepeatedTest(500)
    public void targetTest() {
        assertThat(faker.weapon().target()).matches("[a-zA-Z0-9\\-\\.\\ \\/]+");
    }


    @RepeatedTest(500)
    public void typeTest() {
        assertThat(faker.weapon().type()).matches("[a-zA-Z0-9\\-\\.\\ \\/]+");
    }
}
