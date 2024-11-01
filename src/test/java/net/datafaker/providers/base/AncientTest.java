package net.datafaker.providers.base;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;

import java.util.List;
import java.util.Collection;
import java.util.Locale;

class AncientTest extends BaseFakerTest<BaseFaker> {

    private static Collection<TestSpec> getProviderListTests(Ancient ancient) {
        return List.of(
            TestSpec.of(ancient::god, "ancient.god"),
            TestSpec.of(ancient::primordial, "ancient.primordial"),
            TestSpec.of(ancient::titan, "ancient.titan"),
            TestSpec.of(ancient::hero, "ancient.hero")
        );
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        Ancient ancient = faker.ancient();
        return getProviderListTests(ancient);
    }

    @Nested
    class AncientInGreekTest extends BaseFakerTest<BaseFaker> {

        @BeforeAll
        void setup() {
            this.setFaker(new BaseFaker(new Locale("el", "GR")));
        }

        @AfterAll
        void reset() {
            this.setFaker(this.getFaker());
        }

        @Override
        protected Collection<TestSpec> providerListTest() {
            Ancient ancient = faker.ancient();
            return getProviderListTests(ancient);
        }
    }
}
