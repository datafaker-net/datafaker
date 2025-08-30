package net.datafaker.providers.base;

import net.datafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;

import static net.datafaker.helpers.IdNumberPatterns.BRAZILIAN;
import static net.datafaker.idnumbers.pt.br.IdNumberGeneratorPtBrUtil.isCPFValid;
import static org.assertj.core.api.Assertions.assertThat;


class CPFTest {

    private final Faker faker = new Faker();

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
        assertThat(faker.cpf().valid()).matches(BRAZILIAN);
        assertThat(faker.cpf().valid(true)).matches(BRAZILIAN);
        assertThat(faker.cpf().invalid()).matches(BRAZILIAN);
        assertThat(faker.cpf().invalid(true)).matches(BRAZILIAN);
    }
}
