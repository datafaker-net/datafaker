package net.datafaker;

import org.junit.Test;

import java.util.Date;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class TwitterTest extends AbstractFakerTest {

    @Test
    public void testCreatedDateForward() {
        Date testDate = new Date();
        Date constrainDate = new Date(testDate.getTime() + 3000000);
        Date generated = faker.twitter().created_time(true, testDate, constrainDate);
        boolean test = generated.after(testDate) && generated.before(constrainDate);
        assertTrue(test);
    }

    @Test
    public void testCreatedDateBackward() {
        Date testDate = new Date();
        Date constrainDate = new Date(testDate.getTime() - 3000000);
        Date generated = faker.twitter().created_time(false, testDate, constrainDate);
        boolean test = generated.before(testDate) && generated.after(constrainDate);
        assertTrue(test);
    }

    @Test
    public void testShortTwitterIdLength() {
        int expectedLength = 6;
        String generatedID = faker.twitter().twitterId(expectedLength);
        assertEquals(generatedID.length(), expectedLength);
    }

    @Test
    public void testLongTwitterIdLength() {
        int expectedLength = 25;
        String generatedID = faker.twitter().twitterId(expectedLength);
        assertEquals(generatedID.length(), expectedLength);
    }

    @Test
    public void testTwitterIdLength() {
        int expectedLength = 15;
        String generatedID = faker.twitter().twitterId(expectedLength);
        assertEquals(generatedID.length(), expectedLength);
    }

    @Test
    public void testTwitterIdUnique() {
        int expectedLength = 15;
        String generatedIDOne = faker.twitter().twitterId(expectedLength);
        String generatedIDTwo = faker.twitter().twitterId(expectedLength);
        assertNotEquals(generatedIDOne, generatedIDTwo);
    }

    @Test
    public void testTextLength() {
        int sentenceMaxLength = 15;
        int wordMaxLength = 5;
        String text = faker.twitter().text(null, sentenceMaxLength, wordMaxLength);
        String[] textwords = text.split(" ");
        assertTrue(textwords.length <= sentenceMaxLength);
    }

    @Test
    public void testTextKeyWords() {
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
        assertTrue(flag);
    }

    @Test
    public void username() {
        for (int i = 0; i < 10; i++) {
            assertThat(faker.twitter().userName(), matchesRegularExpression("[a-zA-Z0-9_\\-\u4e00-\u9fa5]+"));
        }
    }

    @Test
    public void userId() {
        for (int i = 0; i < 10; i++) {
            assertThat(faker.twitter().userId(), matchesRegularExpression("[0-9]+"));
        }
    }

    @Test
    public void linkTestRules() {
        for (int i = 0; i < 10; i++) {
            assertThat(faker.twitter().getLink("John", 6), matchesRegularExpression("[A-Za-z0-9.:/]+"));
        }
    }

    @Test
    public void linkTestKeyWords() {
        for (int i = 0; i < 10; i++) {
            assertTrue(faker.twitter().getLink("John", 6).contains("John"));
        }
    }
}
