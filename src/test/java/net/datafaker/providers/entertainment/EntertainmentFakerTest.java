package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.BaseFakerTest;

public class EntertainmentFakerTest extends BaseFakerTest<EntertainmentFaker> {
    @Override
    protected EntertainmentFaker getFaker() {
        return new EntertainmentFaker();
    }
}
