package net.datafaker;

import net.datafaker.fileformats.Format;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Random;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FakeCollectionTest extends AbstractFakerTest {
    @Test
    public void generateCollection() {
        List<String> names = new FakeCollection.Builder<String>()
            .suppliers(() -> faker.name().firstName(), () -> faker.name().lastName())
            .minLen(3)
            .maxLen(5).build().get();
        assertThat(names.size(), is(lessThanOrEqualTo(5)));
        assertThat(names.size(), is(greaterThanOrEqualTo(3)));
        for (String name : names) {
            assertThat(name, matchesRegularExpression("[a-zA-Z']+"));
        }
    }

    @Test
    public void generateNullCollection() {
        List<String> names = new FakeCollection.Builder<String>()
            .suppliers(() -> faker.name().firstName(), () -> faker.name().lastName())
            .nullRate(1d)
            .minLen(3)
            .maxLen(5).build().get();
        assertThat(names.size(), is(lessThanOrEqualTo(5)));
        assertThat(names.size(), is(greaterThanOrEqualTo(3)));
        for (String name : names) {
            assertNull(name);
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {Long.MIN_VALUE, Integer.MIN_VALUE, -1, -0.3, 2, 3, Integer.MAX_VALUE, Double.MAX_VALUE})
    public void illegalNullRate(double nullRate) {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new FakeCollection.Builder<String>()
                .suppliers(() -> faker.name().firstName(), () -> faker.name().lastName())
                .nullRate(nullRate)
                .minLen(3)
                .maxLen(5).build().get(), "Not thrown for nullRate " + nullRate);
    }

    @Test
    public void generateCollectionWithRepeatableFaker() {
        Faker seededFaker = new Faker(new Random(10L));

        List<String> names = new FakeCollection.Builder<String>()
            .faker(seededFaker)
            .suppliers(() -> seededFaker.name().firstName(), () -> seededFaker.name().lastName())
            .minLen(1)
            .maxLen(20).build().get();

        assertThat(names.size(), is(equalTo(14)));
        assertThat(names.get(0), is(equalTo("Flor")));
        assertThat(names.get(1), is(equalTo("Brian")));
    }

    @Test
    public void generateCollectionWithDifferentObjects() {
        List<Object> objects = new FakeCollection.Builder<>()
            .suppliers(() -> faker.name().firstName(), () -> faker.random().nextInt(100))
            .maxLen(5).build().get();
        assertEquals(5, objects.size());
        for (Object object : objects) {
            assertTrue(object instanceof Integer || object instanceof String);
        }
    }

    @Test
    public void checkWrongArguments() {
        IllegalArgumentException iae = Assertions.assertThrows(IllegalArgumentException.class, () ->
            new FakeCollection.Builder<String>()
                .suppliers(() -> faker.name().firstName())
                .minLen(10)
                .maxLen(5).build().get());
        assertEquals("Max length must be not less than min length and not negative", iae.getMessage());
    }

    private interface Data {
        String name();

        String value();

        String range();

        String unit();
    }

    private static class BloodPressure implements Data {

        @Override
        public String name() {
            return "Mean Blood Pressure";
        }

        @Override
        public String value() {
            return faker.random().nextInt(60, 180) + "";
        }

        @Override
        public String range() {
            return "";
        }

        @Override
        public String unit() {
            return "mm Hg";
        }
    }

    private static class Glucose implements Data {

        @Override
        public String name() {
            return "Glucose";
        }

        @Override
        public String value() {
            return String.format("%.1f", faker.random().nextDouble(3.2, 5.5));
        }

        @Override
        public String range() {
            return "3.2-5.5";
        }

        @Override
        public String unit() {
            return "mmol/L";
        }
    }

    private static class Temperature implements Data {

        @Override
        public String name() {
            return "Temperature";
        }

        @Override
        public String value() {
            return faker.random().nextInt(30, 50) + "";
        }

        @Override
        public String range() {
            return "";
        }

        @Override
        public String unit() {
            return "degrees C";
        }
    }

    @Test
    public void toCsv() {
        String separator = "$$$";
        int limit = 5;
        String csv = Format.toCsv(
                new FakeCollection.Builder<Data>().minLen(limit).maxLen(limit)
                    .suppliers(BloodPressure::new, Glucose::new, Temperature::new)
                    .build())
            .headers(() -> "name", () -> "value", () -> "range", () -> "unit")
            .columns(Data::name, Data::value, Data::range, Data::unit)
            .separator(separator)
            .build().get();

        int numberOfLines = 0;
        int numberOfSeparator = 0;
        for (int i = 0; i < csv.length(); i++) {
            if (csv.regionMatches(i, System.lineSeparator(), 0, System.lineSeparator().length())) {
                numberOfLines++;
            } else if (csv.regionMatches(i, separator, 0, separator.length())) {
                numberOfSeparator++;
            }
        }

        assertEquals(limit + 1, numberOfLines); // limit + 1 line for header
        assertEquals((limit + 1) * (4 - 1), numberOfSeparator); // number of lines * (number of columns - 1)
    }

    @Test
    public void toJson() {
        int limit = 10;
        String json = Format.toJson(
                new FakeCollection.Builder<Data>().minLen(limit).maxLen(limit)
                    .suppliers(BloodPressure::new, Glucose::new, Temperature::new)
                    .build())
            .set("name", Data::name)
            .set("value", Data::value)
            .set("range", Data::range)
            .set("unit", Data::unit)
            .build()
            .generate();

        int numberOfLines = 0;
        for (int i = 0; i < json.length(); i++) {
            if (json.regionMatches(i, "},", 0, "},".length())) {
                numberOfLines++;
            }
        }

        assertEquals(limit - 1, numberOfLines); // limit - 1 since for the last line there is no comma
    }

    @Test
    public void toNestedJson() {
        final int limit = 2;
        final String json =
            Format.toJson(new FakeCollection.Builder<Name>().faker(faker)
                    .suppliers(() -> faker.name())
                    .maxLen(limit)
                    .minLen(limit)
                    .build())
                .set("primaryAddress", Format.toJson()
                    .set("country", () -> faker.address().country())
                    .set("city", () -> faker.address().city())
                    .set("zipcode", () -> faker.address().zipCode())
                    .set("streetAddress", () -> faker.address().streetAddress())
                    .build())
                .set("secondaryAddresses", Format.toJson(new FakeCollection.Builder<Address>().faker(faker)
                        .suppliers(() -> faker.address())
                        .maxLen(1)
                        .minLen(1)
                        .build())
                    .set("country", Address::country)
                    .set("city", Address::city)
                    .set("zipcode", Address::zipCode)
                    .set("streetAddress", Address::streetAddress)
                    .build())
                .set("phones", name -> new FakeCollection.Builder<String>().suppliers(() -> faker.phoneNumber().phoneNumber()).maxLen(3).build().get())
                .build()
                .generate();

        int numberOfLines = 0;
        for (int i = 0; i < json.length(); i++) {
            if (json.regionMatches(i, System.lineSeparator(), 0, System.lineSeparator().length())) {
                numberOfLines++;
            }
        }
        assertEquals(limit - 1, numberOfLines); // limit - 1 since for the last line there is no comma
    }

    @RepeatedTest(10)
    public void singletonTest() {
        int limit = 10;
        assertNotNull(new FakeCollection.Builder<Data>().minLen(limit).maxLen(limit)
            .suppliers(BloodPressure::new, Glucose::new, Temperature::new)
            .build().singleton());
    }
}
