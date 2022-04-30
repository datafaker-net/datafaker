package net.datafaker.idnumbers;

import net.datafaker.AbstractFakerTest;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class PtNifIdNumberTest extends AbstractFakerTest {

    private Faker ptFaker;

    @BeforeEach
    public void before() {
        ptFaker = new Faker(new Locale("pt"));
    }

    @RepeatedTest(100)
    void testInValid() {
        PtNifIdNumber idNumber = new PtNifIdNumber();
        assertThat(idNumber.getInvalid(ptFaker)).matches("[0-9]{9,10}");
    }

    @RepeatedTest(100)
    void testValid() {
        PtNifIdNumber idNumber = new PtNifIdNumber();
        assertThat(idNumber.getValid(ptFaker)).matches("[0-9]{9,10}");
    }

    @RepeatedTest(100)
    void testValidWithFaker() {
        assertThat(ptFaker.idNumber().valid()).matches("[0-9]{9,10}");
    }

}
