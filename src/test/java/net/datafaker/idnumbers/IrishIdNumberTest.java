package net.datafaker.idnumbers;

import net.datafaker.Faker;
import net.datafaker.helpers.IdNumberPatterns;
import net.datafaker.service.FakeValuesService;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Locale;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

public class IrishIdNumberTest {
    private static final Logger LOG = Logger.getLogger(IrishIdNumberTest.class.getName());
    private final IrishIdNumber irishIdNumber = new IrishIdNumber();
    private final Faker faker = new Faker(new Locale("ie", "IE"));

    @RepeatedTest(100)
    void validIrishIdNumber() {
        String actual = irishIdNumber.generateValid(faker);
        LOG.info("PPSN Generated: " + actual);
        assertThat(actual).matches(IdNumberPatterns.IRISH);
        assertThat(irishIdNumber.checkModulo23(actual)).isTrue();
    }
}
