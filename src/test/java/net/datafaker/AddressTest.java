package net.datafaker;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class AddressTest extends AbstractFakerTest {

    private static final char decimalSeparator = new DecimalFormatSymbols(faker.getLocale()).getDecimalSeparator();
    public static final Condition<String> IS_A_NUMBER = new Condition<>(s -> {
        try {
            Double.valueOf(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }, "Is a number");

    @Test
    public void testStreetAddressStartsWithNumber() {
        final String streetAddressNumber = faker.address().streetAddress();
        assertThat(streetAddressNumber).matches("[0-9]+ .+");
    }

    @Test
    public void testStreetAddressIsANumber() {
        final String streetAddressNumber = faker.address().streetAddressNumber();
        assertThat(streetAddressNumber).matches("[0-9]+");
    }

    @RepeatedTest(100)
    public void testLatitude() {
        String latStr = faker.address().latitude().replace(decimalSeparator, '.');
        assertThat(latStr).is(IS_A_NUMBER);
        Double lat = Double.valueOf(latStr);
        assertThat(lat).isGreaterThanOrEqualTo(-90.0);
        assertThat(lat).isLessThanOrEqualTo(90.0);
    }

    @RepeatedTest(100)
    public void testLongitude() {
        String longStr = faker.address().longitude().replace(decimalSeparator, '.');
        assertThat(longStr).is(IS_A_NUMBER);
        Double lon = Double.valueOf(longStr);
        assertThat(lon).isGreaterThanOrEqualTo(-180.0);
        assertThat(lon).isLessThanOrEqualTo(180.0);
    }

    @Test
    public void testLocaleLatitude() {
        Faker engFaker = new Faker(Locale.ENGLISH);
        String engLatStr = engFaker.address().latitude();
        assertThat(engLatStr).matches("-?\\d{1,3}\\.\\d+");

        Faker ruFaker = new Faker(new Locale("ru"));
        String rusLatStr = ruFaker.address().latitude();
        assertThat(rusLatStr).matches("-?\\d{1,3},\\d+");
    }

    @Test
    public void testLocaleLongitude() {
        Faker engFaker = new Faker(Locale.ENGLISH);
        String engLatStr = engFaker.address().longitude();
        assertThat(engLatStr).matches("-?\\d{1,3}\\.\\d+");

        Faker ruFaker = new Faker(new Locale("ru"));
        String rusLatStr = ruFaker.address().longitude();
        assertThat(rusLatStr).matches("-?\\d{1,3},\\d+");
    }

    @Test
    public void testTimeZone() {
        assertThat(faker.address().timeZone()).matches("[A-Za-z_]+/[A-Za-z_]+[/A-Za-z_]*");
    }

    @Test
    public void testState() {
        assertThat(faker.address().state()).matches("[A-Za-z ]+");
    }

    @Test
    public void testCity() {
        assertThat(faker.address().city()).matches("[A-Za-z'() ]+");
    }

    @Test
    public void testCityName() {
        assertThat(faker.address().cityName()).matches("[A-Za-z'() ]+");
    }

    @Test
    public void testCountry() {
        assertThat(faker.address().country()).matches("[A-Za-z\\- &.,'()\\d]+");
    }

    @Test
    public void testCountryCode() {
        assertThat(faker.address().countryCode()).matches("[A-Za-z ]+");
    }

    @Test
    public void testStreetAddressIncludeSecondary() {
        assertThat(faker.address().streetAddress(true)).isNotEmpty();
    }

    @Test
    public void testCityWithLocaleFranceAndSeed() {
        long seed = 1L;
        Faker firstFaker = new Faker(Locale.FRANCE, new Random(seed));
        Faker secondFaker = new Faker(Locale.FRANCE, new Random(seed));
        assertThat(firstFaker.address().city()).isEqualTo(secondFaker.address().city());
    }

    @Test
    public void testFullAddress() {
        assertThat(faker.address().fullAddress()).isNotEmpty();
    }

    @Test
    public void testZipCodeByState() {
        final Faker localFaker = new Faker(new Locale("en-US"));
        assertThat(localFaker.address().zipCodeByState(localFaker.address().stateAbbr())).matches("[0-9]{5}");
    }

    @Test
    public void testHungarianZipCodeByState() {
        final Faker localFaker = new Faker(new Locale("hu"));
        assertThat(localFaker.address().zipCodeByState(localFaker.address().stateAbbr())).matches("[0-9]{4}");
    }

    @Test
    public void testCountyByZipCode() {
        final Faker localFaker = new Faker(new Locale("en-US"));
        assertThat(localFaker.address().countyByZipCode("47732")).isNotEmpty();
    }

    @Test
    public void testStreetPrefix() {
        assertThat(faker.address().streetPrefix()).isNotEmpty();
    }

    @Test
    public void testStreetSuffix() {
        assertThat(faker.address().streetSuffix()).isNotEmpty();
    }

    @Test
    public void testCityPrefix() {
        assertThat(faker.address().cityPrefix()).isNotEmpty();
    }

    @Test
    public void testCitySuffix() {
        assertThat(faker.address().citySuffix()).isNotEmpty();
    }

    @RepeatedTest(10)
    public void testMailbox() {
        assertThat(faker.address().mailBox()).matches("PO Box [0-9]{2,4}");
    }

    @Test
    public void testZipIsFiveChars() {
        final Faker localFaker = new Faker(new Locale("en-us"));
        assertThat(localFaker.address().zipCode().length()).isEqualTo(5);
    }

    @Test
    public void testZipPlus4IsTenChars() {
        final Faker localFaker = new Faker(new Locale("en-us"));
        assertThat(localFaker.address().zipCodePlus4().length()).isEqualTo(10);  // includes dash
    }

    @Test
    public void testZipPlus4IsNineDigits() {
        final Faker localFaker = new Faker(new Locale("en-us"));
        final String[] zipCodeParts = localFaker.address().zipCodePlus4().split("-");
        assertThat(zipCodeParts[0]).matches("[0-9]{5}");
        assertThat(zipCodeParts[1]).matches("[0-9]{4}");
    }

}
