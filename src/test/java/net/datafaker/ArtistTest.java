package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ArtistTest extends AbstractFakerTest {

    @Test
    void name() {
        assertThat(faker.artist().name()).matches("(\\w+ ?){1,2}");
    }
}
