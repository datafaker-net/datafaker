package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class TwitterTest extends AbstractFakerTest {

    @Test
    void testCreatedDateForward() {
        Date testDate = new Date();
        Date constrainDate = new Date(testDate.getTime() + 3000000);
        Date generated = faker.twitter().createdTime(true, testDate, constrainDate);
        boolean test = generated.after(testDate) && generated.before(constrainDate);
        assertThat(test).isTrue();
    }

    @Test
    void testCreatedDateBackward() {
        Date testDate = new Date();
        Date constrainDate = new Date(testDate.getTime() - 3000000);
        Date generated = faker.twitter().createdTime(false, testDate, constrainDate);
        boolean test = generated.before(testDate) && generated.after(constrainDate);
        assertThat(test).isTrue();
    }

    @Test
    void testShortTwitterIdLength() {
        int expectedLength = 6;
        String generatedID = faker.twitter().twitterId(expectedLength);
        assertThat(generatedID).hasSize(expectedLength);
    }

    @RepeatedTest(100)
    void testLongTwitterIdLength() {
        int expectedLength = 25;
        String generatedID = faker.twitter().twitterId(expectedLength);
        assertThat(generatedID).hasSize(expectedLength);
    }

    @Test
    void testTwitterIdLength() {
        int expectedLength = 15;
        String generatedID = faker.twitter().twitterId(expectedLength);
        assertThat(generatedID).hasSize(expectedLength);
    }

    @Test
    void testTwitterIdUnique() {
        int expectedLength = 15;
        String generatedIDOne = faker.twitter().twitterId(expectedLength);
        String generatedIDTwo = faker.twitter().twitterId(expectedLength);
        assertThat(generatedIDOne).isNotEqualTo(generatedIDTwo);
    }

    @Test
    void testTextLength() {
        int sentenceMaxLength = 15;
        int wordMaxLength = 5;
        String text = faker.twitter().text(null, sentenceMaxLength, wordMaxLength);
        String[] textwords = text.split(" ");
        assertThat(textwords).hasSizeLessThanOrEqualTo(sentenceMaxLength);
    }

    @Test
    void testTextKeyWords() {
        int sentenceMaxLength = 15;
        int wordMaxLength = 5;
        String[] keywords = new String[]{"buy", "see"};
        String text = faker.twitter().text(keywords, sentenceMaxLength, wordMaxLength);
        String[] textwords = text.split(" ");
        boolean flag = true;
        for (String keyword : keywords) {
            boolean tmpFlag = false;
            for (String textword : textwords) {
                if (keyword.equals(textword)) {
                    tmpFlag = true;
                    break;
                }
            }
            flag = tmpFlag;
            if (!flag) {
                break;
            }
        }
        assertThat(flag).isTrue();
    }

    @Test
    void username() {
        for (int i = 0; i < 10; i++) {
            assertThat(faker.twitter().userName()).matches("[a-zA-Z\\d_\\-\u4e00-\u9fa5]+");
        }
    }

    @Test
    void userId() {
        for (int i = 0; i < 10; i++) {
            assertThat(faker.twitter().userId()).matches("\\d+");
        }
    }

    @Test
    void linkTestRules() {
        for (int i = 0; i < 10; i++) {
            assertThat(faker.twitter().getLink("John", 6)).matches("[A-Za-z\\d.:/]+");
        }
    }

    @Test
    void linkTestKeyWords() {
        for (int i = 0; i < 10; i++) {
            assertThat(faker.twitter().getLink("John", 6)).contains("John");
        }
    }
}
