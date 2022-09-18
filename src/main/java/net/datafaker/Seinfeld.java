package net.datafaker;

/**
 * Seinfeld is an American sitcom television series created by Larry David and Jerry Seinfeld.
 *
 * @since 1.4.0
 */
public class Seinfeld extends AbstractProvider<IProviders> {

    protected Seinfeld(BaseFaker faker) {
        super(faker);
    }

    public String character() {
        return resolve("seinfeld.character");
    }

    public String quote() {
        return resolve("seinfeld.quote");
    }

    public String business() {
        return resolve("seinfeld.business");
    }

}
