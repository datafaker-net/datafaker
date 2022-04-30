package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

class NameTest extends AbstractFakerTest {

    @Spy
    private Faker mockedFaker;

    @Test
    void testName() {
        assertThat(faker.name().name()).matches("([\\w']+\\.?( )?){2,3}");
    }

    @Test
    void testNameWithMiddle() {
        assertThat(faker.name().nameWithMiddle()).matches("([\\w']+\\.?( )?){3,4}");
    }

    @Test
    void testNameWithMiddleDoesNotHaveRepeatedName() {
        int theSameNameCnt = 0;
        int total = 100;
        for (int i = 0; i < total; i++) {
            String nameWithMiddle = faker.name().nameWithMiddle();
            String[] splitNames = nameWithMiddle.split(" ");
            if (splitNames[0].equals(splitNames[1])) {
                theSameNameCnt++;
            }
        }
        assertThat(theSameNameCnt).isLessThan(total / 10);
    }

    @Test
    void testFullName() {
        assertThat(faker.name().fullName()).matches("([\\w']+\\.?( )?){2,4}");
    }

    @Test
    void testFullNameArabic() {
        Faker localFaker = new Faker(new Locale("ar"));

        for (int i = 0; i < 25; i++) {
            assertThat(localFaker.name().fullName()).matches("^[\\u0600-\\u06FF\\u0750-\\u077F ]+$");
        }
    }

    @Test
    void testFirstName() {
        assertThat(faker.name().firstName()).matches("\\w+");
    }

    @Test
    void testLastName() {
        assertThat(faker.name().lastName()).matches("[A-Za-z']+");
    }

    @Test
    void testPrefix() {
        assertThat(faker.name().prefix()).matches("\\w+\\.?");
    }

    @Test
    void testSuffix() {
        assertThat(faker.name().suffix()).matches("\\w+\\.?");
    }

    @Test
    void testTitle() {
        assertThat(faker.name().title()).matches("(\\w+\\.?( )?){3}");
    }

    @RepeatedTest(100)
    void testUsername() {
        assertThat(faker.name().username()).matches("^(\\w+)\\.(\\w+)$");
    }

    @Test
    void testUsernameWithSpaces() {
        final Name name = Mockito.spy(new Name(mockedFaker));
        doReturn("Compound Name").when(name).firstName();
        doReturn(name).when(mockedFaker).name();
        assertThat(mockedFaker.name().username()).matches("^(\\w+)\\.(\\w+)$");
    }

    @Test
    void testBloodGroup() {
        assertThat(faker.name().bloodGroup()).matches("(A|B|AB|O)[+-]");
    }

}
