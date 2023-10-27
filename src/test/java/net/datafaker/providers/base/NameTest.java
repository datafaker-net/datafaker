package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class NameTest extends BaseFakerTest<BaseFaker> {

    private final Name name = faker.name();

    @Test
    void testName() {
        assertThat(name.name()).matches("([\\w']+\\.?( )?){2,4}");
    }

    @Test
    void testNameWithMiddle() {
        assertThat(name.nameWithMiddle()).matches("([\\w']+\\.?( )?){3,}");
    }

    @Test
    void testNameWithMiddleDoesNotHaveRepeatedName() {
        int theSameNameCnt = 0;
        int total = 100;
        for (int i = 0; i < total; i++) {
            String nameWithMiddle = name.nameWithMiddle();
            String[] splitNames = nameWithMiddle.split(" ");
            if (splitNames[0].equals(splitNames[1])) {
                theSameNameCnt++;
            }
        }
        assertThat(theSameNameCnt).isLessThan(total / 10);
    }

    @Test
    void testFullName() {
        assertThat(name.fullName()).matches("([\\w']+\\.?( )?){2,4}");
    }

    @Test
    void testFullNameArabic() {
        BaseFaker localFaker = new BaseFaker(new Locale("ar"));

        for (int i = 0; i < 25; i++) {
            assertThat(localFaker.name().fullName()).matches("^[\\u0600-\\u06FF\\u0750-\\u077F ]+$");
        }
    }

    @Test
    void testFirstName() {
        assertThat(name.firstName()).matches("\\w+");
    }

    @RepeatedTest(10)
    void testFemaleFirstName() {
        assertThat(name.femaleFirstName()).matches("\\w+");
    }

    @RepeatedTest(10)
    void testMaleFirstName() {
        assertThat(name.malefirstName()).matches("\\w+");
    }

    @Test
    void testLastName() {
        assertThat(name.lastName()).matches("[A-Za-z']+");
    }

    @Test
    void testPrefix() {
        assertThat(name.prefix()).matches("\\w+\\.?");
    }

    @Test
    void testSuffix() {
        assertThat(name.suffix()).matches("\\w+\\.?");
    }

    @Test
    void testTitle() {
        assertThat(name.title()).matches("(\\w+\\.?( )?){3}");
    }

}
