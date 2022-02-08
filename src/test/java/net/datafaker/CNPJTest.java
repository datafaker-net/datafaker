package net.datafaker;

import net.datafaker.repeating.Repeat;
import org.hamcrest.Matcher;
import org.junit.Test;

import static net.datafaker.idnumbers.pt.br.IdNumberGeneratorPtBrUtil.isCNPJValid;
import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class CNPJTest extends AbstractFakerTest {

    /**
     * A valid CNPJ is either a real number or a generated valid number.
     */
    @Test
    @Repeat(times = 1000)
    public void isValidCNPJ() {
        assertTrue(isCNPJValid(faker.cnpj().valid()));
    }

    /**
     * A invalid CNPJ is that does not meet the requirements of the algorithm
     */
    @Test
    @Repeat(times = 1000)
    public void isInvalidCNPJ() {
        CNPJ cnpj = faker.cnpj();
        assertFalse(isCNPJValid(cnpj.invalid()));
    }

    /**
     * CNPJ has a main format. This test validate if the number is on the correct format
     * Eg: 11.111.111/0001-11
     */
    @Test
    public void formattedCNPJ() {
        final Matcher<String> cnpjMatcher = matchesRegularExpression("(^\\d{2}\\x2E\\d{3}\\x2E\\d{3}\\x2F\\d{4}\\x2D\\d{2}$)");

        assertThat(faker.cnpj().valid(), cnpjMatcher);
        assertThat(faker.cnpj().valid(true), cnpjMatcher);
        assertThat(faker.cnpj().invalid(), cnpjMatcher);
        assertThat(faker.cnpj().invalid(true), cnpjMatcher);
    }

}
