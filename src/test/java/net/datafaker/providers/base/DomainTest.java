package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class DomainTest extends BaseFakerTest<BaseFaker> {

    @Test
    void firstLevelDomainNotNull() {
        String ret = faker.domain().firstLevelDomain("example");
        assert (ret != null);
    }

    @Test
    void firstLevelDomain() {
        String[] components = faker.domain().firstLevelDomain("example").split("\\.");
        for (String str : components) {
            assert (str.length() > 0);
        }
    }

    @Test
    void secondLevelDomainNotNull() {
        String ret = faker.domain().secondLevelDomain("example");
        assert (ret != null);
    }

    @Test
    void secondLevelDomain() {
        String[] components = faker.domain().secondLevelDomain("example").split("\\.");
        for (String str : components) {
            assert (str.length() > 0);
        }
    }


    @Test
    void fullDomainNotNull() {
        String ret = faker.domain().fullDomain("example");
        assert (ret != null);
    }

    @Test
    void fullDomain() {
        String[] components = faker.domain().fullDomain("example").split("\\.");
        for (String str : components) {
            assert (str.length() > 0);
        }
    }

    @RepeatedTest(10)
    void validDomainNotNull() {
        String ret = faker.domain().validDomain("example");
        assert (ret != null);
    }

    @Test
    void validDomain() {
        String[] components = faker.domain().validDomain("example").split("\\.");
        for (String str : components) {
            assert (str.length() > 0);
        }
    }
}
