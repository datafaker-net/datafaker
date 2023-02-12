package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

public class OnePieceTest extends EntertainmentFakerTest {

    private final OnePiece onePiece = getFaker().onePiece();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(onePiece::akumasNoMi, "one_piece.akumas_no_mi"),
            TestSpec.of(onePiece::character, "one_piece.characters"),
            TestSpec.of(onePiece::island, "one_piece.islands"),
            TestSpec.of(onePiece::location, "one_piece.locations"),
            TestSpec.of(onePiece::quote, "one_piece.quotes"),
            TestSpec.of(onePiece::sea, "one_piece.seas")
        );
    }
}
