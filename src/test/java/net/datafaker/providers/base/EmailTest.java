package net.datafaker.providers.base;

import org.apache.commons.validator.routines.EmailValidator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class EmailTest extends BaseFakerTest<BaseFaker>{
    @Test
    void testEmailAddress() {
        String emailAddress = faker.email().emailAddress();
        assertThat(EmailValidator.getInstance().isValid(emailAddress)).isTrue();
    }
    @Test
    void testEmailAddressWithLocalPartParameter() {
        String emailAddress = faker.email().emailAddress("john");
        assertThat(emailAddress).startsWith("john@");
        assertThat(EmailValidator.getInstance().isValid(emailAddress)).isTrue();
    }

    @Test
    void testSafeEmailAddress() {
        List<String> emails = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String emailAddress = faker.email().safeEmailAddress();
            assertThat(EmailValidator.getInstance().isValid(emailAddress)).isTrue();
            emails.add(emailAddress);
        }
        final String safeDomain = faker.email().resolve("internet.safe_email");

        assertThat(emails.stream().filter(t -> t.endsWith("@" + safeDomain)).collect(Collectors.toList()))
            .isNotEmpty();
    }

    @Test
    void testSafeEmailAddressWithLocalPartParameter() {
        List<String> emails = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String emailAddress = faker.email().safeEmailAddress("john");
            assertThat(emailAddress).startsWith("john@");
            assertThat(EmailValidator.getInstance().isValid(emailAddress)).isTrue();
            emails.add(emailAddress);
        }
        final String safeDomain = faker.email().resolve("internet.safe_email");

        assertThat(emails.stream().filter(t -> t.endsWith("@" + safeDomain)).collect(Collectors.toList()))
            .isNotEmpty();
    }

    @Test
    void testEmailAddressDoesNotIncludeAccentsInTheLocalPart() {
        String emailAddress = faker.email().emailAddress("áéíóú");
        assertThat(emailAddress).startsWith("aeiou@");
    }

    @Test
    void testSafeEmailAddressDoesNotIncludeAccentsInTheLocalPart() {
        String emailAddress = faker.email().safeEmailAddress("áéíóú");
        assertThat(emailAddress).startsWith("aeiou@");
    }

}
