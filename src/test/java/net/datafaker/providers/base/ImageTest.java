package net.datafaker.providers.base;

import net.datafaker.providers.base.Image.ImageType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class ImageTest extends BaseFakerTest<BaseFaker> {

    @Test
    void bmp() {
        assertThat(faker.image().base64BMP()).startsWith("data:image/bmp;base64,");
    }

    @Test
    void gif() {
        assertThat(faker.image().base64GIF()).startsWith("data:image/gif;base64,");
    }

    @Test
    void png() {
        assertThat(faker.image().base64PNG()).startsWith("data:image/png;base64,");
    }

    @Test
    void jpg() {
        assertThat(faker.image().base64JPG()).startsWith("data:image/jpeg;base64,");
    }

    @Test
    void jpeg() {
        assertThat(faker.image().base64JPEG()).startsWith("data:image/jpeg;base64,");
    }

    @Test
    void svg() {
        assertThat(faker.image().base64SVG()).startsWith("data:image/svg+xml;base64,");
    }

    @Test
    void tiff() {
        assertThat(faker.image().base64TIFF()).startsWith("data:image/tiff;base64,");
    }

    @ParameterizedTest
    @EnumSource(ImageType.class)
    void base64(ImageType imageType) {
        String base64Image = faker.image().base64(new Image.Base64ImageRuleConfig(imageType, 1000, 1000));

        assertThat(base64Image)
            .startsWith("data:" + imageType.getMimeType() + ";base64,");
        assertThat(base64Image.substring(base64Image.indexOf(",") + 1))
            .isNotBlank()
            .isBase64();
    }

    @Test
    void defaultBuilder() {
        String image = faker.image().base64(Image.ImageBuilder.builder()
            .build());
        assertThat(image).startsWith("data:image/");
    }

    @Test
    void customBase64builder() {
        String gif = faker.image().base64(Image.ImageBuilder.builder()
            .type(ImageType.GIF)
            .build());
        assertThat(gif).startsWith("data:image/gif;base64,");
    }

    @Test
    void tinyBase64builder() {
        String tiny = faker.image().base64(Image.ImageBuilder.builder()
            .height(1)
            .width(1)
            .type(ImageType.PNG)
            .build());

        assertThat(tiny).startsWith("data:image/png;base64,");
    }

    @Test
    void largeBase64builder() {
        String large = faker.image().base64(Image.ImageBuilder.builder()
            .height(1000)
            .width(2000)
            .type(ImageType.BMP)
            .build());
        assertThat(large).startsWith("data:image/bmp;base64,");
    }

    @Test
    void shouldErrorOnIllegalType() {
        assertThatIllegalArgumentException().isThrownBy(() -> Image.ImageBuilder.builder().type(null).build());
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
