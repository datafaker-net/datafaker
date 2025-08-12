package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.BaseFakerTest;

public abstract class EntertainmentFakerTest extends BaseFakerTest<EntertainmentFaker> {
    @Override
    protected final EntertainmentFaker getFaker() {
        return new EntertainmentFaker();
    }
}
