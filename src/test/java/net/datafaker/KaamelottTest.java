package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class KaamelottTest extends AbstractFakerTest {

    @Test
    void testCharacter() {
        assertThat(faker.kaamelott().character()).matches("[A-Za-z' -ÇÉàçêèéïîüùú]+");
    }

    @Test
    void testQuote() {
        assertThat(faker.kaamelott().quote()).matches("[-A-Za-z0-9 —ÇÉàçêèéïîüùú;:…?!.’‘'”“,\\[\\]]+");
    }
}
