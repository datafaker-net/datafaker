package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProgrammingLanguageTest extends AbstractFakerTest {

    @Test
    void name() {
        assertThat(faker.programmingLanguage().name()).matches("[A-Za-z\\d :,.+*()#/–\\-@πéöü'′!]+");
    }

    @Test
    void creator() {
        assertThat(faker.programmingLanguage().creator()).matches("[A-Za-z .,\\-]+");
    }
}
