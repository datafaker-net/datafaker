package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class ArtistTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Artist artist = faker.artist();
        return Arrays.asList(TestSpec.of(artist::name, "artist.names", "(\\w+ ?){1,2}"));
    }
}
