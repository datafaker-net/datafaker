package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;
import net.datafaker.providers.base.BaseProviders;

/**
 * @since 2.3.0
 */
public class Boardgame extends AbstractProvider<BaseProviders> {

    protected Boardgame(BaseProviders faker) {
        super(faker);
    }

    public String name() {
        return resolve("boardgame.name");
    }

    public String category() {
        return resolve("boardgame.category");
    }

    public String mechanic() {
        return resolve("boardgame.mechanic");
    }

    public String subdomain() {
        return resolve("boardgame.subdomain");
    }

    public String designer() {
        return resolve("boardgame.designer");
    }

    public String artist() {
        return resolve("boardgame.artist");
    }

    public String publisher() {
        return resolve("boardgame.publisher");
    }

}
