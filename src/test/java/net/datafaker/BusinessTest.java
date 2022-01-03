package net.datafaker;

import org.junit.Test;

import static net.datafaker.matchers.IsStringWithContents.isStringWithContents;
import static org.hamcrest.MatcherAssert.assertThat;

public class BusinessTest extends AbstractFakerTest {
    @Test
    public void creditCardNumber() {
        assertThat(faker.business().creditCardNumber(), isStringWithContents());
    }

    @Test
    public void creditCardType() {
        assertThat(faker.business().creditCardType(), isStringWithContents());
    }

    @Test
    public void creditCardExpiry() {
        assertThat(faker.business().creditCardExpiry(), isStringWithContents());
    }

}
