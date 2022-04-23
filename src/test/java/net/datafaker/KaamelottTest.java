package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class KaamelottTest extends AbstractFakerTest {

    @Test
    public void testCharacter() {
        assertThat(faker.kaamelott().character()).matches("[A-Za-z' -ÇÉàçêèéïîüùú]+");
    }

    @Test
    public void testQuote() {
        assertThat(faker.kaamelott().quote()).matches("[-A-Za-z0-9 —ÇÉàçêèéïîüùú;:…?!.’‘'”“,\\[\\]]+");
    }
}
