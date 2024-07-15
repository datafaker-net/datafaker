package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ImageTest extends BaseFakerTest<BaseFaker> {

    @Test
    void png() {
        assertThat(faker.image().base64PNG()).startsWith("data:image/png;base64,");
    }

    @Test
    void jpg() {
        assertThat(faker.image().base64JPG()).startsWith("data:image/jpeg;base64,");
    }

    @Test
    void gif() {
        assertThat(faker.image().base64GIF()).startsWith("data:image/gif;base64,");
    }

    @Test
    void svg() {
        assertThat(faker.image().base64SVG()).startsWith("data:image/svg+xml;base64,");
    }
}
