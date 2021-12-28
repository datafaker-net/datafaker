package com.github.javafaker;

import org.junit.Test;

/**
 * The type Domain test.
 * CS304 Issue link: https://github.com/DiUS/java-faker/issues/391
 */
public class DomainTest extends AbstractFakerTest {
    /**
     * CS304 Issue link: https://github.com/DiUS/java-faker/issues/391
     */
    @Test
    public void testFirstLevelDomainNotNull() {
        String ret = faker.domain().firstLevelDomain("example");
        assert (ret != null);
    }

    /**
     * CS304 Issue link: https://github.com/DiUS/java-faker/issues/391
     */
    @Test
    public void testFirstLevelDomain() {
        String[] components = faker.domain().firstLevelDomain("example").split("\\.");
        for (String str : components) {
            assert (str.length() > 0);
        }
    }

    /**
     * CS304 Issue link: https://github.com/DiUS/java-faker/issues/391
     */
    @Test
    public void testSecondLevelDomainNotNull() {
        String ret = faker.domain().secondLevelDomain("example");
        assert (ret != null);
    }

    /**
     * CS304 Issue link: https://github.com/DiUS/java-faker/issues/391
     */
    @Test
    public void testSecondLevelDomain() {
        String[] components = faker.domain().secondLevelDomain("example").split("\\.");
        for (String str : components) {
            assert (str.length() > 0);
        }
    }


    /**
     * CS304 Issue link: https://github.com/DiUS/java-faker/issues/391
     */
    @Test
    public void testFullDomainNotNull() {
        String ret = faker.domain().fullDomain("example");
        assert (ret != null);
    }

    /**
     * CS304 Issue link: https://github.com/DiUS/java-faker/issues/391
     */
    @Test
    public void testFullDomain() {
        String[] components = faker.domain().fullDomain("example").split("\\.");
        for (String str : components) {
            assert (str.length() > 0);
        }
    }

    /**
     * CS304 Issue link: https://github.com/DiUS/java-faker/issues/391
     */
    @Test
    public void testValidDomainNotNull() {
        String ret = faker.domain().validDomain("example");
        assert (ret != null);
    }

    /**
     * CS304 Issue link: https://github.com/DiUS/java-faker/issues/391
     */
    @Test
    public void testValidDomain() {
        String[] components = faker.domain().validDomain("example").split("\\.");
        for (String str : components) {
            assert (str.length() > 0);
        }
    }
}