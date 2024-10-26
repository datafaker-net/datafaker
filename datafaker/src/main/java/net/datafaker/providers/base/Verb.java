package net.datafaker.providers.base;

/**
 * @since 1.5.0
 */
public class Verb extends AbstractProvider<BaseProviders> {

    protected Verb(BaseProviders faker) {
        super(faker);
    }

    /**
     * This method generates the base form of a random verb.
     *
     * @return a string of base form of a verb.
     */
    public String base() {
        return resolve("verbs.base");
    }

    /**
     * This method generates a random verb in past tense.
     *
     * @return a string of verb in past tense.
     */
    public String past() {
        return resolve("verbs.past");
    }

    /**
     * This method generates a random verb in past participle tense.
     *
     * @return a string of verb in past participle tense.
     */
    public String pastParticiple() {
        return resolve("verbs.past_participle");
    }

    /**
     * This method generates a random verb in simple present tense.
     *
     * @return a string of verb in simple present tense.
     */
    public String simplePresent() {
        return resolve("verbs.simple_present");
    }

    /**
     * This method generates a random verb in -ing form.
     *
     * @return a string of verb in -ing form.
     */
    public String ingForm() {
        return resolve("verbs.ing_form");
    }
}
