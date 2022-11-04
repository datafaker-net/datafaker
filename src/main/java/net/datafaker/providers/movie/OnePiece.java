package net.datafaker.providers.movie;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 1.7.0
 */
public class OnePiece extends AbstractProvider<MovieProviders> {

    protected OnePiece(MovieProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("one_piece.characters");
    }

    public String sea() {
        return resolve("one_piece.seas");
    }

    public String island() {
        return resolve("one_piece.islands");
    }

    public String location() {
        return resolve("one_piece.locations");
    }

    public String quote() {
        return resolve("one_piece.quotes");
    }

    public String akumasNoMi() {
        return resolve("one_piece.akumas_no_mi");
    }

}
