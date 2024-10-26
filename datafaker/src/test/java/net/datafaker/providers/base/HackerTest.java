package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class HackerTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Hacker hacker = faker.hacker();
        return List.of(TestSpec.of(hacker::abbreviation, "hacker.abbreviation", "[A-Z]{2,4}"),
            TestSpec.of(hacker::adjective, "hacker.adjective", "(?:\\w+[- ]?){1,2}"),
            TestSpec.of(hacker::noun, "hacker.noun"),
            TestSpec.of(hacker::verb, "hacker.verb"),
            TestSpec.of(hacker::ingverb, "hacker.ingverb", "\\w+ing(?: \\w+)?"));
    }
}
