package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HitchhikersGuideToTheGalaxyTest extends AbstractFakerTest {

    @Test
    void character() {
        assertThat(faker.hitchhikersGuideToTheGalaxy().character()).matches("^(\\w+(\\.?\\s?'?))+$");
    }

    @Test
    void location() {
        assertThat(faker.hitchhikersGuideToTheGalaxy().location()).matches("^(\\w+\\S?\\.?\\s?'?-?)+$");
    }

    @Test
    void marvinQuote() {
        assertThat(faker.hitchhikersGuideToTheGalaxy().marvinQuote()).isNotEmpty();
    }

    @Test
    void planet() {
        assertThat(faker.hitchhikersGuideToTheGalaxy().planet()).matches("^(\\w+-?\\s?)+$");
    }

    @Test
    void quote() {
        assertThat(faker.hitchhikersGuideToTheGalaxy().quote()).isNotEmpty();
    }

    @Test
    void species() {
        assertThat(faker.hitchhikersGuideToTheGalaxy().species()).matches("^(\\w+'?\\s?)+$");
    }
}
