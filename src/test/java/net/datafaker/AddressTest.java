package net.datafaker;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AddressTest extends AbstractFakerTest {

    private static final char DECIMAL_SEPARATOR = new DecimalFormatSymbols(getFaker().getLocale()).getDecimalSeparator();
    private static final Faker US_FAKER = new Faker(new Locale("en-us"));
    private static final Faker NL_FAKER = new Faker(new Locale("nl-nl"));

    public static final Condition<String> IS_A_NUMBER = new Condition<>(s -> {
        try {
            Double.valueOf(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }, "Is a number");

    public static final Pattern LAT_LON_REGEX = Pattern.compile("-?\\d{1,2}.\\d{5,10},-?\\d{1,3}.\\d{5,10}+");
    public static final Pattern LON_LAT_REGEX = Pattern.compile("-?\\d{1,3}.\\d{5,10}+,-?\\d{1,2}.\\d{5,10}");

    @Test
    void testStreetName() {
        final Faker faker = new Faker();
        assertThat(faker.address().streetName()).isNotEmpty();
    }

    @Test
    void testBulgarianStreetName() {
        final Faker localFaker = new Faker(new Locale("bg"));
        assertThat(localFaker.address().streetName()).isNotEmpty();
    }

    @Test
    void testStreetAddressStartsWithNumber() {
        final String streetAddressNumber = faker.address().streetAddress();
        assertThat(streetAddressNumber).matches("[0-9]+ .+");
    }

    @Test
    void testStreetAddressIsANumber() {
        final String streetAddressNumber = faker.address().streetAddressNumber();
        assertThat(streetAddressNumber).matches("[0-9]+");
    }

    @RepeatedTest(100)
    void testLatitude() {
        String latStr = faker.address().latitude().replace(DECIMAL_SEPARATOR, '.');
        assertThat(latStr).is(IS_A_NUMBER);
        Double lat = Double.valueOf(latStr);
        assertThat(lat).isBetween(-90.0, 90.0);
    }

    @RepeatedTest(100)
    void testLongitude() {
        String longStr = faker.address().longitude().replace(DECIMAL_SEPARATOR, '.');
        assertThat(longStr).is(IS_A_NUMBER);
        Double lon = Double.valueOf(longStr);
        assertThat(lon).isBetween(-180.0, 180.0);
    }

    @RepeatedTest(10)
    void testLocaleLatitude() {
        Faker engFaker = new Faker(Locale.ENGLISH);
        String engLatStr = engFaker.address().latitude();
        assertThat(engLatStr).matches("-?\\d{1,3}\\.\\d+");
    }

    @RepeatedTest(10)
    void testLocaleLongitude() {
        Faker engFaker = new Faker(Locale.ENGLISH);
        String engLatStr = engFaker.address().longitude();
        assertThat(engLatStr).matches("-?\\d{1,3}\\.\\d+");
    }

    @Test
    void testTimeZone() {
        assertThat(faker.address().timeZone()).matches("[A-Za-z_]+/[A-Za-z_]+[/A-Za-z_]*");
    }

    @Test
    void testState() {
        assertThat(faker.address().state()).matches("[A-Za-z ]+");
    }

    @Test
    void testCity() {
        assertThat(faker.address().city()).matches("[A-Za-z'() ]+");
    }

    @Test
    void testCityName() {
        assertThat(faker.address().cityName()).matches("[A-Za-z'() ]+");
    }

    @Test
    void testCountry() {
        assertThat(faker.address().country()).matches("[A-Za-z\\- &.,'()\\d]+");
    }

    @Test
    void testCountryCode() {
        assertThat(faker.address().countryCode()).matches("[A-Za-z ]+");
    }

    @Test
    void testStreetAddressIncludeSecondary() {
        assertThat(faker.address().streetAddress(true)).isNotEmpty();
    }

    @Test
    void testCityWithLocaleFranceAndSeed() {
        long seed = 1L;
        Faker firstFaker = new Faker(Locale.FRANCE, new Random(seed));
        Faker secondFaker = new Faker(Locale.FRANCE, new Random(seed));
        assertThat(firstFaker.address().city()).isEqualTo(secondFaker.address().city());
    }

    @Test
    void testFullAddress() {
        assertThat(faker.address().fullAddress()).isNotEmpty();
    }

    @Test
    void testZipCodeByState() {
        final Faker localFaker = new Faker(new Locale("en-US"));
        assertThat(localFaker.address().zipCodeByState(localFaker.address().stateAbbr())).matches("[0-9]{5}");
    }

    @Test
    void testHungarianZipCodeByState() {
        final Faker localFaker = new Faker(new Locale("hu"));
        assertThat(localFaker.address().zipCodeByState(localFaker.address().stateAbbr())).matches("[0-9]{4}");
    }

    @Test
    void testCountyByZipCode() {
        final Faker localFaker = new Faker(new Locale("en-US"));
        assertThat(localFaker.address().countyByZipCode("47732")).isNotEmpty();
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"1", "asd", "qwe", "wrong"})
    void testCountyForWrongZipCode(String zipCode) {
        final Faker localFaker = new Faker(new Locale("en-US"));
        assertThatThrownBy(() -> localFaker.address().countyByZipCode(zipCode))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("County are not configured for postcode " + zipCode);
    }

    @Test
    void testStreetPrefix() {
        assertThat(faker.address().streetPrefix()).isNotEmpty();
    }

    @Test
    void testStreetSuffix() {
        assertThat(faker.address().streetSuffix()).isNotEmpty();
    }

    @Test
    void testCityPrefix() {
        assertThat(faker.address().cityPrefix()).isNotEmpty();
    }

    @Test
    void testCitySuffix() {
        assertThat(faker.address().citySuffix()).isNotEmpty();
    }

    @RepeatedTest(10)
    void testMailbox() {
        assertThat(faker.address().mailBox()).matches("PO Box [0-9]{2,4}");
    }

    @Test
    void testZipIsFiveChars() {
        final Faker localFaker = new Faker(new Locale("en-us"));
        assertThat(localFaker.address().zipCode()).hasSize(5);
    }

    @Test
    void testZipPlus4IsTenChars() {
        final Faker localFaker = new Faker(new Locale("en-us"));
        assertThat(localFaker.address().zipCodePlus4()).hasSize(10);  // includes dash
    }

    @Test
    void testZipPlus4IsNineDigits() {
        final Faker localFaker = new Faker(new Locale("en-us"));
        final String[] zipCodeParts = localFaker.address().zipCodePlus4().split("-");
        assertThat(zipCodeParts[0]).matches("[0-9]{5}");
        assertThat(zipCodeParts[1]).matches("[0-9]{4}");
    }

    @RepeatedTest(100)
    void testLatLonEnUs() {
        assertThat(US_FAKER.address().latLon()).matches(LAT_LON_REGEX);
    }

    @RepeatedTest(100)
    void testLatLonNl() {
        assertThat(NL_FAKER.address().latLon()).matches(LAT_LON_REGEX);
    }

    @RepeatedTest(100)
    void testLonLatEnUs() {
        assertThat(US_FAKER.address().lonLat()).matches(LON_LAT_REGEX);
    }

    @RepeatedTest(100)
    void testLonLatNl() {
        assertThat(NL_FAKER.address().lonLat()).matches(LON_LAT_REGEX);
    }

}
