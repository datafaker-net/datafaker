package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class CommerceTest extends BaseFakerTest<BaseFaker> {

    private final char decimalSeparator = new DecimalFormatSymbols(getFaker().getContext().getLocale()).getDecimalSeparator();

    private static final String CAPITALIZED_WORD_REGEX = "[A-Z][a-z]+";

    private static final String PROMOTION_CODE_REGEX = CAPITALIZED_WORD_REGEX + "(-" + CAPITALIZED_WORD_REGEX + ")*";

    private final Commerce commerce = faker.commerce();

    @Test
    void testDepartment() {
        assertThat(commerce.department()).matches("(\\w+(, | & )?){1,3}");
    }

    @Test
    void testProductName() {
        assertThat(commerce.productName()).matches("(\\w+ ?){3,4}");
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(commerce::material, "commerce.product_name.material"),
            TestSpec.of(commerce::brand, "commerce.brand"),
            TestSpec.of(commerce::vendor, "commerce.vendor"));
    }

    @Test
    void testPrice() {
        assertThat(commerce.price()).matches("\\d{1,3}\\" + decimalSeparator + "\\d{2}");
    }

    @Test
    void testPriceMinMax() {
        assertThat(commerce.price(100, 1000)).matches("\\d{3,4}\\" + decimalSeparator + "\\d{2}");
    }

    @Test
    void testPromotionCode() {
        assertThat(commerce.promotionCode()).matches(PROMOTION_CODE_REGEX + PROMOTION_CODE_REGEX + "\\d{6}");
    }

    @Test
    void testPromotionCodeDigits() {
        assertThat(commerce.promotionCode(3)).matches(PROMOTION_CODE_REGEX + PROMOTION_CODE_REGEX + "\\d{3}");
    }
}
