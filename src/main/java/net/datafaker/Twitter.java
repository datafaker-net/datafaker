package net.datafaker;

import net.datafaker.service.RandomService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Creates fake Twitter messages.
 *
 * @since 0.9.0
 */
public class Twitter {

    private final Faker faker;
    private final String basicstr = "QabR0cYdZ1efSg2hi3jNOPkTUM4VLlmXK5nJo6WIpHGqF7rEs8tDuC9vwBxAyz";

    /**
     * @param faker used as constructor
     */
    protected Twitter(Faker faker) {
        this.faker = faker;
    }

    /**
     * Used to fake a new Twitter Date.
     *
     * @param forward    to determined if the returned date is later (or before) the given date.
     * @param base       the base date given as a start point.
     * @param constrains used to constrain the returned date range.
     * @return a new date later (or before) the base date with respect to the constraint (no later/earlier than the constrain).
     */
    public Date createdTime(boolean forward, Date base, Date constrains) {
        final RandomService random = faker.random();
        if (forward) {
            return new Date(base.getTime() + (long) (random.nextDouble() * (constrains.getTime() - base.getTime())));
        } else {
            return new Date(base.getTime() - (long) (random.nextDouble() * (base.getTime() - constrains.getTime())));
        }
    }

    /**
     * Used to fake a new Twitter ID without collision using both current time and uuid as seeds.
     *
     * @param expectedLength the expected length of the twitter id. Should be greater or equals to 6 and
     *                       less than or equals to 25.
     * @return a new Twitter ID consists of purely numbers.
     */
    public String twitterId(int expectedLength) {
        if (expectedLength <= 6 || expectedLength >= 25) {
            Logger logger = Logger.getLogger(Twitter.class.getName());
            logger.setLevel(Level.WARNING);
            logger.warning("expectedLength <= 6 may easily cause twitter id collision. And expectedLength >= 25" +
                " can be easily out of bound.");
        }
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {
            hashCodeV = -hashCodeV;
        }
        String id1 = String.valueOf(hashCodeV);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate = sdf.format(new Date());
        String result = "";
        RandomService random = faker.random();
        for (int i = 0; i < expectedLength - id1.length() - newDate.length(); i++) {
            result = result.concat(String.valueOf(random.nextInt(10)));
        }
        String id2 = result + newDate;

        StringBuilder sb = new StringBuilder();
        int i = 0;
        int j = 0;
        while (i < id1.length() || j < id2.length()) {
            if (i < id1.length()) {
                sb.append(id1.charAt(i++));
            }
            if (j < id2.length()) {
                sb.append(id2.charAt(j++));
            }
        }
        String id = sb.toString();
        int start = random.nextInt(id.length() - expectedLength + 1);
        id = id.substring(start, start + expectedLength);
        return id;
    }

    /**
     * To fake a new text context for the Twitter.
     *
     * @param keywords          the keywords that you wish to appear in the text.
     * @param sentenceMaxLength the text should be in range of the sentence max length.
     * @param wordMaxLength     each word should be in range of the word max length.
     * @return a new fake text for the Twitter.
     */
    public String text(String[] keywords, int sentenceMaxLength, int wordMaxLength) {
        if (wordMaxLength <= 2) {
            Logger logger = Logger.getLogger(Twitter.class.getName());
            logger.setLevel(Level.WARNING);
            logger.warning("Word length less than 2 is dangerous. Exceptions can be raised.");
        }
        ArrayList<String> text = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        RandomService random = faker.random();
        int sentenceLength = random.nextInt(1, sentenceMaxLength);

        for (int i = 0; i < sentenceLength; i++) {
            int tmpWordLength = random.nextInt(3, wordMaxLength);
            for (int j = 0; j < tmpWordLength; j++) {
                sb.append(basicstr.charAt(random.nextInt(basicstr.length())));
            }
            text.add(sb.toString());
            sb.setLength(0);
        }
        if (keywords != null && keywords.length > 0) {
            for (String keyword : keywords) {
                int position = random.nextInt(text.size());
                text.add(position, keyword);
            }
        }
        return String.join(" ", text);
    }

    /**
     * @return Return a user name using the twitter.user_name.
     */
    public String userName() {
        return faker.fakeValuesService().resolve("twitter.user_name", this, faker);
    }

    /**
     * @return Return a user id using the twitter.user_name.
     */
    public String userId() {
        return faker.fakeValuesService().resolve("twitter.user_id", this, faker);
    }

    /**
     * return a fake link to a Twitter message. The extra length is expected to be larger than 4.
     *
     * @param username    the username will be used in the link.
     * @param extraLength the expected length of the extra link part.
     * @return return a fake link to a Twitter message.
     */
    public String getLink(String username, int extraLength) {
        if (extraLength <= 4) {
            Logger logger = Logger.getLogger(Twitter.class.getName());
            logger.setLevel(Level.WARNING);
            logger.warning("Extra length <=4 can cause collision.");
        }
        RandomService random = faker.random();
        StringBuilder sb = new StringBuilder();
        sb.append(username).append("/");

        for (int i = 0; i < extraLength; i++) {
            sb.append(basicstr.charAt(random.nextInt(basicstr.length())));
        }
        return "https://twitter.com/" + sb;
    }
}
