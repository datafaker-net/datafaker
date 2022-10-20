package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest extends BaseFakerTest<BaseFaker> {

    @Test
    void name() {
        assertThat(faker.app().name()).matches("([\\w-]+ ?)+");
    }

    @Test
    void version() {
        assertThat(faker.app().version()).matches("\\d\\.(\\d){1,2}(\\.\\d)?");
    }

    @Test
    void author() {
        assertThat(faker.app().author()).matches("([\\w']+[-&,.]? ?){2,9}");
    }
}
