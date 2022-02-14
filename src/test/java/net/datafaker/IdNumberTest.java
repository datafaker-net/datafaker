package net.datafaker;

import org.junit.Test;

import java.util.Locale;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

public class IdNumberTest extends AbstractFakerTest {

    @Test
    public void testValid() {
        assertThat(faker.idNumber().valid(), matchesRegularExpression("[0-8]\\d{2}-\\d{2}-\\d{4}"));
    }

    @Test
    public void testInvalid() {
        assertThat(faker.idNumber().invalid(), matchesRegularExpression("[0-9]\\d{2}-\\d{2}-\\d{4}"));
    }

    @Test
    public void testSsnValid() {
        assertThat(faker.idNumber().ssnValid(), matchesRegularExpression("[0-8]\\d{2}-\\d{2}-\\d{4}"));
    }

    @Test
    public void testValidSwedishSsn() {
        final Faker f = new Faker(new Locale("sv_SE"));
        for (int i = 0; i < 100; i++) {
            assertThat(f.idNumber().validSvSeSsn(), matchesRegularExpression("\\d{6}[-+]\\d{4}"));
        }
    }

    @Test
    public void testInvalidSwedishSsn() {
        final Faker f = new Faker(new Locale("sv_SE"));
        for (int i = 0; i < 100; i++) {
            assertThat(f.idNumber().invalidSvSeSsn(), matchesRegularExpression("\\d{6}[-+]\\d{4}"));
        }
    }

    @Test
    public void testValidEnZaSsn() {
        final Faker f = new Faker(new Locale("en_ZA"));
        for (int i = 0; i < 100; i++) {
            assertThat(f.idNumber().validEnZaSsn(), matchesRegularExpression("[0-9]{10}(0|1)8[0-9]"));
        }
    }

    @Test
    public void testInvalidEnZaSsn() {
        final Faker f = new Faker(new Locale("en_ZA"));
        for (int i = 0; i < 100; i++) {
            assertThat(f.idNumber().inValidEnZaSsn(), matchesRegularExpression("[0-9]{10}(0|1)8[0-9]"));
        }
    }
}
