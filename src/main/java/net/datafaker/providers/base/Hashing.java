package net.datafaker.providers.base;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @since 0.8.0
 */
public class Hashing extends AbstractProvider<BaseProviders> {

    protected Hashing(BaseProviders faker) {
        super(faker);
    }

    public String md2() {
        return generateString("MD2", "%032x");
    }

    public String md5() {
        return generateString("MD5", "%032x");
    }

    public String sha1() {
        return generateString("SHA-1", "%040x");
    }

    public String sha384() {
        return generateString("SHA-384", "%096x");
    }

    public String sha256() {
        return generateString("SHA-256", "%064x");
    }

    public String sha512() {
        return generateString("SHA-512", "%0128x");
    }

    private String generateString(String algorithm, String format) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            String characters = faker.lorem().characters();
            messageDigest.update(characters.getBytes(StandardCharsets.UTF_8), 0, characters.length());
            return format.formatted(new BigInteger(1, messageDigest.digest()));
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException(noSuchAlgorithmException);
        }
    }
}
