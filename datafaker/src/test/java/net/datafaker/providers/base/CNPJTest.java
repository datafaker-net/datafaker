package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static net.datafaker.idnumbers.pt.br.IdNumberGeneratorPtBrUtil.isCNPJValid;
import static org.assertj.core.api.Assertions.assertThat;

class CNPJTest extends BaseFakerTest<BaseFaker> {

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
        final CNPJ cnpj1 = faker.cnpj();
        String cnpj = cnpj1.valid(true, true);
        String branch = cnpj.substring(11, 15);

        // branches are allowed to be 0001 even in multibranch mode. In this case,
        // we are giving the system 5 chances to generate something different than 0001.
        for (int i = 0; "0001".equals(branch) && i < 5; i++) {
            cnpj = cnpj1.valid(true, true);
            branch = cnpj.substring(11, 15);
        }

        assertThat(parseInt(branch)).isGreaterThan(1);
        assertThat(isCNPJValid(cnpj)).describedAs("Current value " + cnpj).isTrue();
    }

    @RepeatedTest(1000)
    void invalid_multiBranchIsTrue_shouldGenerateCNPJWithBranchNumberGreaterThan0001() {
        final CNPJ cnpj1 = faker.cnpj();
        String cnpj = cnpj1.invalid(true, true);
        String branch = cnpj.substring(11, 15);

        // branches are allowed to be 0001 even in multibranch mode. In this case,
        // we are giving the system 5 chances to generate something different than 0001.
        for (int i = 0; "0001".equals(branch) && i < 5 || "0000".equals(branch); i++) {
            cnpj = cnpj1.invalid(true, true);
            branch = cnpj.substring(11, 15);
        }

        assertThat(parseInt(branch)).describedAs("Branch " + branch).isGreaterThan(1);
        assertThat(isCNPJValid(cnpj)).describedAs("Current value " + cnpj).isFalse();
    }

    @Test
    void bug() {
        isCNPJValid("57.615.644/1633-29");
    }


    /**
     * CNPJ has a main format. This test validate if the number is on the correct format
     * Eg: 11.111.111/0001-11
     */
    @Test
    void formattedCNPJ() {
        final Pattern cnpjExpression = Pattern.compile("(^\\d{2}\\x2E\\d{3}\\x2E\\d{3}\\x2F\\d{4}\\x2D\\d{2}$)");

        final CNPJ cnpj = faker.cnpj();
        assertThat(cnpj.valid()).matches(cnpjExpression);
        assertThat(cnpj.valid(true)).matches(cnpjExpression);
        assertThat(cnpj.invalid()).matches(cnpjExpression);
        assertThat(cnpj.invalid(true)).matches(cnpjExpression);
    }

}
