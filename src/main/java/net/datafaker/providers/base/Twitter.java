package net.datafaker.providers.base;

import net.datafaker.service.RandomService;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Creates fake Twitter messages.
 *
 * @since 0.9.0
 */
public class Twitter extends AbstractProvider<BaseProviders> {
    private static final Logger LOGGER = Logger.getLogger(Twitter.class.getName());
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern("yyyyMMddHHmmss").withZone(ZoneId.systemDefault());
    private static final String BASIC_STRING = "QabR0cYdZ1efSg2hi3jNOPkTUM4VLlmXK5nJo6WIpHGqF7rEs8tDuC9vwBxAyz";

    /**
     * @param faker used as constructor
     */
    protected Twitter(BaseProviders faker) {
        super(faker);
    }

    /**
     * Used to fake a new Twitter Date.
     *
     * @param forward     to determined if the returned date is later (or before) the given date.
     * @param base        the base date given as a start point.
     * @param constraints used to constrain the returned date range.
     * @return a new date later (or before) the base date with respect to the constraint (no later/earlier than the constraint).
     * @deprecated better to use TimeAndDate for more flexibility
     */
    @Deprecated(since = "2.3.0", forRemoval = true)
    public Date createdTime(boolean forward, Date base, Date constraints) {
        final RandomService random = faker.random();
        final long time = base.getTime();
        if (forward) {
            return new Date(time + (long) (random.nextDouble() * (constraints.getTime() - time)));
        } else {
            return new Date(time - (long) (random.nextDouble() * (time - constraints.getTime())));
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
            LOGGER.warning("expectedLength <= 6 may easily cause twitter id collision. And expectedLength >= 25" +
                " can be easily out of bound.");
        }
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {
            hashCodeV = -hashCodeV;
        }
        String id1 = String.valueOf(hashCodeV);

        String newDate = DATE_TIME_FORMATTER.format(Instant.now());
        int capacity = Math.max(0, expectedLength - id1.length() - newDate.length());
        StringBuilder result = new StringBuilder(capacity);
        RandomService random = faker.random();
        for (int i = 0; i < capacity; i++) {
            result.append(random.nextInt(10));
        }
        result.append(newDate);

        StringBuilder sb = new StringBuilder();
        int i = 0;
        int j = 0;
        while (i < id1.length() || j < result.length()) {
            if (i < id1.length()) {
                sb.append(id1.charAt(i++));
            }
            if (j < result.length()) {
                sb.append(result.charAt(j++));
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
            LOGGER.warning("Word length less than 2 is dangerous. Exceptions can be raised.");
        }
        ArrayList<String> text = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        RandomService random = faker.random();
        int sentenceLength = random.nextInt(1, sentenceMaxLength);

        for (int i = 0; i < sentenceLength; i++) {
            int tmpWordLength = random.nextInt(3, wordMaxLength);
            for (int j = 0; j < tmpWordLength; j++) {
                sb.append(BASIC_STRING.charAt(random.nextInt(BASIC_STRING.length())));
            }
            text.add(sb.toString());
            sb.setLength(0);
        }
        if (keywords != null) {
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
        return resolve("twitter.user_name");
    }

    /**
     * @return Return a user id using the twitter.user_name.
     */
    public String userId() {
        return resolve("twitter.user_id");
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
            LOGGER.warning("Extra length <=4 can cause collision.");
        }
        RandomService random = faker.random();
        final char[] res = new char[extraLength + 1];
        res[0] = '/';
        for (int i = 1; i < res.length; i++) {
            res[i] = BASIC_STRING.charAt(random.nextInt(BASIC_STRING.length()));
        }
        return "https://twitter.com/" + username + String.valueOf(res);
    }
}
