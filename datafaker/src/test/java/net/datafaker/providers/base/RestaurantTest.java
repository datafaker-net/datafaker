package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Collection;

class RestaurantTest extends BaseFakerTest<BaseFaker> {

    private final Restaurant restaurant = faker.restaurant();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(restaurant::nameSuffix, "restaurant.name_suffix"),
                TestSpec.of(restaurant::type, "restaurant.type"),
                TestSpec.of(restaurant::description, "restaurant.description"),
                TestSpec.of(restaurant::review, "restaurant.review"));
    }

    @RepeatedTest(100)
    void namePrefix() {
        assertThat(restaurant.namePrefix())
            .isNotEmpty()
            .doesNotContain("#", "?") // make sure bothify is applied
            .matches("[A-Z0-9].*");   // and that bothify only uses uppercase characters
    }

    @RepeatedTest(100)
    void name() {
        assertThat(restaurant.name())
            .isNotEmpty()
            .doesNotContain("#", "?") // make sure bothify is applied
            .matches("[A-Z0-9].*");   // and that bothify only uses uppercase characters
    }

}
