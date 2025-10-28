package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

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
        String name = commerce.productName();
        String[] commerceProductName = name.split(" ");

        if (commerceProductName.length == 3) {
            testProviderList(TestSpec.of(() -> commerceProductName[0], "commerce.product_name.adjective"));
            testProviderList(TestSpec.of(() -> commerceProductName[1], "commerce.product_name.material"));
            testProviderList(TestSpec.of(() -> commerceProductName[2], "commerce.product_name.product"));
        } else {
            List<String> adjectives = faker.fakeValuesService().fetchObject("commerce.product_name.adjective", faker.getContext());
            List<String> materials = faker.fakeValuesService().fetchObject("commerce.product_name.material", faker.getContext());
            List<String> products = faker.fakeValuesService().fetchObject("commerce.product_name.product", faker.getContext());

            // Step 1: find which adjective the name starts with
            String adjective = adjectives.stream()
                .filter(name::startsWith)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Adjective not found in: " + name));

            // Step 2: remove it and trim remaining
            String remaining = name.substring(adjective.length()).trim();

            // Step 3: find which material the remainder starts with
            String material = materials.stream()
                .filter(remaining::startsWith)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Material not found in: " + name));

            // Step 4: remaining should now be the product
            String product = remaining.substring(material.length()).trim();

            // Step 5: verify product is from correct list
            assertThat(products).contains(product);

            // Step 6: verify the full composition
            assertThat(name).isEqualTo(String.join(" ", adjective, material, product));
        }
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(commerce::material, "commerce.product_name.material"),
            TestSpec.of(commerce::brand, "commerce.brand"),
            TestSpec.of(commerce::vendor, "commerce.vendor"));
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
