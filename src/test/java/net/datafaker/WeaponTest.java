package net.datafaker;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class WeaponTest extends AbstractFakerTest {


    @Test
    public void getWeaponTest() {
        assertThat(faker.weapon().getWeapon()).matches("[a-zA-Z0-9\\-\\.\\ \\/]+");
    }


    @Test
    public void provenanceTest() {
        assertThat(faker.weapon().provenance()).matches("[a-zA-Z0-9\\-\\.\\ \\/]+");
    }


    @Test
    public void userTest() {
        assertThat(faker.weapon().user()).matches("[a-zA-Z0-9\\-\\.\\ \\/]+");
    }


    @Test
    public void functionTest() {
        assertThat(faker.weapon().function()).matches("[a-zA-Z0-9\\-\\.\\ \\/]+");
    }


    @Test
    public void targetTest() {
        assertThat(faker.weapon().target()).matches("[a-zA-Z0-9\\-\\.\\ \\/]+");
    }


    @Test
    public void typeTest() {
        assertThat(faker.weapon().type()).matches("[a-zA-Z0-9\\-\\.\\ \\/]+");
    }
}
