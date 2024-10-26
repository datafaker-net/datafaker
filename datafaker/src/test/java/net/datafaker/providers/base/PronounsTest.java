package net.datafaker.providers.base;

import java.util.Collection;
import java.util.List;

class PronounsTest extends BaseFakerTest<BaseFaker> {

    private final Pronouns pronouns = faker.pronouns();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(pronouns::subjective, "pronouns.subjective"),
            TestSpec.of(pronouns::objective, "pronouns.objective"),
            TestSpec.of(pronouns::possessive, "pronouns.possessive"),
            TestSpec.of(pronouns::reflexive, "pronouns.reflexive"));
    }
}
