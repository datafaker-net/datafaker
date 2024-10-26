package net.datafaker.providers.base;

import org.apache.commons.validator.routines.ISBNValidator;
import org.apache.commons.validator.routines.checkdigit.EAN13CheckDigit;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class CodeTest extends BaseFakerTest<BaseFaker> {

    @RepeatedTest(100)
    void isbn10DefaultIsNoSeparator() {
        final BaseFaker faker = new BaseFaker();
        String isbn10 = faker.code().isbn10();

        final ISBNValidator isbnValidator = ISBNValidator.getInstance(false);
        assertIsValidISBN10(isbn10, isbnValidator);
        assertThat(isbn10).doesNotContain("-");
    }

    @RepeatedTest(100)
    void isbn13DefaultIsNoSeparator() {
        final BaseFaker faker = new BaseFaker();
        String isbn13 = faker.code().isbn13();

        final ISBNValidator isbnValidator = ISBNValidator.getInstance(false);
        assertIsValidISBN13(isbn13, isbnValidator);
        assertThat(isbn13).doesNotContain("-");
    }

    @RepeatedTest(100)
    void testIsbn10() {
        final BaseFaker faker = new BaseFaker();
        final String isbn10NoSep = faker.code().isbn10(false);
        final String isbn10Sep = faker.code().isbn10(true);
        final ISBNValidator isbnValidator = ISBNValidator.getInstance(false);

        assertThat(isbn10NoSep).hasSize(10);
        assertIsValidISBN10(isbn10NoSep, isbnValidator);
        assertThat(isbn10Sep).hasSize(13);
        assertIsValidISBN10(isbn10Sep, isbnValidator);
    }

    @RepeatedTest(100)
    void testIsbn13() {
        final BaseFaker faker = new BaseFaker();
        final String isbn13NoSep = faker.code().isbn13(false);
        final String isbn13Sep = faker.code().isbn13(true);
        final ISBNValidator isbnValidator = ISBNValidator.getInstance(false);

        assertThat(isbn13NoSep).hasSize(13);
        assertIsValidISBN13(isbn13NoSep, isbnValidator);

        assertThat(isbn13Sep).hasSize(17);
        assertIsValidISBN13(isbn13Sep, isbnValidator);
    }

    private void assertIsValidISBN10(String isbn10, ISBNValidator isbnValidator) {
        assertThat(isbnValidator.isValidISBN10(isbn10)).describedAs(isbn10 + " is valid").isTrue();
    }

    private void assertIsValidISBN13(String isbn13, ISBNValidator isbnValidator) {
        assertThat(isbnValidator.isValidISBN13(isbn13)).describedAs(isbn13 + " is valid").isTrue();
    }

    @RepeatedTest(100)
    void testOverrides() {
        BaseFaker faker = new BaseFaker(new Locale("test"));

        final String isbn10Sep = faker.code().isbn10(true);
        final String isbn13Sep = faker.code().isbn13(true);

        assertThat(isbn10Sep).matches("9971-\\d-\\d{4}-(\\d|X)");

        assertThat(isbn13Sep).matches("(333|444)-9971-\\d-\\d{4}-\\d");
    }

    @Test
    void asin() {
        assertThat(faker.code().asin()).matches("B000([A-Z]|\\d){6}");
    }

    @Test
    void imei() {
        String imei = faker.code().imei();

        assertThat(imei).matches("\\A[\\d.:\\-\\s]+\\z");
        assertThat(LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(imei)).isTrue();
    }

    @Test
    void ean8() {
        assertThat(faker.code().ean8()).matches("\\d{8}");
    }

    @Test
    void gtin8() {
        assertThat(faker.code().gtin8()).matches("\\d{8}");
    }

    @Test
    void ean13() {
        String ean13 = faker.code().ean13();
        assertThat(ean13).matches("\\d{13}");
        assertThat(EAN13CheckDigit.EAN13_CHECK_DIGIT.isValid(ean13)).isTrue();
    }

    @Test
    void gtin13() {
        String gtin13 = faker.code().gtin13();
        assertThat(gtin13).matches("\\d{13}");
        assertThat(EAN13CheckDigit.EAN13_CHECK_DIGIT.isValid(gtin13)).isTrue();
    }

    @Test
    void isbnGs1() {
        String isbnGs1 = faker.code().isbnGs1();
        assertThat(isbnGs1).matches("978|979");
    }

    @Test
    void isbnGroup() {
        String isbnGroup = faker.code().isbnGroup();
        assertThat(isbnGroup).matches("[01]");
    }

    @RepeatedTest(100)
    void isbnRegistrant() {
        String isbnRegistrant = faker.code().isbnRegistrant();
        assertThat(isbnRegistrant).matches("[0-9]{1,7}-[0-9]{1,6}");
    }
}
