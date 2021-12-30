package com.github.javafaker;

import org.junit.Test;

public class DomainTest extends AbstractFakerTest {

    @Test
    public void testFirstLevelDomainNotNull() {
        String ret = faker.domain().firstLevelDomain("example");
        assert (ret != null);
    }

    @Test
    public void testFirstLevelDomain() {
        String[] components = faker.domain().firstLevelDomain("example").split("\\.");
        for (String str : components) {
            assert (str.length() > 0);
        }
    }

    @Test
    public void testSecondLevelDomainNotNull() {
        String ret = faker.domain().secondLevelDomain("example");
        assert (ret != null);
    }

    @Test
    public void testSecondLevelDomain() {
        String[] components = faker.domain().secondLevelDomain("example").split("\\.");
        for (String str : components) {
            assert (str.length() > 0);
        }
    }


    @Test
    public void testFullDomainNotNull() {
        String ret = faker.domain().fullDomain("example");
        assert (ret != null);
    }

    @Test
    public void testFullDomain() {
        String[] components = faker.domain().fullDomain("example").split("\\.");
        for (String str : components) {
            assert (str.length() > 0);
        }
    }

    @Test
    public void testValidDomainNotNull() {
        String ret = faker.domain().validDomain("example");
        assert (ret != null);
    }

    @Test
    public void testValidDomain() {
        String[] components = faker.domain().validDomain("example").split("\\.");
        for (String str : components) {
            assert (str.length() > 0);
        }
    }
}