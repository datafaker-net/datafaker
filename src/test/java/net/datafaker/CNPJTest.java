package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static java.lang.Integer.parseInt;
import static net.datafaker.idnumbers.pt.br.IdNumberGeneratorPtBrUtil.isCNPJValid;
import static org.assertj.core.api.Assertions.assertThat;

class CNPJTest extends AbstractFakerTest {

    /**
     * A valid CNPJ is either a real number or a generated valid number.
     */
    @RepeatedTest(1000)
    void isValidCNPJ() {
        CNPJ cnpj = faker.cnpj();
        assertThat(isCNPJValid(cnpj.valid())).describedAs("Current value " + cnpj).isTrue();
    }

    /**
     * A invalid CNPJ is that does not meet the requirements of the algorithm
     */
    @RepeatedTest(1000)
    void isInvalidCNPJ() {
        CNPJ cnpj = faker.cnpj();
        assertThat(isCNPJValid(cnpj.invalid())).describedAs("Current value " + cnpj).isFalse();
    }

    @Test
    void valid_multiBranchIsTrue_shouldGenerateCNPJWithBranchNumberGreaterThan0001() {
        String cnpj = faker.cnpj().valid(true, true);
        String branch = cnpj.substring(11, 15);

        // branches are allowed to be 0001 even in multibranch mode. In this case,
        // we are giving the system 5 chances to generate something different than 0001.
        for (int i = 0; "0001".equals(branch) && i < 5; i++) {
            cnpj = faker.cnpj().valid(true, true);
            branch = cnpj.substring(11, 15);
        }

        assertThat(parseInt(branch)).isGreaterThan(1);
        assertThat(isCNPJValid(cnpj)).describedAs("Current value " + cnpj).isTrue();
    }

    @Test
    void invalid_multiBranchIsTrue_shouldGenerateCNPJWithBranchNumberGreaterThan0001() {
        String cnpj = faker.cnpj().invalid(true, true);
        String branch = cnpj.substring(11, 15);

        // branches are allowed to be 0001 even in multibranch mode. In this case,
        // we are giving the system 5 chances to generate something different than 0001.
        for (int i = 0; "0001".equals(branch) && i < 5 || "0000".equals(branch); i++) {
            cnpj = faker.cnpj().valid(true, true);
            branch = cnpj.substring(11, 15);
        }

        assertThat(parseInt(branch)).describedAs("Branch " + branch).isGreaterThan(1);
        assertThat(isCNPJValid(cnpj)).describedAs("Current value " + cnpj).isFalse();
    }

    /**
     * CNPJ has a main format. This test validate if the number is on the correct format
     * Eg: 11.111.111/0001-11
     */
    @Test
    void formattedCNPJ() {
        final String cnpjExpression = "(^\\d{2}\\x2E\\d{3}\\x2E\\d{3}\\x2F\\d{4}\\x2D\\d{2}$)";

        assertThat(faker.cnpj().valid()).matches(cnpjExpression);
        assertThat(faker.cnpj().valid(true)).matches(cnpjExpression);
        assertThat(faker.cnpj().invalid()).matches(cnpjExpression);
        assertThat(faker.cnpj().invalid(true)).matches(cnpjExpression);
    }

}
