package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class MoodTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Mood mood = faker.mood();
        return List.of(TestSpec.of(mood::feeling, "mood.feeling"),
            TestSpec.of(mood::emotion, "mood.emotion"),
            TestSpec.of(mood::tone, "mood.tone"));
    }

}
