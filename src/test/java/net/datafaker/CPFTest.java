package net.datafaker;

import net.datafaker.repeating.Repeat;
import org.hamcrest.Matcher;
import org.junit.Test;

import static net.datafaker.idnumbers.pt.br.IdNumberGeneratorPtBrUtil.isCPFValid;
import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class CPFTest extends AbstractFakerTest {

    /**
     * A valid CPF is either a real number or a generated valid number.
     */
    @Test
    @Repeat(times = 100)
    public void isValidCPF() {
        assertTrue(isCPFValid(faker.cpf().valid()));
    }

    /**
     * A invalid CPF is that dos not meet the requirements of the algorithm
     */
    @Test
    @Repeat(times = 100)
    public void isInvalidCPF() {
        assertFalse(isCPFValid(faker.cpf().invalid()));
    }

    /**
     * CPF has a main format. This test validate if the number is on the correct format
     * Eg: 111.111.111-11
     */
    @Test
    @Repeat(times = 100)
    public void formattedCPF() {
        final Matcher<String> cpfMatcher = matchesRegularExpression("(^\\d{3}\\x2E\\d{3}\\x2E\\d{3}\\x2D\\d{2}$)");

        assertThat(faker.cpf().valid(), cpfMatcher);
        assertThat(faker.cpf().valid(true), cpfMatcher);
        assertThat(faker.cpf().invalid(), cpfMatcher);
        assertThat(faker.cpf().invalid(true), cpfMatcher);
    }
}
