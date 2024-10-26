package net.datafaker.providers.videogame;

import net.datafaker.providers.base.BaseFakerTest;

public class VideoGameFakerTest extends BaseFakerTest<VideoGameFaker> {
    @Override
    protected VideoGameFaker getFaker() {
        return new VideoGameFaker();
    }
}
