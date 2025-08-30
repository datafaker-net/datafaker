package net.datafaker.providers.base;

import net.datafaker.Faker;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class NameTest {
    private final Faker faker = new Faker();
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
        assertThat(name.maleFirstName()).matches("\\w+");
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

    @Nested
    class NameInGreekTest {

        private final Faker faker = new Faker(new Locale("el", "GR"));
        private final Name name = faker.name();

        @RepeatedTest(10)
        void testName() {
            assertThat(name.fullName()).matches("(\\p{L}+\\.?( )?){2,3}");
        }

        @Test
        void testFullName() {
            testName();
        }

        @Test
        void testFirstName() {
            assertThat(name.firstName()).matches("\\p{L}+");
        }

        @RepeatedTest(10)
        void testFemaleFirstName() {
            assertThat(name.femaleFirstName()).matches("\\p{L}+");
        }

        @RepeatedTest(10)
        void testMaleFirstName() {
            assertThat(name.maleFirstName()).matches("\\p{L}+");
        }

        @Test
        void testPrefix() {
            assertThat(name.prefix()).matches("\\p{L}+\\.?");
        }
    }
}
