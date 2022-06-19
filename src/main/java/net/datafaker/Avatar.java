package net.datafaker;

import net.datafaker.core.Faker;

/**
 * @since 0.8.0
 */
public class Avatar {
    private final Faker faker;
    private final String baseUrl;

    protected Avatar(Faker faker) {
        this.faker = faker;
        this.baseUrl = "https://s3.amazonaws.com/uifaces/faces/twitter/";
    }

    public String image() {
        return baseUrl + faker.fakeValuesService().resolve("internet.avatar", this, faker);
    }
}
