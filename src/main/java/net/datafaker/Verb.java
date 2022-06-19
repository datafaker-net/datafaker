package net.datafaker;

import net.datafaker.core.Faker;

public class Verb {

    private final Faker faker;

    protected Verb(Faker faker) {
        this.faker = faker;
    }

    /**
     * This method generates the base form of a random verb.
     *
     * @return a string of base form of a verb.
     */
    public String base() {
        return faker.fakeValuesService().resolve("verbs.base", this, faker);
    }

    /**
     * This method generates a random verb in past tense.
     *
     * @return a string of verb in past tense.
     */
    public String past() {
        return faker.fakeValuesService().resolve("verbs.past", this, faker);
    }

    /**
     * This method generates a random verb in past participle tense.
     *
     * @return a string of verb in past participle tense.
     */
    public String pastParticiple() {
        return faker.fakeValuesService().resolve("verbs.past_participle", this, faker);
    }

    /**
     * This method generates a random verb in simple present tense.
     *
     * @return a string of verb in simple present tense.
     */
    public String simplePresent() {
        return faker.fakeValuesService().resolve("verbs.simple_present", this, faker);
    }

    /**
     * This method generates a random verb in -ing form.
     *
     * @return a string of verb in -ing form.
     */
    public String ingForm() {
        return faker.fakeValuesService().resolve("verbs.ing_form", this, faker);
    }
}
