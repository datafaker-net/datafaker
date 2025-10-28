package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static java.lang.Float.parseFloat;
import static org.assertj.core.api.Assertions.assertThat;

class CommerceTest extends BaseFakerTest {

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

    @Test
    void productNameConsistsOf_adjective_material_and_productType() {
        BaseFaker faker = new BaseFaker(new Locale("test"));
        assertThat(faker.commerce().productName()).isIn(
            "Silver Linings Playbook",
            "Silver Linings Train",
            "Silver Iron Playbook",
            "Silver Iron Train",
            "Silent Linings Playbook",
            "Silent Linings Train",
            "Silent Iron Playbook",
            "Silent Iron Train"
        );
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(commerce::material, "commerce.product_name.material"),
            TestSpec.of(commerce::brand, "commerce.brand"),
            TestSpec.of(commerce::vendor, "commerce.vendor")
        );
    }

    @Test
    void testPrice() {
        String price = commerce.price();
        assertThat(price)
            .as("random price between 0.00 and 100.00")
            .matches("\\d{1,3}\\.\\d{2}");
        assertThat(parseFloat(price)).isBetween(0f, 100f);
    }

    @Test
    void testPriceMinMax() {
        String price = commerce.price(100, 1000);
        assertThat(price)
            .as("random price between 100.00 and 1000.00")
            .matches("\\d{3,4}\\.\\d{2}");
        assertThat(parseFloat(price)).isBetween(100f, 1000f);
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
