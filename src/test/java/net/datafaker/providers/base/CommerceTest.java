package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import java.text.DecimalFormatSymbols;

import static org.assertj.core.api.Assertions.assertThat;

class CommerceTest extends BaseFakerTest<BaseFaker> {

    private final char decimalSeparator = new DecimalFormatSymbols(faker.getContext().getLocale()).getDecimalSeparator();

    private static final String CAPITALIZED_WORD_REGEX = "[A-Z][a-z]+";

    private static final String PROMOTION_CODE_REGEX = CAPITALIZED_WORD_REGEX + "(-" + CAPITALIZED_WORD_REGEX + ")*";

    @Test
    void department() {
        assertThat(faker.commerce().department()).matches("(\\w+(, | & )?){1,3}");
    }

    @Test
    void productName() {
        assertThat(faker.commerce().productName()).matches("(\\w+ ?){3,4}");
    }

    @Test
    void material() {
        assertThat(faker.commerce().material()).matches("\\w+");
    }

    @Test
    void brand() {
        assertThat(faker.commerce().brand()).matches("\\w+");
    }

    @Test
    void vendor() {
        assertThat(faker.commerce().vendor()).matches("[A-Za-z'() 0-9-,]+");
    }

    @Test
    void price() {
        assertThat(faker.commerce().price()).matches("\\d{1,3}\\" + decimalSeparator + "\\d{2}");
    }

    @Test
    void priceMinMax() {
        assertThat(faker.commerce().price(100, 1000)).matches("\\d{3,4}\\" + decimalSeparator + "\\d{2}");
    }

    @Test
    void promotionCode() {
        assertThat(faker.commerce().promotionCode()).matches(PROMOTION_CODE_REGEX + PROMOTION_CODE_REGEX + "\\d{6}");
    }

    @Test
    void promotionCodeDigits() {
        assertThat(faker.commerce().promotionCode(3)).matches(PROMOTION_CODE_REGEX + PROMOTION_CODE_REGEX + "\\d{3}");
    }
}
