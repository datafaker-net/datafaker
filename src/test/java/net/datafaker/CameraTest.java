package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CameraTest extends AbstractFakerTest {

    @Test
    void testBrand() {
        assertThat(faker.camera().brand()).matches("^[a-zA-Z0-9 -]+$");
    }

    @Test
    void testModel() {
        assertThat(faker.camera().model()).matches("^[a-zA-Z0-9 -]+$");
    }

    @Test
    void testBrandWithModel() {
        assertThat(faker.camera().brandWithModel()).matches("^[a-zA-Z0-9 -]+$");
    }
}
