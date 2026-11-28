import net.datafaker.Faker;

import java.util.Locale;

public class NativeImageSmokeTest {
    public static void main(String[] args) {
        Faker faker = new Faker(Locale.US);
        assertMatches(faker.expression("#{IdNumber.ssn_valid}"), "[0-9]{3}-[0-9]{2}-[0-9]{4}");
        assertMatches(faker.expression("#{Number.number_between '7','7'}"), "7");
        assertMatches(faker.expression("#{Aviation.airport 'ICAO'}"), "[A-Z]{4}");
        assertMatches(faker.expression("#{bothify '??-##','false'}"), "[a-z]{2}-[0-9]{2}");
        assertMatches(new Faker(Locale.forLanguageTag("vi-VN")).resolve("address.default_country"), "Việt Nam");
        assertMatches(new Faker(Locale.forLanguageTag("es-ES")).idNumber().valid(), "[0-9]{8}[A-Z]");
        assertMatches(faker.phoneNumber().phoneNumberInternational(), "\\+1 .*");
        System.out.println("Datafaker native image checks passed.");
    }

    private static void assertMatches(String value, String pattern) {
        if (!value.matches(pattern)) {
            throw new AssertionError("Expected " + pattern + ", got " + value);
        }
    }
}
