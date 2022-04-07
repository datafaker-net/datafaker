package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Luka Obradovic (luka@vast.com)
 */
public class LordOfTheRingsTest extends AbstractFakerTest {

    @Test
    public void character() {
        assertThat(faker.lordOfTheRings().character()).matches("(?U)([\\w ]+ ?)+");
    }

    @Test
    public void location() {
        assertThat(faker.lordOfTheRings().location()).matches("(?U)([\\w'\\- ]+ ?)+");
    }
}
