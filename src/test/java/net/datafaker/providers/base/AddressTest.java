package net.datafaker.providers.base;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import net.datafaker.Faker;
import net.datafaker.junit.FakerSource;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;

class AddressTest {

    private final Faker   faker   = new Faker();
    private final Address address = faker.address();

    private final char decimalSeparator = new DecimalFormatSymbols(faker.getContext().getLocale()).getDecimalSeparator();

    // Still needed for tests that use locale-specific decimal separators at assertion time
    static final Faker RU_FAKER = new Faker(new Locale("ru", "RU"));
    static final Faker US_FAKER = new Faker(new Locale("en", "US"));
    static final Faker NL_FAKER = new Faker(new Locale("nl", "NL"));

    private static final Pattern CYRILLIC_LETTERS = Pattern.compile(".*[а-яА-Я].*");

    private static final Condition<String> IS_A_NUMBER = new Condition<>(s -> {
        try {
            Double.valueOf(s);
        } catch (NumberFormatException ignore) {
            return false;
        }
        return true;
    }, "Is a number");

    private static final BiFunction<String, String, Pattern> BI_LAT_LON_REGEX =
        (decimalDelimiter, delimiter) ->
            Pattern.compile("-?\\d{1,2}" + decimalDelimiter + "\\d{5,10}" + delimiter + "-?\\d{1,3}" + decimalDelimiter + "\\d{5,10}+");

    private static final BiFunction<String, String, Pattern> BI_LON_LAT_REGEX =
        (decimalDelimiter, delimiter) ->
            Pattern.compile("-?\\d{1,3}" + decimalDelimiter + "\\d{5,10}+" + delimiter + "-?\\d{1,2}" + decimalDelimiter + "\\d{5,10}");

    private static final Function<Locale, String> ESCAPED_DECIMAL_SEPARATOR =
        t -> "\\" + new DecimalFormatSymbols(t).getDecimalSeparator();

    @ParameterizedTest
    @ValueSource(strings = {"en", "id", "ca", "cs"})
    void testLatinStreetName(String locale) {
        assertThat(new BaseFaker(new Locale(locale)).address().streetName())
            .isNotEmpty().doesNotMatch(CYRILLIC_LETTERS);
    }

    @ParameterizedTest
    @ValueSource(strings = {"be", "bg", "by", "mk", "ru", "ru_MD", "uk"})
    void testCyrillicStreetName(String cyrillicLocale) {
        assertThat(new BaseFaker(new Locale(cyrillicLocale)).address().streetName())
            .isNotEmpty().matches(CYRILLIC_LETTERS);
    }

    @ParameterizedTest
    @ValueSource(strings = {"bg", "ru", "uk", "by"})
    void cyrillicStreetName(String locale) {
        assertThat(new BaseFaker(new Locale(locale)).address().streetName())
            .isNotEmpty().matches(CYRILLIC_LETTERS);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "address#latitude", repeat = 10)
    void testLatitude(String latitude) {
        String latStr = latitude.replace(decimalSeparator, '.');
        assertThat(latStr).is(IS_A_NUMBER);
        assertThat(Double.valueOf(latStr)).isBetween(-90.0, 90.0);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "address#longitude", repeat = 10)
    void testLongitude(String longitude) {
        String longStr = longitude.replace(decimalSeparator, '.');
        assertThat(longStr).is(IS_A_NUMBER);
        assertThat(Double.valueOf(longStr)).isBetween(-180.0, 180.0);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "address#latitude", repeat = 10)
    void testLocaleLatitude(String latitude) {
        assertThat(latitude.replace(decimalSeparator, '.')).matches("-?\\d{1,3}\\.\\d+");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "address#longitude", repeat = 10)
    void testLocaleLongitude(String longitude) {
        assertThat(longitude.replace(decimalSeparator, '.')).matches("-?\\d{1,3}\\.\\d+");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "address#latLon", locale = "en-US", repeat = 10)
    void testLatLonEnUs(String latLon) {
        assertThat(latLon).matches(BI_LAT_LON_REGEX.apply(
            ESCAPED_DECIMAL_SEPARATOR.apply(new Locale("en", "US")), ","));
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "address#latLon", locale = "nl-NL", repeat = 10)
    void testLatLonNl(String latLon) {
        assertThat(latLon).matches(BI_LAT_LON_REGEX.apply(
            ESCAPED_DECIMAL_SEPARATOR.apply(new Locale("nl", "NL")), ","));
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "address#lonLat", locale = "en-US", repeat = 10)
    void testLonLatEnUs(String lonLat) {
        assertThat(lonLat).matches(BI_LON_LAT_REGEX.apply(
            ESCAPED_DECIMAL_SEPARATOR.apply(new Locale("en", "US")), ","));
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "address#lonLat", locale = "nl-NL", repeat = 10)
    void testLonLatNl(String lonLat) {
        assertThat(lonLat).matches(BI_LON_LAT_REGEX.apply(
            ESCAPED_DECIMAL_SEPARATOR.apply(new Locale("nl", "NL")), ","));
    }

    @Test
    void testLonLatRU() {
        assertThat(RU_FAKER.address().lonLat(";"))
            .matches(BI_LON_LAT_REGEX.apply(ESCAPED_DECIMAL_SEPARATOR.apply(RU_FAKER.getContext().getLocale()), ";"));
    }

    @Test
    void testLatLonRU() {
        assertThat(RU_FAKER.address().latLon(";"))
            .matches(BI_LAT_LON_REGEX.apply(ESCAPED_DECIMAL_SEPARATOR.apply(RU_FAKER.getContext().getLocale()), ";"));
    }

    @Test
    void testStreetAddressStartsWithNumber() {
        assertThat(address.streetAddress()).matches("[0-9]+ .+");
    }

    @Test
    void testStreetAddressNumberIsANumberGreaterZero() {
        assertThat(address.streetAddressNumber()).matches("[1-9][0-9]*");
    }

    @Test
    void testStreetAddressIncludeSecondary() {
        assertThat(address.streetAddress(true)).isNotEmpty();
    }

    @Test
    void testStreetPrefix() {
        assertThat(address.streetPrefix()).isNotEmpty();
    }

    @Test
    void testStreetSuffix() {
        assertThat(address.streetSuffix()).isNotEmpty();
    }

    @Test
    void testCityPrefix() {
        assertThat(address.cityPrefix()).isNotEmpty();
    }

    @Test
    void testCitySuffix() {
        assertThat(address.citySuffix()).isNotEmpty();
    }

    @Test
    void testCity() {
        assertThat(address.city()).matches("[A-Za-z'() ]+");
    }

    @Test
    void testCityName() {
        assertThat(address.cityName()).matches("[A-Za-z'() ]+");
    }

    @Test
    void testState() {
        assertThat(address.state()).matches("[A-Za-z ]+");
    }

    @Test
    void testCountry() {
        assertThat(address.country()).matches("[A-Za-z\\- &.,'()\\d]+");
    }

    @Test
    void testCountryCode() {
        assertThat(address.countryCode()).matches("[A-Za-z ]+");
    }

    @Test
    void testTimeZone() {
        assertThat(address.timeZone()).matches("[A-Za-z_]+/[A-Za-z_]+[/A-Za-z_]*");
    }

    @Test
    void testFullAddress() {
        assertThat(address.fullAddress()).isNotEmpty();
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "address#mailBox", repeat = 10)
    void testMailbox(String mailBox) {
        assertThat(mailBox).matches("PO Box [0-9]{2,4}");
    }

    @Test
    void testZipIsFiveChars() {
        assertThat(new BaseFaker(new Locale("en", "US")).address().zipCode()).hasSize(5);
    }

    @Test
    void testZipPlus4IsTenChars() {
        assertThat(new BaseFaker(new Locale("en", "US")).address().zipCodePlus4()).hasSize(10);
    }

    @Test
    void testZipPlus4IsNineDigits() {
        String[] parts = new BaseFaker(new Locale("en", "US")).address().zipCodePlus4().split("-");
        assertThat(parts[0]).matches("[0-9]{5}");
        assertThat(parts[1]).matches("[0-9]{4}");
    }

    @Test
    void testZipCodeByState() {
        BaseFaker localFaker = new BaseFaker(new Locale("en", "US"));
        assertThat(localFaker.address().zipCodeByState(localFaker.address().stateAbbr())).matches("[0-9]{5}");
    }

    @Test
    void testHungarianZipCodeByState() {
        BaseFaker localFaker = new BaseFaker(new Locale("hu"));
        assertThat(localFaker.address().zipCodeByState(localFaker.address().stateAbbr())).matches("[0-9]{4}");
    }

    @Test
    void testCountyByZipCode() {
        assertThat(new BaseFaker(new Locale("en", "US")).address().countyByZipCode("47732")).isNotEmpty();
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"1", "asd", "qwe", "wrong"})
    void testCountyForWrongZipCode(String zipCode) {
        assertThatThrownBy(() -> new BaseFaker(new Locale("en", "US")).address().countyByZipCode(zipCode))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("County is not configured for postcode " + zipCode);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "address#zipCode", locale = "es-AR", repeat = 4025, distinct = true)
    void testArgentineZipCodes(String zipCode) {
        assertThat(zipCode).matches("^[ABDEFGHJKLMNPQRSTUVWXYZ][0-9]{4}$");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "address#fullAddress", locale = "nl-NL", repeat = 10)
    void dutchAddress(String fullAddress) {
        assertThat(fullAddress).matches("[A-Z].+, [0-9]{4} [A-Z]{2}, [A-Z].+");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "address#stateAbbr", locale = "nl-BE", repeat = 10)
    void belgianAddressStateAbbr(String stateAbbr) {
        assertThat(stateAbbr).matches("[A-Z]{3}");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "address#fullAddress", locale = "nl-BE", repeat = 10)
    void belgianAddressFullAddress(String fullAddress) {
        assertThat(fullAddress).matches("[A-Z].+, [0-9]{4}, [A-Z].+");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "address#zipCode", locale = "nl-BE", repeat = 10)
    void belgianZipcode(String zipCode) {
        assertThat(Integer.valueOf(zipCode)).isBetween(1000, 9992);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "address#fullAddress", locale = "en-AU", repeat = 10)
    void australiaAddress(String fullAddress) {
        assertThat(fullAddress).matches("(Unit|[0-9]).+, [A-Z].+, [A-Z]{2,3} [0-9]{4}");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "address#fullAddress", locale = "en-IE", repeat = 10)
    void irishAddress(String fullAddress) {
        assertThat(fullAddress).matches(
            "(?:.+, [A-Z].+, Co\\. [A-Z].+)|(?:.+, Dublin D[A-Z0-9]{2,3})|(?:.+, [A-Z].+, (?:Ireland|Republic of Ireland))");
    }

    @Test
    void usingOnlyCountryCodeWithoutLanguage() {
        assertThat(new BaseFaker(new Locale("xx", "AL")).address().countryCode()).isEqualTo("AL");
        assertThat(new BaseFaker(new Locale("xx", "AM")).address().countryCode()).isEqualTo("AM");
        assertThat(new BaseFaker(new Locale("xx", "BG")).address().countryCode()).isEqualTo("BG");
        assertThat(new BaseFaker(new Locale("xx", "BY")).address().countryCode()).isEqualTo("BY");
        assertThat(new BaseFaker(new Locale("xx", "BY")).address().postcode()).matches("\\d{6}");
        assertThat(new BaseFaker(new Locale("xx", "EE")).address().postcode()).matches("\\d{5}");
    }

    @Test
    void testCityWithLocaleFranceAndSeed() {
        long seed = (long) (Long.MAX_VALUE * Math.random());
        BaseFaker firstFaker  = new BaseFaker(Locale.FRANCE, new Random(seed));
        BaseFaker secondFaker = new BaseFaker(Locale.FRANCE, new Random(seed));
        for (int i = 0; i < 100; i++) {
            assertThat(firstFaker.address().city()).isEqualTo(secondFaker.address().city());
        }
    }

    @Test
    void fullAddress_estonia() {
        assertThat(new BaseFaker(new Locale("et", "EE")).address().fullAddress()).isNotEmpty();
    }

    @Test
    void eirCode_ireland() {
        assertThat(new BaseFaker(new Locale("en", "IE")).address().eircode()).isNotEmpty();
    }

    @Test
    void testCityCnSuffix() {
        assertThat(new Faker(Locale.CHINA).address().citySuffix()).matches("[\\u4e00-\\u9fa5]{1,7}(?:省|自治区)");
    }
}

