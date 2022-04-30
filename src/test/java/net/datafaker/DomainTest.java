package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class DomainTest extends AbstractFakerTest {

    @Test
    void testFirstLevelDomainNotNull() {
        String ret = faker.domain().firstLevelDomain("example");
        assert (ret != null);
    }

    @Test
    void testFirstLevelDomain() {
        String[] components = faker.domain().firstLevelDomain("example").split("\\.");
        for (String str : components) {
            assert (str.length() > 0);
        }
    }

    @Test
    void testSecondLevelDomainNotNull() {
        String ret = faker.domain().secondLevelDomain("example");
        assert (ret != null);
    }

    @Test
    void testSecondLevelDomain() {
        String[] components = faker.domain().secondLevelDomain("example").split("\\.");
        for (String str : components) {
            assert (str.length() > 0);
        }
    }


    @Test
    void testFullDomainNotNull() {
        String ret = faker.domain().fullDomain("example");
        assert (ret != null);
    }

    @Test
    void testFullDomain() {
        String[] components = faker.domain().fullDomain("example").split("\\.");
        for (String str : components) {
            assert (str.length() > 0);
        }
    }

    @RepeatedTest(10)
    void testValidDomainNotNull() {
        String ret = faker.domain().validDomain("example");
        assert (ret != null);
    }

    @Test
    void testValidDomain() {
        String[] components = faker.domain().validDomain("example").split("\\.");
        for (String str : components) {
            assert (str.length() > 0);
        }
    }
}
