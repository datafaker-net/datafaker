package net.datafaker.providers.base;

import net.datafaker.Faker;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.Locale;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AddressTest extends BaseFakerTest<BaseFaker> {

    private final char decimalSeparator = new DecimalFormatSymbols(getFaker().getContext().getLocale()).getDecimalSeparator();
    private static final Faker US_FAKER = new Faker(new Locale("en", "US"));
    private static final Faker NL_FAKER = new Faker(new Locale("nl", "NL"));
    private static final Faker BELGIAN_FAKER = new Faker(new Locale("nl", "BE"));
    private static final Faker RU_FAKER = new Faker(new Locale("ru", "RU"));
    private static final Faker AU_FAKER = new Faker(new Locale("en", "AU"));
    private static final Faker IE_FAKER = new Faker(new Locale("en", "IE"));
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

    private final static Function<Locale, String> ESCAPED_DECIMAL_SEPARATOR = t -> "\\" + new DecimalFormatSymbols(t).getDecimalSeparator();

    @ParameterizedTest
    @ValueSource(strings = {"en", "id", "ca", "cs"})
    void testLatinStreetName() {
        final BaseFaker faker = new BaseFaker();
        assertThat(faker.address().streetName()).isNotEmpty().doesNotMatch(CYRILLIC_LETTERS);
    }

    @ParameterizedTest
    @ValueSource(strings = {"be", "bg", "by", "mk", "ru", "ru_MD", "uk"})
    void testCyrillicStreetName(String cyrillicLocale) {
        final BaseFaker localFaker = new BaseFaker(new Locale(cyrillicLocale));
        assertThat(localFaker.address().streetName()).isNotEmpty().matches(CYRILLIC_LETTERS);
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

    @Test
    void usingOnlyCountryCodeWithoutLanguage() {
        assertThat(new BaseFaker(new Locale("xx", "AL")).address().countryCode()).isEqualTo("AL");
        assertThat(new BaseFaker(new Locale("xx", "AM")).address().countryCode()).isEqualTo("AM");
        assertThat(new BaseFaker(new Locale("xx", "BG")).address().countryCode()).isEqualTo("BG");
        assertThat(new BaseFaker(new Locale("xx", "BY")).address().countryCode()).isEqualTo("BY");
        assertThat(new BaseFaker(new Locale("xx", "BY")).address().postcode()).matches("\\d{6}");
        assertThat(new BaseFaker(new Locale("xx", "EE")).address().postcode()).matches("\\d{5}");
    }

    @RepeatedTest(10)
    void testLatitude() {
        String latStr = faker.address().latitude().replace(decimalSeparator, '.');
        assertThat(latStr).is(IS_A_NUMBER);
        Double lat = Double.valueOf(latStr);
        assertThat(lat).isBetween(-90.0, 90.0);
    }

    @RepeatedTest(10)
    void testLongitude() {
        String longStr = faker.address().longitude().replace(decimalSeparator, '.');
        assertThat(longStr).is(IS_A_NUMBER);
        Double lon = Double.valueOf(longStr);
        assertThat(lon).isBetween(-180.0, 180.0);
    }

    @RepeatedTest(10)
    void testLocaleLatitude() {
        BaseFaker engFaker = new BaseFaker(Locale.ENGLISH);
        String engLatStr = engFaker.address().latitude();
        assertThat(engLatStr).matches("-?\\d{1,3}\\.\\d+");
    }

    @RepeatedTest(10)
    void testLocaleLongitude() {
        BaseFaker engFaker = new BaseFaker(Locale.ENGLISH);
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
        long seed = (long) (Long.MAX_VALUE * Math.random());
        BaseFaker firstFaker = new BaseFaker(Locale.FRANCE, new Random(seed));
        BaseFaker secondFaker = new BaseFaker(Locale.FRANCE, new Random(seed));
        for (int i = 0; i < 100; i++) {
            assertThat(firstFaker.address().city()).isEqualTo(secondFaker.address().city());
        }
    }

    @Test
    void testFullAddress() {
        assertThat(faker.address().fullAddress()).isNotEmpty();
    }

    @Test
    void fullAddress_estonia() {
        BaseFaker f = new BaseFaker(new Locale("et", "EE"));
        assertThat(f.address().fullAddress()).isNotEmpty();
    }

    @Test
    void eircode_ireland() {
        BaseFaker f = new BaseFaker(new Locale("en", "IE"));
        assertThat(f.address().eircode()).isNotEmpty();
    }

    @Test
    void testZipCodeByState() {
        final BaseFaker localFaker = new BaseFaker(new Locale("en", "US"));
        assertThat(localFaker.address().zipCodeByState(localFaker.address().stateAbbr())).matches("[0-9]{5}");
    }

    @Test
    void testHungarianZipCodeByState() {
        final BaseFaker localFaker = new BaseFaker(new Locale("hu"));
        assertThat(localFaker.address().zipCodeByState(localFaker.address().stateAbbr())).matches("[0-9]{4}");
    }

    @Test
    void testCountyByZipCode() {
        final BaseFaker localFaker = new BaseFaker(new Locale("en", "US"));
        assertThat(localFaker.address().countyByZipCode("47732")).isNotEmpty();
    }

    Collection<String> argentineZipCodesSource() {
        return Stream.generate(() -> new BaseFaker(Locale.forLanguageTag("es-AR")).address().zipCode())
            .distinct().limit(4025)
            .collect(Collectors.toSet());
    }

    @ParameterizedTest
    @MethodSource("argentineZipCodesSource")
    void testArgentineZipCodes(String zipCode) {
        assertThat(zipCode).matches("^[ABDEFGHJKLMNPQRSTUVWXYZ][0-9]{4}$");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"1", "asd", "qwe", "wrong"})
    void testCountyForWrongZipCode(String zipCode) {
        final BaseFaker localFaker = new BaseFaker(new Locale("en", "US"));
        assertThatThrownBy(() -> localFaker.address().countyByZipCode(zipCode))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("County is not configured for postcode " + zipCode);
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
        final BaseFaker localFaker = new BaseFaker(new Locale("en", "US"));
        assertThat(localFaker.address().zipCode()).hasSize(5);
    }

    @Test
    void testZipPlus4IsTenChars() {
        final BaseFaker localFaker = new BaseFaker(new Locale("en", "US"));
        assertThat(localFaker.address().zipCodePlus4()).hasSize(10);  // includes dash
    }

    @Test
    void testZipPlus4IsNineDigits() {
        final BaseFaker localFaker = new BaseFaker(new Locale("en", "US"));
        final String[] zipCodeParts = localFaker.address().zipCodePlus4().split("-");
        assertThat(zipCodeParts[0]).matches("[0-9]{5}");
        assertThat(zipCodeParts[1]).matches("[0-9]{4}");
    }

    @RepeatedTest(10)
    void testLatLonEnUs() {
        assertThat(US_FAKER.address().latLon())
            .matches(BI_LAT_LON_REGEX.apply(ESCAPED_DECIMAL_SEPARATOR.apply(US_FAKER.getContext().getLocale()), ","));
    }

    @RepeatedTest(10)
    void testLatLonNl() {
        assertThat(NL_FAKER.address().latLon())
            .matches(BI_LAT_LON_REGEX.apply(ESCAPED_DECIMAL_SEPARATOR.apply(NL_FAKER.getContext().getLocale()), ","));
    }

    @RepeatedTest(10)
    void testLonLatEnUs() {
        assertThat(US_FAKER.address().lonLat())
            .matches(BI_LON_LAT_REGEX.apply(ESCAPED_DECIMAL_SEPARATOR.apply(US_FAKER.getContext().getLocale()), ","));
    }

    @RepeatedTest(10)
    void testLonLatNl() {
        assertThat(NL_FAKER.address().lonLat())
            .matches(BI_LON_LAT_REGEX.apply(ESCAPED_DECIMAL_SEPARATOR.apply(NL_FAKER.getContext().getLocale()), ","));
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

    @ParameterizedTest
    @ValueSource(strings = {"bg", "ru", "uk", "by"})
    void cyrillicStreetName(String locale) {
        BaseFaker localFaker = new BaseFaker(new Locale(locale));
        assertThat(localFaker.address().streetName()).isNotEmpty().matches(CYRILLIC_LETTERS);
    }

    @RepeatedTest(10)
    void dutchAddress() {
        assertThat(NL_FAKER.address().stateAbbr()).matches("[A-Z]{2}");
        assertThat(NL_FAKER.address().fullAddress()).matches("[A-Z].+, [0-9]{4} [A-Z]{2}, [A-Z].+");
    }

    @RepeatedTest(10)
    void belgianSAddress() {
        assertThat(BELGIAN_FAKER.address().stateAbbr()).matches("[A-Z]{3}");
        assertThat(BELGIAN_FAKER.address().fullAddress()).matches("[A-Z].+, [0-9]{4}, [A-Z].+");
    }

    @RepeatedTest(10)
    void belgianZipcode() {
        assertThat(Integer.valueOf(BELGIAN_FAKER.address().zipCode())).isBetween(1000, 9992);
    }

    @RepeatedTest(10)
    void australiaAddress() {
        assertThat(AU_FAKER.address().fullAddress()).matches("(Unit|[0-9]).+, [A-Z].+, [A-Z]{2,3} [0-9]{4}");
    }

    @RepeatedTest(10)
    void irishAddress() {
        String regex = "(?:.+, [A-Z].+, Co\\. [A-Z].+)|" +
            "(?:.+, Dublin D[A-Z0-9]{2,3})|" +
            "(?:.+, [A-Z].+, (?:Ireland|Republic of Ireland))";
        assertThat(IE_FAKER.address().fullAddress()).matches(regex);
    }

    @RepeatedTest(10)
    void testCityCnSuffix() {
        assertThat(new Faker(Locale.CHINA).address().citySuffix()).matches("[\\u4e00-\\u9fa5]{1,7}(?:省|自治区)");
    }
}
