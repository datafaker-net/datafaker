package net.datafaker.providers.videogame;

import net.datafaker.providers.base.ProviderListTest;

public abstract class VideoGameFakerTest extends ProviderListTest<VideoGameFaker> {

    protected final VideoGameFaker faker = new VideoGameFaker();

    @Override
    protected final VideoGameFaker getFaker() {
        return faker;
    }
}
