package com.github.javafaker;


import com.github.javafaker.service.RandomService;

import static org.apache.commons.lang3.StringUtils.join;

/**
 * The type Domain.
 */
public class Domain {
    private final Faker faker;

    /**
     * Instantiates a new Domain.
     *
     * @param faker the faker
     */
    protected Domain(Faker faker) {
        this.faker = faker;
    }

    /**
     * First level domain string. Such as example.com
     *
     * @param name the company name
     * @return the
     */
    public String firstLevelDomain(String name) {
        String top = faker.fakeValuesService().resolve("domain.top", this, faker);
        return join(
                name,
                ".",
                top
        );
    }

    /**
     * Second level domain string. Such as example.com.uk
     *
     * @param name the company name
     * @return the second level domain with company name
     */
    public String secondLevelDomain(String name) {
        String top = faker.fakeValuesService().resolve("domain.top", this, faker);
        String suffix = faker.fakeValuesService().resolve("domain.country", this, faker);
        return join(
                name,
                ".",
                top,
                ".",
                suffix
        );
    }

    /**
     * Full domain string. Such as www.example.com.uk
     *
     * @param name the company name
     * @return the full domain name
     */
    public String fullDomain(String name) {
        String prefix = faker.fakeValuesService().resolve("domain.prefix", this, faker);
        String top = faker.fakeValuesService().resolve("domain.top", this, faker);
        String suffix = faker.fakeValuesService().resolve("domain.country", this, faker);
        return join(
                prefix,
                ".",
                name,
                ".",
                top,
                ".",
                suffix
        );
    }


    /**
     * Return a random valid domain.
     *
     * @param name the company name
     * @return A valid domain
     */
    public String validDomain(String name) {
        final RandomService random = faker.random();

        boolean has_prefix = random.nextInt(3) == 1;
        boolean has_suffix = random.nextInt(2) == 1;

        String top = faker.fakeValuesService().resolve("domain.top", this, faker);

        String result = join(
                name,
                ".",
                top
        );

        if (has_prefix) {
            String prefix = faker.fakeValuesService().resolve("domain.prefix", this, faker);
            result = join(
                    prefix,
                    ".",
                    result
            );
        }

        if (has_suffix) {
            String suffix = faker.fakeValuesService().resolve("domain.country", this, faker);
            result = join(
                    result,
                    ".",
                    suffix
            );
        }

        return result;
    }
}
