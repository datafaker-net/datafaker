package net.datafaker.providers.base;

import net.datafaker.Faker;
import net.datafaker.providers.base.Image.ImageType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static net.datafaker.assertions.ImageAssertions.assertThatImage;
import static net.datafaker.providers.base.Image.ImageType.BMP;
import static net.datafaker.providers.base.Image.ImageType.GIF;
import static net.datafaker.providers.base.Image.ImageType.JPEG;
import static net.datafaker.providers.base.Image.ImageType.PNG;
import static net.datafaker.providers.base.Image.ImageType.SVG;
import static net.datafaker.providers.base.Image.ImageType.TIFF;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class ImageTest {
    private final Faker faker = new Faker();

    @Test
    void bmp() {
        assertThatImage(faker.image().base64BMP()).is(BMP);
    }

    @Test
    void gif() {
        assertThatImage(faker.image().base64GIF()).is(GIF);
    }

    @Test
    void png() {
        assertThatImage(faker.image().base64PNG()).is(PNG);
    }

    @Test
    void jpg() {
        assertThatImage(faker.image().base64JPG()).is(JPEG);
    }

    @Test
    void jpeg() {
        assertThatImage(faker.image().base64JPEG()).is(JPEG);
    }

    @Test
    void svg() {
        assertThatImage(faker.image().base64SVG()).is(SVG);
    }

    @Test
    void tiff() {
        assertThatImage(faker.image().base64TIFF()).is(TIFF);
    }

    @ParameterizedTest
    @EnumSource(ImageType.class)
    void base64(ImageType imageType) {
        String base64Image = faker.image().base64(new Image.Base64ImageRuleConfig(imageType, 1000, 1000));

        assertThatImage(base64Image).is(imageType, 1000, 1000);
    }

    @Test
    void defaultBuilder_generatesPngImage() {
        String image = faker.image().base64(Image.ImageBuilder.builder().build());
        assertThatImage(image).is(PNG);
    }

    @Test
    void customBase64builder() {
        String gif = faker.image().base64(Image.ImageBuilder.builder()
            .type(ImageType.GIF)
            .build());
        assertThatImage(gif).is(GIF);
    }

    @Test
    void tinyBase64builder() {
        String tiny = faker.image().base64(Image.ImageBuilder.builder()
            .height(1)
            .width(1)
            .type(PNG)
            .build());

        assertThatImage(tiny).is(PNG, 1, 1);
    }

    @Test
    void largeBase64builder() {
        String large = faker.image().base64(Image.ImageBuilder.builder()
            .height(1000)
            .width(2000)
            .type(ImageType.BMP)
            .build());
        assertThatImage(large).is(BMP, 2000, 1000);
    }

    @Test
    void shouldErrorOnIllegalType() {
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> Image.ImageBuilder.builder().type(null).build());
    }

    @Test
    void shouldErrorOnNegativeWidth() {
        assertThatIllegalArgumentException().isThrownBy(() -> Image.ImageBuilder.builder().width(-1).build());
    }

    @Test
    void shouldErrorOnZeroWidth() {
        assertThatIllegalArgumentException().isThrownBy(() -> Image.ImageBuilder.builder().width(0).build());
    }

    @Test
    void shouldErrorOnNegativeHeight() {
        assertThatIllegalArgumentException().isThrownBy(() -> Image.ImageBuilder.builder().height(-1).build());
    }

    @Test
    void shouldErrorOnZeroHeight() {
        assertThatIllegalArgumentException().isThrownBy(() -> Image.ImageBuilder.builder().height(0).build());
    }
}
