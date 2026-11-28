package net.datafaker.providers.base;

import net.datafaker.Faker;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Stream;

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

    @Nested
    class NameInVietnameseTest {
        private static final Locale VIETNAMESE = Locale.forLanguageTag("vi");

        private final Faker faker = new Faker(VIETNAMESE);
        private final Name name = faker.name();

        private final List<String> familyNames = list("name.last_name");
        private final List<String> maleGivenNames = list("name.male_first_name");
        private final List<String> femaleGivenNames = list("name.female_first_name");
        private final List<String> givenNames = distinctNames(maleGivenNames, femaleGivenNames);
        private final List<String> maleMiddleNames = list("name.male_middle_name");
        private final List<String> femaleMiddleNames = list("name.female_middle_name");

        private List<String> list(String key) {
            return faker.fakeValuesService().fetchObject(key, faker.getContext());
        }

        private static List<String> distinctNames(List<String> first, List<String> second) {
            return Stream.concat(first.stream(), second.stream()).distinct().toList();
        }

        @Test
        void testFamilyAndGivenNamesAreInTheRightLists() {
            assertThat(familyNames).contains("Nguyễn", "Trần", "Lê").doesNotContain("Thị", "Văn");
            assertThat(maleMiddleNames).contains("Văn").doesNotContain("Nguyễn");
            assertThat(femaleMiddleNames).contains("Thị").doesNotContain("Nguyễn");
            assertThat(givenNames).contains("An", "Mai").doesNotContain("Nguyễn", "Thị", "Văn");
        }

        @RepeatedTest(10)
        void testLastNameIsFamilyName() {
            assertThat(name.lastName()).isIn(familyNames);
        }

        @RepeatedTest(10)
        void testFirstNameIsGivenName() {
            assertThat(name.firstName()).isIn(givenNames);
            assertThat(name.maleFirstName()).isIn(maleGivenNames);
            assertThat(name.femaleFirstName()).isIn(femaleGivenNames);
        }

        @Test
        void testUniqueGivenNames() {
            List<String> uniqueNames = Stream.generate(() -> faker.unique().fetchFromYaml("name.first_name"))
                .limit(givenNames.size())
                .toList();

            assertThat(uniqueNames).containsExactlyInAnyOrderElementsOf(givenNames);
        }

        @Test
        void testFullNameUsuallyIncludesAMiddleName() {
            Name seededName = new Faker(VIETNAMESE, new Random(42)).name();
            int samples = 100;
            int withMiddle = 0;
            for (int i = 0; i < samples; i++) {
                String[] parts = seededName.fullName().split(" ");
                assertThat(parts).hasSizeBetween(2, 3);
                assertThat(parts[0]).isIn(familyNames);
                assertThat(parts[parts.length - 1]).isIn(givenNames);
                if (parts.length == 3) {
                    assertMiddleAndGivenNamesMatch(parts[1], parts[2]);
                    withMiddle++;
                }
            }
            assertThat(withMiddle).isGreaterThan(samples / 2).isLessThan(samples);
        }

        @RepeatedTest(10)
        void testNameWithMiddleIsFamilyMiddleGiven() {
            String[] parts = name.nameWithMiddle().split(" ");
            assertThat(parts).hasSize(3);
            assertThat(parts[0]).isIn(familyNames);
            assertMiddleAndGivenNamesMatch(parts[1], parts[2]);
        }

        private void assertMiddleAndGivenNamesMatch(String middleName, String givenName) {
            boolean male = maleMiddleNames.contains(middleName) && maleGivenNames.contains(givenName);
            boolean female = femaleMiddleNames.contains(middleName) && femaleGivenNames.contains(givenName);
            assertThat(male || female)
                .as("middle name '%s' and given name '%s' belong to the same name group", middleName, givenName)
                .isTrue();
        }
    }
}
