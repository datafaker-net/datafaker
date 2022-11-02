package net.datafaker.providers.movie;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 1.8.0
 */
public class DoctorWho extends AbstractProvider<MovieProviders> {

    protected DoctorWho(MovieProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("dr_who.character");
    }

    public String doctor() {
        return resolve("dr_who.the_doctors");
    }

    public String actor() {
        return resolve("dr_who.actors");
    }

    public String catchPhrase() {
        return resolve("dr_who.catch_phrases");
    }

    public String quote() {
        return resolve("dr_who.quotes");
    }

    public String villain() {
        return resolve("dr_who.villains");
    }

    public String species() {
        return resolve("dr_who.species");
    }

}
