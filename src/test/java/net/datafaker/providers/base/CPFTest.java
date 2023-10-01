package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;

import java.util.regex.Pattern;

import static net.datafaker.idnumbers.pt.br.IdNumberGeneratorPtBrUtil.isCPFValid;
import static org.assertj.core.api.Assertions.assertThat;


class CPFTest extends BaseFakerTest<BaseFaker> {

    public static final Pattern CPF_EXPRESSION = Pattern.compile("(^\\d{3}\\x2E\\d{3}\\x2E\\d{3}\\x2D\\d{2}$)");

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
        assertThat(faker.cpf().valid()).matches(CPF_EXPRESSION);
        assertThat(faker.cpf().valid(true)).matches(CPF_EXPRESSION);
        assertThat(faker.cpf().invalid()).matches(CPF_EXPRESSION);
        assertThat(faker.cpf().invalid(true)).matches(CPF_EXPRESSION);
    }
}
