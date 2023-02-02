package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;

class RestaurantTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(TestSpec.of(() -> faker.restaurant().nameSuffix(), "restaurant.name_suffix"),
                TestSpec.of(() -> faker.restaurant().type(), "restaurant.type"),
                TestSpec.of(() -> faker.restaurant().description(), "restaurant.description"),
                TestSpec.of(() -> faker.restaurant().review(), "restaurant.review"));
    }
    @RepeatedTest(100)
    void namePrefix() {
        assertThat(faker.restaurant().namePrefix())
            .isNotEmpty()
            .doesNotContain("#", "?") // make sure bothify is applied
            .matches("[A-Z0-9].*");   // and that bothify only uses uppercase characters
    }

    @RepeatedTest(100)
    void name() {
        assertThat(faker.restaurant().name())
            .isNotEmpty()
            .doesNotContain("#", "?") // make sure bothify is applied
            .matches("[A-Z0-9].*");   // and that bothify only uses uppercase characters
    }

}
