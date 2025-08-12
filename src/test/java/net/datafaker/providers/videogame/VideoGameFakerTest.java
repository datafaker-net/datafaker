package net.datafaker.providers.videogame;

import net.datafaker.providers.base.BaseFakerTest;

public abstract class VideoGameFakerTest extends BaseFakerTest<VideoGameFaker> {
    @Override
    protected final VideoGameFaker getFaker() {
        return new VideoGameFaker();
    }
}
