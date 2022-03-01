package net.datafaker;

import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

/**
 * @since 0.8.0
 */
public class Nation {

    private static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;

    private final Faker faker;

    protected Nation(Faker faker) {
        this.faker = faker;
    }

    public String nationality() {
        return faker.fakeValuesService().resolve("nation.nationality", this, faker);
    }

    public String language() {
        return faker.fakeValuesService().resolve("nation.language", this, faker);
    }

    public String capitalCity() {
        return faker.fakeValuesService().resolve("nation.capital_city", this, faker);
    }

    public String flag() {
        @SuppressWarnings("unchecked")
        List<Integer> flagInts = (List<Integer>) faker.fakeValuesService().fetch("nation.flag");

        ByteBuffer byteBuffer = MappedByteBuffer.allocate(flagInts.size());

        for (Integer flagInt : flagInts) {
            byteBuffer.put(flagInt.byteValue());
        }

        return new String(byteBuffer.array(), UTF8_CHARSET);
    }

    public String isoLanguage() {
        String[] isoLangs = Locale.getISOLanguages();
        return isoLangs[faker.random().nextInt(isoLangs.length)];
    }

    public String isoCountry() {
        String[] isoCountries = Locale.getISOCountries();
        return isoCountries[faker.random().nextInt(isoCountries.length)];
    }
}
