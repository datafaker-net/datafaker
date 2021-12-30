package com.github.javafaker.idnumbers;

import com.github.javafaker.Faker;
import org.junit.Test;

import java.util.Locale;

import static com.github.javafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

public class EsMXIdNumberTest {

    //Test the Valid MX ssn
    @Test
    public void testValidMXSsn() {
        final Faker f = new Faker(new Locale("es-MX"));
        for (int i = 0; i < 100; i++) {
            assertThat(f.idNumber().valid(), matchesRegularExpression("[A-Z][A-Z][A-Z][A-Z]\\d{6}[HM]" +
                    "[A-Z][A-Z][A-Z][A-Z][A-Z][A-Z,0-9]\\d{1}"));
        }
        for (int i = 0; i < 100; i++) {
            assertThat(f.idNumber().invalid(), matchesRegularExpression("[A-Z][A-Z][A-Z][A-Z]\\d{6}[HM]" +
                    "[A-Z][A-Z][A-Z][A-Z][A-Z][A-Z,0-9]\\d{1}"));
        }
    }

    //Test the Invalid MX ssn
    @Test
    public void testInvalidMXSsn() {
        final Faker f = new Faker(new Locale("es-MX"));
        for (int i = 0; i < 100; i++) {
            assertThat(f.idNumber().validEsMXSsn(), matchesRegularExpression("[A-Z][A-Z][A-Z][A-Z]\\d{6}[HM]" +
                    "[A-Z][A-Z][A-Z][A-Z][A-Z][A-Z,0-9]\\d{1}"));
        }
        for (int i = 0; i < 10; i++) {
            assertThat(f.idNumber().invalidEsMXSsn(), matchesRegularExpression("[A-Z][A-Z][A-Z][A-Z]\\d{6}[HM]" +
                    "[A-Z][A-Z][A-Z][A-Z][A-Z][A-Z,0-9]\\d{1}"));
        }
    }
}