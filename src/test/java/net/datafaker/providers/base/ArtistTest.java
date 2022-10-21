package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ArtistTest extends BaseFakerTest<BaseFaker> {

    @Test
    void name() {
        assertThat(faker.artist().name()).matches("(\\w+ ?){1,2}");
    }
}
