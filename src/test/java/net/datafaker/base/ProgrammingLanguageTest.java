package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProgrammingLanguageTest extends BaseFakerTest<BaseFaker> {

    @Test
    void name() {
        assertThat(faker.programmingLanguage().name()).matches("[A-Za-z\\d :,.+*()#/–\\-@πéöü'′!]+");
    }

    @Test
    void creator() {
        assertThat(faker.programmingLanguage().creator()).matches("[A-Za-z .,\\-]+");
    }
}
