package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CameraTest extends BaseFakerTest<BaseFaker> {

    @Test
    void brand() {
        assertThat(faker.camera().brand()).matches("^[a-zA-Z0-9 -]+$");
    }

    @Test
    void model() {
        assertThat(faker.camera().model()).matches("^[a-zA-Z0-9 -]+$");
    }

    @Test
    void brandWithModel() {
        assertThat(faker.camera().brandWithModel()).matches("^[a-zA-Z0-9 -]+$");
    }
}
