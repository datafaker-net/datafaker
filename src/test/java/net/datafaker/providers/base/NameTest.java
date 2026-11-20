package net.datafaker.providers.base;

import net.datafaker.Faker;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;
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

        private static final List<String> FAMILY_NAMES = List.of(
            "Nguyễn", "Trần", "Lê", "Phạm", "Hoàng", "Huỳnh", "Phan", "Vũ", "Võ", "Đặng",
            "Bùi", "Đỗ", "Hồ", "Ngô", "Dương", "Lý", "Đào", "Đoàn", "Vương", "Trịnh",
            "Đinh", "Lâm", "Phùng", "Mai", "Tô", "Trương", "Hà", "Tăng", "Cao", "Lương",
            "Châu", "Tạ", "Quách", "Lưu", "Thái", "Triệu", "Kiều", "Từ", "La", "Nghiêm");

        private static final List<String> MIDDLE_NAMES = List.of(
            "Văn", "Đức", "Minh", "Hữu", "Quốc", "Công", "Thanh", "Xuân", "Đình", "Quang",
            "Gia", "Mạnh", "Bá", "Duy", "Ngọc", "Tiến", "Trọng",
            "Thị", "Thu", "Kim", "Bảo", "Diệu", "Khánh", "Thùy", "Hoài", "Mỹ", "Hồng", "Phương", "Bích");

        private static final List<String> MALE_GIVEN_NAMES = List.of(
            "An", "Anh", "Bảo", "Bình", "Cường", "Dũng", "Đạt", "Đông", "Hải", "Hiếu",
            "Hòa", "Hùng", "Huy", "Khang", "Khánh", "Khoa", "Kiên", "Lâm", "Long", "Lộc",
            "Nam", "Nghĩa", "Nhân", "Phát", "Phong", "Phúc", "Quân", "Sơn", "Tài", "Thắng",
            "Thành", "Thịnh", "Toàn", "Trí", "Trung", "Tuấn", "Tùng", "Việt", "Vinh", "Vũ");

        private static final List<String> FEMALE_GIVEN_NAMES = List.of(
            "An", "Anh", "Châu", "Chi", "Dung", "Duyên", "Giang", "Hà", "Hạnh", "Hằng",
            "Hiền", "Hoa", "Hương", "Huyền", "Lan", "Liên", "Linh", "Loan", "Ly", "Mai",
            "My", "Nga", "Ngân", "Nhàn", "Nhung", "Oanh", "Quyên", "Quỳnh", "Tâm", "Thảo",
            "Thắm", "Thủy", "Trang", "Trinh", "Uyên", "Vân", "Vy", "Yến");

        private static final List<String> GIVEN_NAMES = Stream
            .concat(MALE_GIVEN_NAMES.stream(), FEMALE_GIVEN_NAMES.stream())
            .toList();

        private final Name name = new Faker(new Locale("vi")).name();

        @RepeatedTest(10)
        void testLastNameIsFamilyName() {
            assertThat(name.lastName()).isIn(FAMILY_NAMES);
        }

        @RepeatedTest(10)
        void testFirstNameIsGivenName() {
            assertThat(name.firstName()).isIn(GIVEN_NAMES);
            assertThat(name.maleFirstName()).isIn(MALE_GIVEN_NAMES);
            assertThat(name.femaleFirstName()).isIn(FEMALE_GIVEN_NAMES);
        }

        @RepeatedTest(10)
        void testFullNameIsFamilyNameThenGivenName() {
            String[] parts = name.fullName().split(" ");
            assertThat(parts).hasSizeBetween(2, 3);
            assertThat(parts[0]).isIn(FAMILY_NAMES);
            assertThat(parts[parts.length - 1]).isIn(GIVEN_NAMES);
            if (parts.length == 3) {
                assertThat(parts[1]).isIn(MIDDLE_NAMES);
            }
        }

        @RepeatedTest(10)
        void testNameWithMiddleIsFamilyMiddleGiven() {
            String[] parts = name.nameWithMiddle().split(" ");
            assertThat(parts).hasSize(3);
            assertThat(parts[0]).isIn(FAMILY_NAMES);
            assertThat(parts[1]).isIn(MIDDLE_NAMES);
            assertThat(parts[2]).isIn(GIVEN_NAMES);
        }
    }
}
