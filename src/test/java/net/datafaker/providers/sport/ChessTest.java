package net.datafaker.providers.sport;

import java.util.Arrays;
import java.util.Collection;

public class ChessTest extends SportFakerTest {

    private final Chess chess = getFaker().chess();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(chess::opening, "chess.openings"),
            TestSpec.of(chess::player, "chess.players"),
            TestSpec.of(chess::title, "chess.titles"),
            TestSpec.of(chess::tournament, "chess.tournaments")
        );
    }
}
