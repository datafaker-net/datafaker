package net.datafaker.providers.base;

import net.datafaker.providers.entertainment.EntertainmentProviders;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import static java.awt.Color.WHITE;

/**
 * Generates base64 encoded PNG, GIF, JPG and SVG images.
 *
 * @since 2.3.0
 */
public class Image extends AbstractProvider<EntertainmentProviders> {

    private static final int WIDTH = 256;
    private static final int HEIGHT = 256;
    private static final int BOX_SIZE = WIDTH / 8;

    protected Image(EntertainmentProviders faker) {
        super(faker);
    }

    public String base64PNG() {
        return generateBase64Image("png");
    }

    public String base64JPG() {
        return generateBase64Image("jpg");
    }

    public String base64GIF() {
        return generateBase64Image("gif");
    }

    public String base64SVG() {
        return generateBase64SVGImage();
    }

    private String generateBase64Image(String format) {
        BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();

        // Fill the image with white background
        graphics.setColor(WHITE);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);

        // Draw random colored boxes
        for (int y = 0; y < HEIGHT; y += BOX_SIZE) {
            for (int x = 0; x < WIDTH; x += BOX_SIZE) {
                int r = faker.random().nextInt(256);
                int g = faker.random().nextInt(256);
                int b = faker.random().nextInt(256);
                java.awt.Color color = new Color(r, g, b);
                graphics.setColor(color);
                graphics.fillRect(x, y, BOX_SIZE, BOX_SIZE);
            }
        }
        graphics.dispose();

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, format, baos);
            byte[] imageBytes = baos.toByteArray();
            return "data:image/" + format + ";base64," + Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String generateBase64SVGImage() {
        StringBuilder svg = new StringBuilder();
        svg.append("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"").append(WIDTH).append("\" height=\"").append(HEIGHT).append("\">");

        for (int y = 0; y < HEIGHT; y += BOX_SIZE) {
            for (int x = 0; x < WIDTH; x += BOX_SIZE) {
                int r = faker.random().nextInt(256);
                int g = faker.random().nextInt(256);
                int b = faker.random().nextInt(256);
                String color = String.format("#%02x%02x%02x", r, g, b);
                svg.append("<rect x=\"").append(x).append("\" y=\"").append(y).append("\" width=\"").append(BOX_SIZE).append("\" height=\"").append(BOX_SIZE).append("\" fill=\"").append(color).append("\"/>");
            }
        }

        svg.append("</svg>");

        String svgString = svg.toString();
        String base64Svg = Base64.getEncoder().encodeToString(svgString.getBytes());
        return "data:image/svg+xml;base64," + base64Svg;
    }
}
