package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TronTest extends BaseFakerTest<BaseFaker> {

    @Test
    void characters() {
        assertThat(faker.tron().character()).isNotEmpty();
    }

    @Test
    void character() {
        assertThat(faker.tron().character(Tron.Character.USER)).isNotEmpty();
    }

    @Test
    void games() {
        assertThat(faker.tron().game()).isNotEmpty();
    }

    @Test
    void locations() {
        assertThat(faker.tron().location()).isNotEmpty();
    }

    @Test
    void quotes() {
        assertThat(faker.tron().quote()).isNotNull();
    }

    @Test
    void quote() {
        assertThat(faker.tron().quote(Tron.Quote.DR_WALTER_GIBBS)).isNotNull();
    }

    @Test
    void taglines() {
        assertThat(faker.tron().tagline()).isNotEmpty();
    }

    @Test
    void vehicles() {
        assertThat(faker.tron().vehicle()).isNotEmpty();
    }

    @Test
    void alternateCharacterSpellings() {
        assertThat(faker.tron().alternateCharacterSpelling()).isNotEmpty();
    }

    @Test
    void alternateCharacterSpelling() {
        assertThat(faker.tron().alternateCharacterSpelling(Tron.AlternateCharacterSpelling.DR_LORA_BAINES)).isNotEmpty();
    }

}
