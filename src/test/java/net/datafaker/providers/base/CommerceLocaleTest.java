package net.datafaker.providers.base;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CommerceLocaleTest extends BaseFakerLocaleTest {

    private final Commerce commerceUA = fakerUA.commerce();
    private final String[] commerceProductNameUA = commerceUA.productName().split(" ");

    @Override
    protected Stream<Arguments> localeProviderListTest() {
        return Stream.of(
            arguments(TestSpec.of(commerceUA::material, "commerce.product_name.material"), fakerUA),
            arguments(TestSpec.of(() -> commerceProductNameUA[0], "commerce.product_name.adjective"), fakerUA),
            arguments(TestSpec.of(() -> commerceProductNameUA[1], "commerce.product_name.material"), fakerUA),
            arguments(TestSpec.of(() -> commerceProductNameUA[2], "commerce.product_name.product"), fakerUA));
    }
}
