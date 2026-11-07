package net.datafaker.providers.base;

import java.util.Collection;
import java.util.List;

class VerbTest extends BaseFakerTest {
    private static final String WORDS = "[\\w-]+";

    @Override
    protected Collection<TestSpec> providerListTest() {
        Verb verb = faker.verb();
        return List.of(
            TestSpec.of(verb::base, "verbs.base", WORDS),
            TestSpec.of(verb::past, "verbs.past", WORDS),
            TestSpec.of(verb::pastParticiple, "verbs.past_participle", WORDS),
            TestSpec.of(verb::simplePresent, "verbs.simple_present", WORDS),
            TestSpec.of(verb::ingForm, "verbs.ing_form", WORDS));
    }
}
