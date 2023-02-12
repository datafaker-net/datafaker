package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Collection;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class HololiveTest extends BaseFakerTest<BaseFaker> {

    private static final BaseFaker JA_FAKER = new BaseFaker(new Locale("ja"));

    @Override
    protected Collection<TestSpec> providerListTest() {
        Hololive hl = faker.hololive();
        return List.of(TestSpec.of(hl::talent, "hololive.talent", "^[A-Za-z '+-]+$"));
    }

    @Test
    void talent_jaLocale() {
        assertThat(JA_FAKER.hololive().talent()).matches("^AZKi|[\\u3040-\\u30FF\\u4E00-\\u9FAF]+$");
    }
}
