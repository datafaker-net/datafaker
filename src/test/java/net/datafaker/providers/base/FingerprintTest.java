package net.datafaker.providers.base;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class FingerprintTest {

    private final Faker faker = new Faker();

    @Test
    void pngReturnsPngBytes() {
        byte[] bytes = faker.fingerprint().png();

        assertThat(bytes).isNotEmpty();
        // PNG magic: 0x89 P N G
        assertThat(bytes[0]).isEqualTo((byte) 0x89);
        assertThat(bytes[1]).isEqualTo((byte) 'P');
        assertThat(bytes[2]).isEqualTo((byte) 'N');
        assertThat(bytes[3]).isEqualTo((byte) 'G');
    }

    @Test
    void pngHasExpectedDimensions() throws IOException {
        byte[] bytes = faker.fingerprint().png();

        BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
        assertThat(image.getWidth()).isEqualTo(200);
        assertThat(image.getHeight()).isEqualTo(250);
    }

    @Test
    void base64ReturnsDataUrl() {
        String dataUrl = faker.fingerprint().base64();

        assertThat(dataUrl).startsWith("data:image/png;base64,");
        String encoded = dataUrl.substring("data:image/png;base64,".length());
        assertThat(encoded).isNotBlank().isBase64();
    }

    @Test
    void base64DecodesBackToPng() throws IOException {
        String dataUrl = faker.fingerprint().base64();

        String encoded = dataUrl.substring("data:image/png;base64,".length());
        byte[] bytes = Base64.getDecoder().decode(encoded);
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
        assertThat(image).isNotNull();
        assertThat(image.getWidth()).isEqualTo(200);
        assertThat(image.getHeight()).isEqualTo(250);
    }

    @ParameterizedTest
    @EnumSource(Fingerprint.PatternType.class)
    void eachPatternTypeProducesValidPng(Fingerprint.PatternType pattern) throws IOException {
        byte[] bytes = faker.fingerprint().png(400, 500, pattern);

        assertThat(bytes).isNotEmpty();
        assertThat(bytes[0]).isEqualTo((byte) 0x89);
        assertThat(bytes[1]).isEqualTo((byte) 'P');

        BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
        assertThat(image.getWidth()).isEqualTo(400);
        assertThat(image.getHeight()).isEqualTo(500);

        File folder = new File("target/surefire-reports");
        folder.mkdirs();
        ImageIO.write(image, "png", new File(folder, "fingerprint-" + pattern + ".png"));
    }

    @Test
    void twoPngCallsProduceDifferentImages() {
        byte[] first  = faker.fingerprint().png();
        byte[] second = faker.fingerprint().png();

        // Extremely unlikely to be identical given randomised parameters
        assertThat(first).isNotEqualTo(second);
    }

    @Test
    void twoPngCallsWithSameSeeProduceSameImages() {
        long seed = System.nanoTime();
        byte[] first  = new Faker(new Random(seed)).fingerprint().png();
        byte[] second = new Faker(new Random(seed)).fingerprint().png();

        assertThat(first).isEqualTo(second);
    }
}
