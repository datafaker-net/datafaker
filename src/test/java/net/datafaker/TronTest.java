package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TronTest extends AbstractFakerTest {

    @Test
    public void characters() {
        assertThat(faker.tron().character()).isNotEmpty();
    }

    @Test
    public void character() {
        assertThat(faker.tron().character(Tron.Character.USER)).isNotEmpty();
    }

    @Test
    public void games() {
        assertThat(faker.tron().game()).isNotEmpty();
    }

    @Test
    public void locations() {
        assertThat(faker.tron().location()).isNotEmpty();
    }

    @Test
    public void quotes() {
        assertThat(faker.tron().quote()).isNotNull();
    }

    @Test
    public void quote() {
        assertThat(faker.tron().quote(Tron.Quote.DR_WALTER_GIBBS)).isNotNull();
    }

    @Test
    public void taglines() {
        assertThat(faker.tron().tagline()).isNotEmpty();
    }

    @Test
    public void vehicles() {
        assertThat(faker.tron().vehicle()).isNotEmpty();
    }

    @Test
    public void alternateCharacterSpellings() {
        assertThat(faker.tron().alternateCharacterSpelling()).isNotEmpty();
    }

    @Test
    public void alternateCharacterSpelling() {
        assertThat(faker.tron().alternateCharacterSpelling(Tron.AlternateCharacterSpelling.DR_LORA_BAINES)).isNotEmpty();
    }

}
