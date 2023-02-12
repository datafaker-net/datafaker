package net.datafaker.providers.sport;

import java.util.List;
import java.util.Collection;

public class ChessTest extends SportFakerTest {

    private final Chess chess = getFaker().chess();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(chess::opening, "chess.openings"),
            TestSpec.of(chess::player, "chess.players"),
            TestSpec.of(chess::title, "chess.titles"),
            TestSpec.of(chess::tournament, "chess.tournaments")
        );
    }
}
