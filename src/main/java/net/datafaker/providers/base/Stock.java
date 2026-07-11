package net.datafaker.providers.base;

import net.datafaker.internal.helper.LuhnCheckDigit;

import java.util.Locale;

/**
 * @since 0.8.0
 */
public class Stock extends AbstractProvider<BaseProviders> {

    protected Stock(BaseProviders faker) {
        super(faker);
    }

    public String nsdqSymbol() {
        return resolve("stock.symbol_nsdq");
    }

    public String nyseSymbol() {
        return resolve("stock.symbol_nyse");
    }

    public String nseSymbol() {
        return resolve("stock.symbol_nse");
    }

    public String lseSymbol() {
        return resolve("stock.symbol_lse");
    }

    public String exchanges() {
        return resolve("stock.exchanges");
    }

    /**
     * Generates a random International Securities Identification Number (ISIN) compliant with ISO 6166.
     * <p>
     * The ISIN consists of a 2-letter country code, a 9-character alphanumeric security identifier,
     * and a single check digit calculated using the Luhn algorithm via {@link LuhnCheckDigit}.
     *
     * @return a valid-formatted ISIN
     * @since 3.0.0
     */
    public String isin() {
        String countryCode = faker.country().countryCode2().toUpperCase(Locale.ROOT);
        String nsin = faker.regexify("[0-9A-Z]{9}");
        String baseIsin = countryCode + nsin;

        int baseIsinLen = baseIsin.length();
        StringBuilder digits = new StringBuilder(baseIsinLen * 2);
        for (int i = 0; i < baseIsinLen; i++) {
            char c = baseIsin.charAt(i);
            if (Character.isLetter(c)) {
                digits.append(c - 'A' + 10);
            } else {
                digits.append(c - '0');
            }
        }

        return baseIsin + LuhnCheckDigit.calculate(digits);
    }

}
