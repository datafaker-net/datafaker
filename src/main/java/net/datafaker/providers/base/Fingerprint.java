package net.datafaker.providers.base;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * Generates synthetic fingerprint images.
 * <p>
 * The generated images mimic the three standard fingerprint pattern types:
 * whorl (spiral ridges), loop (ridges that loop around a core), and arch
 * (ridges that arch over the centre).  A random pattern is chosen unless one
 * is supplied explicitly.
 *
 * @since 2.6.0
 */
public class Fingerprint extends AbstractProvider<BaseProviders> {

    private static final int DEFAULT_WIDTH = 200;
    private static final int DEFAULT_HEIGHT = 250;
    private static final double TWO_PI = 2 * Math.PI;

    /**
     * The three standard fingerprint ridge-flow patterns.
     */
    public enum PatternType {WHORL, LOOP, ARCH}

    protected Fingerprint(BaseProviders faker) {
        super(faker);
    }

    /**
     * Returns raw PNG bytes of a random-pattern fingerprint image.
     */
    public byte[] png() {
        return png(DEFAULT_WIDTH, DEFAULT_HEIGHT, randomType());
    }

    /**
     * Returns raw PNG bytes of a fingerprint image with the specified size and pattern.
     */
    public byte[] png(int width, int height, PatternType pattern) {
        return encodePng(render(width, height, pattern));
    }

    /**
     * Returns a base64 PNG data URL of a random-pattern fingerprint.
     */
    public String base64() {
        return base64(DEFAULT_WIDTH, DEFAULT_HEIGHT, randomType());
    }

    /**
     * Returns a base64 PNG data URL of a random-pattern fingerprint image with the specified size and pattern.
     */
    public String base64(int width, int height, PatternType pattern) {
        byte[] png = png(width, height, pattern);
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(png);
    }

    private PatternType randomType() {
        return faker.random().nextEnum(PatternType.class);
    }

    private byte[] encodePng(BufferedImage image) {
        try (var baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "PNG", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to encode fingerprint as PNG", e);
        }
    }

    private BufferedImage render(int width, int height, PatternType patternType) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        // Core position – slightly randomised around the centre
        double cx = width * (0.47 + faker.random().nextDouble(-0.04, 0.04));
        double cy = height * (0.47 + faker.random().nextDouble(-0.04, 0.04));

        // Ridge parameters
        double ridgePeriod = 11.0 + faker.random().nextDouble(-2.0, 3.0);
        double ridgeFraction = 0.42 + faker.random().nextDouble(-0.04, 0.06);

        // Global pattern parameters (drive the overall shape – spiral / loop / arch)
        double spiralFactor = 0.5 + faker.random().nextDouble(0.0, 0.5);
        double loopAmplitude = height * (0.18 + faker.random().nextDouble(0.0, 0.10));
        double loopSigma = Math.min(width, height) * (0.28 + faker.random().nextDouble(0.0, 0.12));
        double archAmplitude = height * (0.12 + faker.random().nextDouble(0.0, 0.08));
        double archSigma = width * (0.30 + faker.random().nextDouble(0.0, 0.15));

        // Organic displacement noise: three sine-wave components
        int numNoise = 3;
        double[] noiseAmp = {3.2, 1.6, 0.7};
        double[] noiseFreq = {0.014, 0.032, 0.068};
        double[] phasesX = new double[numNoise];
        double[] phasesY = new double[numNoise];
        for (int i = 0; i < numNoise; i++) {
            phasesX[i] = faker.random().nextDouble(0.0, TWO_PI);
            phasesY[i] = faker.random().nextDouble(0.0, TWO_PI);
        }

        // Oval mask (fingerprints are oval)
        double maskA = width * 0.44;
        double maskB = height * 0.46;
        double fadeZone = 0.13;   // fraction of mask radius used for edge fade

        // Minutiae: random ridge endings (+) and bifurcations (−) scattered
        // across the print. Each is a Gaussian phase bump with peak height
        // ridgePeriod/2, which produces a half-period local shift — exactly
        // the topological change a real ending or bifurcation creates.
        int numMinutiae = 18 + faker.random().nextInt(0, 18);
        double minutiaSigma = ridgePeriod * 0.85;
        double twoSigmaMinSq = 2 * minutiaSigma * minutiaSigma;
        double minutiaCutoffSq = 9 * minutiaSigma * minutiaSigma;   // ≈3σ
        double[][] minutiae = new double[numMinutiae][3];
        for (int i = 0; i < numMinutiae; i++) {
            double rx, ry;
            do {
                rx = faker.random().nextDouble(-0.40, 0.40);
                ry = faker.random().nextDouble(-0.42, 0.42);
            } while (rx * rx / 0.16 + ry * ry / 0.18 > 0.85);   // stay within mask
            minutiae[i][0] = width * 0.5 + rx * width;
            minutiae[i][1] = height * 0.5 + ry * height;
            minutiae[i][2] = (faker.random().nextBoolean() ? 1.0 : -1.0) * ridgePeriod * 0.5;
        }

        // Ink tones
        int ridgeGray = faker.random().nextInt(25, 65);
        int valleyGray = faker.random().nextInt(195, 235);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                // Sine-wave displacement gives ridges an organic, non-straight look
                double noiseX = 0, noiseY = 0;
                for (int i = 0; i < numNoise; i++) {
                    double f = noiseFreq[i];
                    noiseX += noiseAmp[i] * Math.sin(x * f + y * f * 0.6 + phasesX[i]);
                    noiseY += noiseAmp[i] * Math.sin(y * f + x * f * 0.6 + phasesY[i]);
                }

                double px = x + noiseX;
                double py = y + noiseY;
                double dx = px - cx;
                double dy = py - cy;
                double r = Math.sqrt(dx * dx + dy * dy);

                // Global ridge value – periodic over ridgePeriod
                double ridgeVal = switch (patternType) {
                    case WHORL -> {
                        // Spiral: angle controls how much the ridges rotate
                        double theta = Math.atan2(dy, dx);
                        yield r + spiralFactor * ridgePeriod * theta / Math.PI;
                    }
                    case LOOP -> {
                        // Pull the effective centre upward near the core to form a loop
                        double pull = loopAmplitude * Math.exp(-r * r / (2 * loopSigma * loopSigma));
                        double loopDy = dy - pull;
                        yield Math.sqrt(dx * dx + loopDy * loopDy);
                    }
                    case ARCH -> {
                        // Ridges arch upward over the core
                        double arch = archAmplitude * Math.exp(-dx * dx / (2 * archSigma * archSigma));
                        yield dy + arch;
                    }
                };

                // Minutiae: localised half-period phase bumps create ridge
                // endings and bifurcations, the small features that give real
                // fingerprints their characteristic broken-up appearance.
                for (double[] m : minutiae) {
                    double dxm = px - m[0];
                    double dym = py - m[1];
                    double rm2 = dxm * dxm + dym * dym;
                    if (rm2 < minutiaCutoffSq) {
                        ridgeVal += m[2] * Math.exp(-rm2 / twoSigmaMinSq);
                    }
                }

                double mod = ((ridgeVal % ridgePeriod) + ridgePeriod) % ridgePeriod;
                boolean isRidge = mod < ridgePeriod * ridgeFraction;

                // Oval mask with smooth edge fade toward white
                double mx = (x - width * 0.5) / maskA;
                double my = (y - height * 0.5) / maskB;
                double maskDist = mx * mx + my * my;

                int pixel;
                if (maskDist >= 1.0) {
                    pixel = 255;
                } else {
                    double fade = Math.min(1.0, (1.0 - maskDist) / fadeZone);
                    int base = isRidge ? ridgeGray : valleyGray;
                    pixel = (int) Math.round(base * fade + 255.0 * (1.0 - fade));
                }

                image.getRaster().setSample(x, y, 0, pixel);
            }
        }

        return image;
    }
}
