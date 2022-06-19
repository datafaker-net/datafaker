package net.datafaker;

import net.datafaker.core.Faker;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @since 0.8.0
 */
public class Hashing {
    private final Lorem lorem;

    protected Hashing(Faker faker) {
        this.lorem = faker.getProvider(Lorem.class, () -> new Lorem(faker));
    }

    public String md2() {
        return generateString("MD2");
    }

    public String md5() {
        return generateString("MD5");
    }

    public String sha1() {
        return generateString("SHA-1");
    }

    public String sha384() {
        return generateString("SHA-384");
    }

    public String sha256() {
        return generateString("SHA-256");
    }

    public String sha512() {
        return generateString("SHA-512");
    }

    private String generateString(String algorithm) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            String characters = lorem.characters();
            messageDigest.update(characters.getBytes(StandardCharsets.UTF_8), 0, characters.length());
            return new BigInteger(1, messageDigest.digest()).toString(16);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException(noSuchAlgorithmException);
        }
    }
}
