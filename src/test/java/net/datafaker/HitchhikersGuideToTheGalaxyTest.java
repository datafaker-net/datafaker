package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HitchhikersGuideToTheGalaxyTest extends AbstractFakerTest {

    @Test
    public void character() {
        assertThat(faker.hitchhikersGuideToTheGalaxy().character()).matches("^(\\w+(\\.?\\s?'?))+$");
    }

    @Test
    public void location() {
        assertThat(faker.hitchhikersGuideToTheGalaxy().location()).matches("^(\\w+\\S?\\.?\\s?'?-?)+$");
    }

    @Test
    public void marvinQuote() {
        assertThat(faker.hitchhikersGuideToTheGalaxy().marvinQuote()).isNotEmpty();
    }

    @Test
    public void planet() {
        assertThat(faker.hitchhikersGuideToTheGalaxy().planet()).matches("^(\\w+-?\\s?)+$");
    }

    @Test
    public void quote() {
        assertThat(faker.hitchhikersGuideToTheGalaxy().quote()).isNotEmpty();
    }

    @Test
    public void species() {
        assertThat(faker.hitchhikersGuideToTheGalaxy().species()).matches("^(\\w+'?\\s?)+$");
    }
}
