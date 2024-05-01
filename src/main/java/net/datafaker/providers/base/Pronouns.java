package net.datafaker.providers.base;

/**
 * This class generates gender neutral pronouns
 * @since 2.2.0
 */
public class Pronouns extends AbstractProvider<BaseProviders> {

    protected Pronouns(BaseProviders faker) {
        super(faker);
    }

    /**
     * Generates a gender neutral subjective pronoun
     * @return a gender neutral subjective pronoun
     */
    public String subjective() {
        return resolve("pronouns.subjective");
    }

    /**
     * Generates a gender neutral objective pronoun
     * @return a gender neutral objective pronoun
     */
    public String objective() {
        return resolve("pronouns.objective");
    }

    /**
     * Generates a gender neutral possessive pronoun
     * @return a gender neutral possessive pronoun
     */
    public String possessive() {
        return resolve("pronouns.possessive");
    }

    /**
     * Generates a gender neutral reflexive pronoun
     * @return a gender neutral reflexive pronoun
     */
    public String reflexive() {
        return resolve("pronouns.reflexive");
    }
}
