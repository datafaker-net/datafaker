package net.datafaker.providers.base;

import static org.assertj.core.api.Assertions.assertThat;

import net.datafaker.Faker;
import net.datafaker.junit.FakerSource;
import org.apache.commons.validator.routines.ISBNValidator;
import org.apache.commons.validator.routines.checkdigit.EAN13CheckDigit;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.Locale;

class CodeTest {

    private static final ISBNValidator ISBN_VALIDATOR = ISBNValidator.getInstance(false);

    private final Code code = new Faker().code();

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "code#isbn10", repeat = 100)
    void isbn10DefaultIsNoSeparator(String isbn10) {
        assertIsValidISBN10(isbn10);
        assertThat(isbn10).doesNotContain("-");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "code#isbn10(boolean)", params = "false", repeat = 100)
    void isbn10WithoutSeparator(String isbn10) {
        assertThat(isbn10).hasSize(10);
        assertIsValidISBN10(isbn10);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "code#isbn10(boolean)", params = "true", repeat = 100)
    void isbn10WithSeparator(String isbn10) {
        assertThat(isbn10).hasSize(13);
        assertIsValidISBN10(isbn10);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "code#isbn13", repeat = 100)
    void isbn13DefaultIsNoSeparator(String isbn13) {
        assertIsValidISBN13(isbn13);
        assertThat(isbn13).doesNotContain("-");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "code#isbn13(boolean)", params = "false", repeat = 100)
    void isbn13WithoutSeparator(String isbn13) {
        assertThat(isbn13).hasSize(13);
        assertIsValidISBN13(isbn13);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "code#isbn13(boolean)", params = "true", repeat = 100)
    void isbn13WithSeparator(String isbn13) {
        assertThat(isbn13).hasSize(17);
        assertIsValidISBN13(isbn13);
    }

    @RepeatedTest(100)
    void testOverrides() {
        // Requires a specific Locale("test") instance – not expressible via @FakerSource locale
        BaseFaker localFaker = new BaseFaker(new Locale("test"));
        assertThat(localFaker.code().isbn10(true)).matches("9971-\\d-\\d{4}-(\\d|X)");
        assertThat(localFaker.code().isbn13(true)).matches("(333|444)-9971-\\d-\\d{4}-\\d");
    }

    @Test
    void asin() {
        assertThat(code.asin()).matches("B000([A-Z]|\\d){6}");
    }

    @Test
    void imei() {
        String imei = code.imei();
        assertThat(imei).matches("\\A[\\d.:\\-\\s]+\\z");
        assertThat(LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(imei)).isTrue();
    }

    @Test
    void ean8() {
        assertThat(code.ean8()).matches("\\d{8}");
    }

    @Test
    void gtin8() {
        assertThat(code.gtin8()).matches("\\d{8}");
    }

    @Test
    void ean13() {
        String ean13 = code.ean13();
        assertThat(ean13).matches("\\d{13}");
        assertThat(EAN13CheckDigit.EAN13_CHECK_DIGIT.isValid(ean13)).isTrue();
    }

    @Test
    void gtin13() {
        String gtin13 = code.gtin13();
        assertThat(gtin13).matches("\\d{13}");
        assertThat(EAN13CheckDigit.EAN13_CHECK_DIGIT.isValid(gtin13)).isTrue();
    }

    @Test
    void isbnGs1() {
        assertThat(code.isbnGs1()).matches("978|979");
    }

    @Test
    void isbnGroup() {
        assertThat(code.isbnGroup()).matches("[01]");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "code#isbnRegistrant", repeat = 100)
    void isbnRegistrant(String isbnRegistrant) {
        assertThat(isbnRegistrant).matches("[0-9]{1,7}-[0-9]{1,6}");
    }

    private void assertIsValidISBN10(String isbn10) {
        assertThat(ISBN_VALIDATOR.isValidISBN10(isbn10))
            .describedAs("%s should be a valid ISBN-10", isbn10)
            .isTrue();
    }

    private void assertIsValidISBN13(String isbn13) {
        assertThat(ISBN_VALIDATOR.isValidISBN13(isbn13))
            .describedAs("%s should be a valid ISBN-13", isbn13)
            .isTrue();
    }
}

