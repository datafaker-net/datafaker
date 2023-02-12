package net.datafaker.providers.videogame;

import java.util.List;
import java.util.Collection;

class VideoGameTest extends VideoGameFakerTest {

    private final VideoGame videoGame = getFaker().videoGame();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(videoGame::genre, "video_game.genre"),
            TestSpec.of(videoGame::platform, "video_game.platform"),
            TestSpec.of(videoGame::title, "video_game.title")
        );
    }
}
