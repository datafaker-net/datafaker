package net.datafaker.providers.movie;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class HitchhikersGuideToTheGalaxyTest extends MovieFakerTest {

    @Test
    void character() {
        Assertions.assertThat(faker.hitchhikersGuideToTheGalaxy().character()).matches("^(\\w+(\\.?\\s?'?))+$");
    }

    @Test
    void location() {
        Assertions.assertThat(faker.hitchhikersGuideToTheGalaxy().location()).matches("^(\\w+\\S?\\.?\\s?'?-?)+$");
    }

    @Test
    void marvinQuote() {
        Assertions.assertThat(faker.hitchhikersGuideToTheGalaxy().marvinQuote()).isNotEmpty();
    }

    @Test
    void planet() {
        Assertions.assertThat(faker.hitchhikersGuideToTheGalaxy().planet()).matches("^(\\w+-?\\s?)+$");
    }

    @Test
    void quote() {
        Assertions.assertThat(faker.hitchhikersGuideToTheGalaxy().quote()).isNotEmpty();
    }

    @Test
    void species() {
        Assertions.assertThat(faker.hitchhikersGuideToTheGalaxy().species()).matches("^(\\w+'?\\s?)+$");
    }
}
