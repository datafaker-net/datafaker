package net.datafaker.providers.base;


import net.datafaker.service.RandomService;

/**
 * A domain name generator.
 *
 * @since 0.9.0
 */
public class Domain extends AbstractProvider<BaseProviders> {

    /**
     * Instantiates a new Domain.
     *
     * @param faker the faker
     */
    protected Domain(BaseProviders faker) {
        super(faker);
    }

    /**
     * First level domain string. Such as example.com
     *
     * @param name the company name
     * @return the
     */
    public String firstLevelDomain(String name) {
        String top = resolve("domain.top");
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
        String top = resolve("domain.top");
        String suffix = resolve("domain.country");
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
        String prefix = resolve("domain.prefix");
        String top = resolve("domain.top");
        String suffix = resolve("domain.country");
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

        String top = resolve("domain.top");

        String result = String.join("",
            name,
            ".",
            top
        );

        if (hasPrefix) {
            String prefix = resolve("domain.prefix");
            result = String.join("",
                prefix,
                ".",
                result
            );
        }

        if (hasSuffix) {
            String suffix = resolve("domain.country");
            result = String.join("",
                result,
                ".",
                suffix
            );
        }

        return result;
    }
}
