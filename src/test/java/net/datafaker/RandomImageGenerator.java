package net.datafaker;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

public class RandomImageGenerator {

    private static final int WIDTH = 256;
    private static final int HEIGHT = 256;
    private static final int BOX_SIZE = WIDTH / 8;

    public static String generateBase64PNG() {
        return generateBase64Image("png");
    }

    public static String generateBase64JPG() {
        return generateBase64Image("jpg");
    }

    public static String generateBase64GIF() {
        return generateBase64Image("gif");
    }

    public static String generateBase64SVG() {
        return generateBase64SVGImage();
    }

    private static String generateBase64Image(String format) {
        BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Random random = new Random();
        Graphics2D graphics = bufferedImage.createGraphics();

        // Fill the image with white background
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);

        // Draw random colored boxes
        for (int y = 0; y < HEIGHT; y += BOX_SIZE) {
            for (int x = 0; x < WIDTH; x += BOX_SIZE) {
                int r = random.nextInt(256);
                int g = random.nextInt(256);
                int b = random.nextInt(256);
                Color color = new Color(r, g, b);
                graphics.setColor(color);
                graphics.fillRect(x, y, BOX_SIZE, BOX_SIZE);
            }
        }
        graphics.dispose();

        // Convert BufferedImage to Base64 String
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, format, baos);
            byte[] imageBytes = baos.toByteArray();
            return "data:image/" + format + ";base64," + Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String generateBase64SVGImage() {
        StringBuilder svg = new StringBuilder();
        Random random = new Random();

        svg.append("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"").append(WIDTH).append("\" height=\"").append(HEIGHT).append("\">");

        for (int y = 0; y < HEIGHT; y += BOX_SIZE) {
            for (int x = 0; x < WIDTH; x += BOX_SIZE) {
                int r = random.nextInt(256);
                int g = random.nextInt(256);
                int b = random.nextInt(256);
                String color = String.format("#%02x%02x%02x", r, g, b);
                svg.append("<rect x=\"").append(x).append("\" y=\"").append(y).append("\" width=\"").append(BOX_SIZE).append("\" height=\"").append(BOX_SIZE).append("\" fill=\"").append(color).append("\"/>");
            }
        }

        svg.append("</svg>");

        String svgString = svg.toString();
        String base64Svg = Base64.getEncoder().encodeToString(svgString.getBytes());
        return "data:image/svg+xml;base64," + base64Svg;
    }

    public static void main(String[] args) {
        System.out.println("PNG Image:");
        System.out.println(generateBase64PNG());
        System.out.println("JPG Image:");
        System.out.println(generateBase64JPG());
        System.out.println("GIF Image:");
        System.out.println(generateBase64GIF());
        System.out.println("SVG Image:");
        System.out.println(generateBase64SVG());
    }
}
