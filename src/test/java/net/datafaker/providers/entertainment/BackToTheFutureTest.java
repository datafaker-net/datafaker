package net.datafaker.providers.entertainment;

import java.util.Arrays;
import java.util.Collection;

class BackToTheFutureTest extends EntertainmentFakerTest {

    private final BackToTheFuture backToTheFuture = getFaker().backToTheFuture();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(backToTheFuture::character, "back_to_the_future.characters"),
            TestSpec.of(backToTheFuture::date, "back_to_the_future.dates"),
            TestSpec.of(backToTheFuture::quote, "back_to_the_future.quotes"));
    }
}
