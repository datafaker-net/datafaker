package net.datafaker;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static java.lang.Integer.parseInt;
import static net.datafaker.idnumbers.pt.br.IdNumberGeneratorPtBrUtil.isCNPJValid;
import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CNPJTest extends AbstractFakerTest {

    /**
     * A valid CNPJ is either a real number or a generated valid number.
     */
    @RepeatedTest(1000)
    public void isValidCNPJ() {
        assertTrue(isCNPJValid(faker.cnpj().valid()));
    }

    /**
     * A invalid CNPJ is that does not meet the requirements of the algorithm
     */
    @RepeatedTest(1000)
    public void isInvalidCNPJ() {
        CNPJ cnpj = faker.cnpj();
        assertFalse(isCNPJValid(cnpj.invalid()));
    }

    @Test
    public void valid_multiBranchIsTrue_shouldGenerateCNPJWithBranchNumberGreaterThan0001() {
        String cnpj = faker.cnpj().valid(true, true);
        String branch = cnpj.substring(11,15);

        // branches are allowed to be 0001 even in multibranch mode. In this case,
        // we are giving the system 5 chances to generate something different than 0001.
        for (int i = 0; "0001".equals(branch) && i < 5; i++) {
            cnpj = faker.cnpj().valid(true, true);
            branch = cnpj.substring(11,15);
        }

        assertTrue(parseInt(branch) > 1);
        assertTrue(isCNPJValid(cnpj));
    }

    @Test
    public void invalid_multiBranchIsTrue_shouldGenerateCNPJWithBranchNumberGreaterThan0001() {
        String cnpj = faker.cnpj().invalid(true, true);
        String branch = cnpj.substring(11,15);

        // branches are allowed to be 0001 even in multibranch mode. In this case,
        // we are giving the system 5 chances to generate something different than 0001.
        for (int i = 0; "0001".equals(branch) && i < 5; i++) {
            cnpj = faker.cnpj().valid(true, true);
            branch = cnpj.substring(11,15);
        }

        assertTrue(parseInt(branch) > 1);
        assertFalse(isCNPJValid(cnpj));
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
