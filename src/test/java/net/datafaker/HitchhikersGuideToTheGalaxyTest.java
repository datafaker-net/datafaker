package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
        assertFalse(faker.hitchhikersGuideToTheGalaxy().marvinQuote().isEmpty());
    }

    @Test
    public void planet() {
        assertThat(faker.hitchhikersGuideToTheGalaxy().planet()).matches("^(\\w+-?\\s?)+$");
    }

    @Test
    public void quote() {
        assertFalse(faker.hitchhikersGuideToTheGalaxy().quote().isEmpty());
    }

    @Test
    public void species() {
        assertThat(faker.hitchhikersGuideToTheGalaxy().species()).matches("^(\\w+'?\\s?)+$");
    }
}
