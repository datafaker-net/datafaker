package net.datafaker.idnumbers;

import net.datafaker.Faker;
import net.datafaker.providers.base.IdNumber.GenderRequest;
import net.datafaker.providers.base.IdNumber.IdNumberRequest;
import net.datafaker.providers.base.PersonIdNumber;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static net.datafaker.providers.base.PersonIdNumber.Gender.FEMALE;
import static net.datafaker.providers.base.PersonIdNumber.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;

class MoldovanIdNumberTest {
    private final MoldovanIdNumber generator = new MoldovanIdNumber();
    private final Faker faker = new Faker();

    @RepeatedTest(100)
    void valid() {
        String pin = generator.generateValid(faker);
        assertThat(pin.length())
            .as(() -> "Presumably valid PIN: '%s'".formatted(pin))
            .isEqualTo(13);
        assertThat(pin)
            .as(() -> "Presumably valid PIN: '%s'".formatted(pin))
            .matches("\\d{13}");
    }

    @RepeatedTest(10)
    void validUsesRequestedGender() {
        PersonIdNumber female = generator.generateValid(faker, new IdNumberRequest(18, 65, GenderRequest.FEMALE));
        assertThat(female.gender()).isEqualTo(FEMALE);

        PersonIdNumber male = generator.generateValid(faker, new IdNumberRequest(18, 65, GenderRequest.MALE));
        assertThat(male.gender()).isEqualTo(MALE);
    }

    @Test
    void checksum() {
        assertThat(generator.checksum("293270095431")).isEqualTo('9');
        assertThat(generator.checksum("201403700084")).isEqualTo('3');
        assertThat(generator.checksum("099220624701")).isEqualTo('8');
        assertThat(generator.checksum("200504212980")).isEqualTo('9');
        assertThat(generator.checksum("200504401269")).isEqualTo('3');
        assertThat(generator.checksum("200201100696")).isEqualTo('1');
        assertThat(generator.checksum("200403612722")).isEqualTo('9');
    }
}
