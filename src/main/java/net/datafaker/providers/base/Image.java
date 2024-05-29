package net.datafaker.providers.base;


import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
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
public class Image extends AbstractProvider<BaseProviders> {

    private static final int WIDTH = 256;
    private static final int HEIGHT = 256;
    private static final int BOX_SIZE = WIDTH / 8;

    protected Image(BaseProviders faker) {
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
                graphics.setColor(randomColor());
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

    private Color randomColor() {
        // Convert the bytes to unsigned integers (0-255) for RGB
        byte[] randomBytes = faker.random().nextRandomBytes(3);
        int red = randomBytes[0] & 0xFF;
        int green = randomBytes[1] & 0xFF;
        int blue = randomBytes[2] & 0xFF;
        return new Color(red, green, blue);
    }

    private String generateBase64SVGImage() {
        StringBuilder svg = new StringBuilder();
        svg.append("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"").append(WIDTH).append("\" height=\"").append(HEIGHT).append("\">");

        for (int y = 0; y < HEIGHT; y += BOX_SIZE) {
            for (int x = 0; x < WIDTH; x += BOX_SIZE) {
                Color randomColor = randomColor();
                String color = String.format("#%02x%02x%02x", randomColor.getRed(), randomColor.getGreen(), randomColor.getBlue());
                svg.append("<rect x=\"").append(x).append("\" y=\"").append(y).append("\" width=\"").append(BOX_SIZE).append("\" height=\"").append(BOX_SIZE).append("\" fill=\"").append(color).append("\"/>");
            }
        }

        svg.append("</svg>");

        String svgString = svg.toString();
        String base64Svg = Base64.getEncoder().encodeToString(svgString.getBytes());
        return "data:image/svg+xml;base64," + base64Svg;
    }
}
