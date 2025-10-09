package net.datafaker.assertions;

import net.datafaker.providers.base.Image.ImageType;
import org.assertj.core.api.AbstractAssert;
import org.opentest4j.AssertionFailedError;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class ImageAssert extends AbstractAssert<ImageAssert, ImageData> {
    private static final Pattern RE = Pattern.compile("data:image/.+;base64,(.+)");

    ImageAssert(ImageData actual) {
        super(actual, ImageAssert.class);
    }

    public ImageAssert is(ImageType type) {
        return is(type, 256, 256);
    }

    public ImageAssert is(ImageType type, int width, int height) {
        assertImageDataUrl(type);
        return switch (type) {
            case SVG -> verifySvg(actual, width, height);
            case BMP, GIF, JPEG, PNG, TIFF -> verifyImage(actual, width, height);
        };
    }

    private void assertImageDataUrl(ImageType type) {
        String prefix = "data:%s;base64,".formatted(type.getMimeType());

        if (!actual.url().startsWith(prefix)) {
            String message = "Expected image data URL starting with \"%s\", but received: \"%s\"".formatted(prefix, actual.url());
            throw new AssertionFailedError(message, prefix, actual.url());
        }

        assertThat(actual.url().substring(actual.url().indexOf(",") + 1))
            .isNotBlank()
            .isBase64();
    }

    private ImageAssert verifyImage(ImageData image, int width, int height) {
        String content = RE.matcher(image.url()).replaceFirst("$1");
        byte[] bytes = Base64.getDecoder().decode(content);
        try (InputStream in = new ByteArrayInputStream(bytes)) {
            BufferedImage i = ImageIO.read(in);
            assertThat(i.getWidth()).isEqualTo(width);
            assertThat(i.getHeight()).isEqualTo(height);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse image " + image.url(), e);
        }
        return this;
    }

    private ImageAssert verifySvg(ImageData image, int width, int height) {
        String content = RE.matcher(image.url()).replaceFirst("$1");
        byte[] bytes = Base64.getDecoder().decode(content);
        try {
            String xml = new String(bytes);
            assertThat(xml).matches("<svg xmlns=\".+\" width=\"256\" height=\"256\">.*</svg>");

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(bytes));
            NodeList childNodes = doc.getChildNodes();
            assertThat(childNodes.getLength()).isEqualTo(1);

            Node svg = childNodes.item(0);
            assertThat(svg.getNodeName()).isEqualTo("svg");

            int boxSize = width / 8;
            int rectCount = (width / boxSize) * (height / boxSize);
            assertThat(svg.getChildNodes().getLength()).isEqualTo(rectCount);
            assertThat(svg.getChildNodes().item(0).getNodeName()).isEqualTo("rect");
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse image " + image.url(), e);
        }
        return this;
    }
}
