package net.datafaker;

import net.datafaker.sport.AbstractSportFakerTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Formula1Test extends AbstractSportFakerTest {

    @Test
    void driver() {
        assertThat(faker.formula1().driver()).matches("[A-Za-zà-úÀ-Ú- ]+");
    }

    @Test
    void team() {
        assertThat(faker.formula1().team()).matches("[A-Za-zà-úÀ-Ú- ]+");
    }

    @Test
    void circuit() {
        assertThat(faker.formula1().circuit()).matches("[A-Za-zà-úÀ-Ú- ]+");
    }

    @Test
    void grandPrix() {
        assertThat(faker.formula1().grandPrix()).matches("[A-Za-zà-úÀ-Ú- ]+");
    }
}
