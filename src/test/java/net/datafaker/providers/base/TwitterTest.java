package net.datafaker.providers.base;

import net.datafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class TwitterTest {

    private final Twitter twitter = new Faker().twitter();

    @Test
    @SuppressWarnings("removal")
    void testCreatedDateForward() {
        Date testDate = new Date();
        Date constrainDate = new Date(testDate.getTime() + 3000000);
        Date generated = twitter.createdTime(true, testDate, constrainDate);
        boolean test = generated.after(testDate) && generated.before(constrainDate);
        assertThat(test).isTrue();
    }

    @Test
    @SuppressWarnings("removal")
    void testCreatedDateBackward() {
        Date testDate = new Date();
        Date constrainDate = new Date(testDate.getTime() - 3000000);
        Date generated = twitter.createdTime(false, testDate, constrainDate);
        boolean test = generated.before(testDate) && generated.after(constrainDate);
        assertThat(test).isTrue();
    }

    @Test
    void testShortTwitterIdLength() {
        int expectedLength = 6;
        String generatedID = twitter.twitterId(expectedLength);
        assertThat(generatedID).hasSize(expectedLength);
    }

    @RepeatedTest(10)
    void testLongTwitterIdLength() {
        int expectedLength = 25;
        String generatedID = twitter.twitterId(expectedLength);
        assertThat(generatedID).hasSize(expectedLength);
    }

    @Test
    void testTwitterIdLength() {
        int expectedLength = 15;
        String generatedID = twitter.twitterId(expectedLength);
        assertThat(generatedID).hasSize(expectedLength);
    }

    @Test
    void testTwitterIdUnique() {
        int expectedLength = 15;
        String generatedIDOne = twitter.twitterId(expectedLength);
        String generatedIDTwo = twitter.twitterId(expectedLength);
        assertThat(generatedIDOne).isNotEqualTo(generatedIDTwo);
    }

    @Test
    void testTextLength() {
        int sentenceMaxLength = 15;
        int wordMaxLength = 5;
        String text = twitter.text(null, sentenceMaxLength, wordMaxLength);
        String[] textwords = text.split(" ");
        assertThat(textwords).hasSizeLessThanOrEqualTo(sentenceMaxLength);
    }

    @Test
    void testTextKeyWords() {
        int sentenceMaxLength = 15;
        int wordMaxLength = 5;
        String[] keywords = {"buy", "see"};
        String text = twitter.text(keywords, sentenceMaxLength, wordMaxLength);
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
        final Pattern pattern = Pattern.compile("[a-zA-Z\\d_\\-\u4e00-\u9fa5]+");
        for (int i = 0; i < 10; i++) {
            assertThat(twitter.userName()).matches(pattern);
        }
    }

    @Test
    void userId() {
        Pattern pattern = Pattern.compile("\\d+");
        for (int i = 0; i < 10; i++) {
            assertThat(twitter.userId()).matches(pattern);
        }
    }

    @Test
    void linkTestRules() {
        final Pattern pattern = Pattern.compile("[A-Za-z\\d.:/]+");
        for (int i = 0; i < 10; i++) {
            assertThat(twitter.getLink("John", 6)).matches(pattern);
        }
    }

    @Test
    void linkTestKeyWords() {
        for (int i = 0; i < 10; i++) {
            assertThat(twitter.getLink("John", 6)).contains("John");
        }
    }
}
