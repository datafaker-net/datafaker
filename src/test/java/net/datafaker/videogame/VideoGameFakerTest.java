package net.datafaker.videogame;

import net.datafaker.base.BaseFakerTest;

public class VideoGameFakerTest extends BaseFakerTest<VideoGameFaker> {
    @Override
    protected VideoGameFaker getFaker() {
        return new VideoGameFaker();
    }
}
