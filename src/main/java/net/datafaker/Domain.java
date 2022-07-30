package net.datafaker;


import net.datafaker.service.RandomService;

/**
 * A domain name generator.
 *
 * @since 0.9.0
 */
public class Domain extends AbstractProvider {

    /**
     * Instantiates a new Domain.
     *
     * @param faker the faker
     */
    protected Domain(Faker faker) {
        super(faker);
    }

    /**
     * First level domain string. Such as example.com
     *
     * @param name the company name
     * @return the
     */
    public String firstLevelDomain(String name) {
        String top = faker.fakeValuesService().resolve("domain.top", this);
        return String.join("",
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
        String top = faker.fakeValuesService().resolve("domain.top", this);
        String suffix = faker.fakeValuesService().resolve("domain.country", this);
        return String.join("",
            name,
            ".",
            top,
            ".",
            suffix
        );
    }

    /**
     * Full domain string. Such as <a href="www.example.com.uk">www.example.com.uk</a>
     *
     * @param name the company name
     * @return the full domain name
     */
    public String fullDomain(String name) {
        String prefix = faker.fakeValuesService().resolve("domain.prefix", this);
        String top = faker.fakeValuesService().resolve("domain.top", this);
        String suffix = faker.fakeValuesService().resolve("domain.country", this);
        return String.join("",
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

        boolean hasPrefix = random.nextInt(3) == 1;
        boolean hasSuffix = random.nextInt(2) == 1;

        String top = faker.fakeValuesService().resolve("domain.top", this);

        String result = String.join("",
            name,
            ".",
            top
        );

        if (hasPrefix) {
            String prefix = faker.fakeValuesService().resolve("domain.prefix", this);
            result = String.join("",
                prefix,
                ".",
                result
            );
        }

        if (hasSuffix) {
            String suffix = faker.fakeValuesService().resolve("domain.country", this);
            result = String.join("",
                result,
                ".",
                suffix
            );
        }

        return result;
    }
}
