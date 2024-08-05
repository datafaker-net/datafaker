package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class FinancialTermsTest extends BaseFakerTest<BaseFaker> {

    private final FinancialTerms financialTerms = faker.financialTerms();

    @EnumSource(FinancialTerms.Category.class)
    @ParameterizedTest
    void category(FinancialTerms.Category category) {
        assertThat(financialTerms.noun(category)).isNotBlank();
        assertThat(financialTerms.verb(category)).isNotBlank();
        assertThat(financialTerms.adjective(category)).isNotBlank();
    }

    @RepeatedTest(10)
    void defaults() {
        assertThat(financialTerms.noun()).isNotBlank();
        assertThat(financialTerms.verb()).isNotBlank();
        assertThat(financialTerms.adjective()).isNotBlank();
    }
}
