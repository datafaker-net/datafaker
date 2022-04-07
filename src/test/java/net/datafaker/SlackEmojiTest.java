package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SlackEmojiTest extends AbstractFakerTest {

    private static final String EMOTICON_REGEX = ":([\\w-]+):";

    @Test
    public void people() {
        assertThat(faker.slackEmoji().people()).matches(EMOTICON_REGEX);
    }

    @Test
    public void nature() {
        assertThat(faker.slackEmoji().nature()).matches(EMOTICON_REGEX);
    }

    @Test
    public void food_and_drink() {
        assertThat(faker.slackEmoji().foodAndDrink()).matches(EMOTICON_REGEX);
    }

    @Test
    public void celebration() {
        assertThat(faker.slackEmoji().celebration()).matches(EMOTICON_REGEX);
    }

    @Test
    public void activity() {
        assertThat(faker.slackEmoji().activity()).matches(EMOTICON_REGEX);
    }

    @Test
    public void travel_and_places() {
        assertThat(faker.slackEmoji().travelAndPlaces()).matches(EMOTICON_REGEX);
    }

    @Test
    public void objects_and_symbols() {
        assertThat(faker.slackEmoji().objectsAndSymbols()).matches(EMOTICON_REGEX);
    }

    @Test
    public void custom() {
        assertThat(faker.slackEmoji().custom()).matches(EMOTICON_REGEX);
    }

    @Test
    public void emoji() {
        assertThat(faker.slackEmoji().emoji()).matches(EMOTICON_REGEX);
    }
}
