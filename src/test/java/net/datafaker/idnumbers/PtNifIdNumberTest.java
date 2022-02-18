package net.datafaker.idnumbers;

import net.datafaker.AbstractFakerTest;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Locale;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

public class PtNifIdNumberTest extends AbstractFakerTest {

    private Faker ptFaker;

    @BeforeEach
    public void before() {
        ptFaker = new Faker(new Locale("pt"));
    }

    @RepeatedTest(100)
    public void testInValid() {
        PtNifIdNumber idNumber = new PtNifIdNumber();
        assertThat(idNumber.getInvalid(ptFaker), matchesRegularExpression("[0-9]{9,10}"));
    }

    @RepeatedTest(100)
    public void testValid() {
        PtNifIdNumber idNumber = new PtNifIdNumber();
        assertThat(idNumber.getValid(ptFaker), matchesRegularExpression("[0-9]{9,10}"));
    }

    @RepeatedTest(100)
    public void testValidWithFaker() {
        assertThat(ptFaker.idNumber().valid(), matchesRegularExpression("[0-9]{9,10}"));
    }

}
