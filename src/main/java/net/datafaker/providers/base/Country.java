package net.datafaker.providers.base;

import java.util.Locale;

/**
 * Provides methods for generating country-related data such as names, codes, capitals, and flags.
 * <p>
 * This provider is backed by the data defined in {@code country.yml}.
 * <p>
 * Flag images are served by <a href="https://flagcdn.com">flagcdn.com</a>
 * (see <a href="https://flagpedia.net/download/api">Flagpedia API</a>).
 *
 * @since 0.8.0
 */
public class Country extends AbstractProvider<BaseProviders> {

    private static final String FLAG_BASE_URL = "https://flagcdn.com/";

    protected Country(BaseProviders faker) {
        super(faker);
    }

    /**
     * Generates a <em>PNG</em> flag URL for a random country at size ({@code w580}).
     *
     * @return a flag image URL string
     */
    public String flag() {
        return flag("w580", ImageFormat.PNG);
    }

    /**
     * Generates a flag URL for a random country.
     * <p>
     * For {@link ImageFormat#SVG} the {@code size} argument is ignored.<br>
     * See the <a href="https://flagpedia.net/download/api">Flagpedia API</a> for valid size strings.
     *
     * @param size   a {@code flagcdn.com} size string, e.g. {@code "w320"}, {@code "h80"}, {@code "32x24"}
     * @param format the desired image format
     * @return a flag image URL string
     */
    public String flag(String size, ImageFormat format) {
        String url = FLAG_BASE_URL;
        if (ImageFormat.SVG != format) {
            url += size + '/';
        }
        return url + countryCode2() + '.' + format.getExtension();
    }

    /**
     * Returns a random two-letter ISO 3166-1 alpha-2 country code in <strong>lowercase</strong>.
     *
     * @return a 2-letter lowercase country code
     */
    public String countryCode2() {
        return resolve("country.code2");
    }

    /**
     * Returns a random three-letter ISO 3166-1 alpha-3 country code in <strong>lowercase</strong>.
     *
     * @return a 3-letter lowercase country code
     */
    public String countryCode3() {
        return resolve("country.code3");
    }

    /**
     * Returns a random capital city.
     *
     * @return a capital city name
     */
    public String capital() {
        return resolve("country.capital");
    }

    /**
     * @see Money#currency()
     * @return a random detailed ISO 4217 currency display name
     */
    public String currency() {
        return faker.money().currency();
    }

    /**
     * @see Money#currencyCode()
     * @return an ISO 4217 currency code
     */
    public String currencyCode() {
        return faker.money().currencyCode();
    }

    /**
     * Returns a random country name.
     *
     * @return a country name
     */
    public String name() {
        return resolve("country.name");
    }

    /**
     * Image format for flag URLs.
     */
    public enum ImageFormat {
        JPG,
        PNG,
        SVG,
        WEBP;

        private final String extension;

        ImageFormat() {
            this.extension = name().toLowerCase(Locale.ROOT);
        }

        public String getExtension() {
            return extension;
        }
    }

}
