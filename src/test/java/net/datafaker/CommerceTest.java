package net.datafaker;

import org.junit.jupiter.api.Test;

import java.text.DecimalFormatSymbols;

import static org.assertj.core.api.Assertions.assertThat;

class CommerceTest extends AbstractFakerTest {

    private static final char DECIMAL_SEPARATOR = new DecimalFormatSymbols(getFaker().getLocale()).getDecimalSeparator();

    private static final String CAPITALIZED_WORD_REGEX = "[A-Z][a-z]+";

    private static final String PROMOTION_CODE_REGEX = CAPITALIZED_WORD_REGEX + "(-" + CAPITALIZED_WORD_REGEX + ")*";

    @Test
    void testDepartment() {
        assertThat(faker.commerce().department()).matches("(\\w+(, | & )?){1,3}");
    }

    @Test
    void testProductName() {
        assertThat(faker.commerce().productName()).matches("(\\w+ ?){3,4}");
    }

    @Test
    void testMaterial() {
        assertThat(faker.commerce().material()).matches("\\w+");
    }

    @Test
    void testBrand() {
        assertThat(faker.commerce().brand()).matches("\\w+");
    }

    @Test
    void testVendor() {
        assertThat(faker.commerce().vendor()).matches("[A-Za-z'() 0-9-,]+");
    }

    @Test
    void testPrice() {
        assertThat(faker.commerce().price()).matches("\\d{1,3}\\" + DECIMAL_SEPARATOR + "\\d{2}");
    }

    @Test
    void testPriceMinMax() {
        assertThat(faker.commerce().price(100, 1000)).matches("\\d{3,4}\\" + DECIMAL_SEPARATOR + "\\d{2}");
    }

    @Test
    void testPromotionCode() {
        assertThat(faker.commerce().promotionCode()).matches(PROMOTION_CODE_REGEX + PROMOTION_CODE_REGEX + "\\d{6}");
    }

    @Test
    void testPromotionCodeDigits() {
        assertThat(faker.commerce().promotionCode(3)).matches(PROMOTION_CODE_REGEX + PROMOTION_CODE_REGEX + "\\d{3}");
    }
}
