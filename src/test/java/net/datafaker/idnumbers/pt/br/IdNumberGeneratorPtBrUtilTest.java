package net.datafaker.idnumbers.pt.br;

import org.junit.jupiter.api.Test;

import static net.datafaker.idnumbers.pt.br.IdNumberGeneratorPtBrUtil.isCPFValid;
import static org.assertj.core.api.Assertions.assertThat;

class IdNumberGeneratorPtBrUtilTest {
    @Test
    void samples() {
        assertThat(isCPFValid("529.982.247-25")).isTrue();
        assertThat(isCPFValid("168.995.350-09")).isTrue();
        assertThat(isCPFValid("862.883.667-57")).isTrue();
        assertThat(isCPFValid("746.971.314-01")).isTrue();
    }
}
