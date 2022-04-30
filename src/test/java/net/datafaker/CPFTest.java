package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;

import static net.datafaker.idnumbers.pt.br.IdNumberGeneratorPtBrUtil.isCPFValid;
import static org.assertj.core.api.Assertions.assertThat;


class CPFTest extends AbstractFakerTest {

    /**
     * A valid CPF is either a real number or a generated valid number.
     */
    @RepeatedTest(100)
    void isValidCPF() {
        assertThat(isCPFValid(faker.cpf().valid())).isTrue();
    }

    /**
     * A invalid CPF is that dos not meet the requirements of the algorithm
     */
    @RepeatedTest(100)
    void isInvalidCPF() {
        assertThat(isCPFValid(faker.cpf().invalid())).isFalse();
    }

    /**
     * CPF has a main format. This test validate if the number is on the correct format
     * Eg: 111.111.111-11
     */
    @RepeatedTest(100)
    void formattedCPF() {
        final String cpfExpression = "(^\\d{3}\\x2E\\d{3}\\x2E\\d{3}\\x2D\\d{2}$)";

        assertThat(faker.cpf().valid()).matches(cpfExpression);
        assertThat(faker.cpf().valid(true)).matches(cpfExpression);
        assertThat(faker.cpf().invalid()).matches(cpfExpression);
        assertThat(faker.cpf().invalid(true)).matches(cpfExpression);
    }
}
