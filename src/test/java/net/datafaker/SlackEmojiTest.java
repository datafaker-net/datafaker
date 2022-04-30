package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SlackEmojiTest extends AbstractFakerTest {

    private static final String EMOTICON_REGEX = ":([\\w-]+):";

    @Test
    void people() {
        assertThat(faker.slackEmoji().people()).matches(EMOTICON_REGEX);
    }

    @Test
    void nature() {
        assertThat(faker.slackEmoji().nature()).matches(EMOTICON_REGEX);
    }

    @Test
    void food_and_drink() {
        assertThat(faker.slackEmoji().foodAndDrink()).matches(EMOTICON_REGEX);
    }

    @Test
    void celebration() {
        assertThat(faker.slackEmoji().celebration()).matches(EMOTICON_REGEX);
    }

    @Test
    void activity() {
        assertThat(faker.slackEmoji().activity()).matches(EMOTICON_REGEX);
    }

    @Test
    void travel_and_places() {
        assertThat(faker.slackEmoji().travelAndPlaces()).matches(EMOTICON_REGEX);
    }

    @Test
    void objects_and_symbols() {
        assertThat(faker.slackEmoji().objectsAndSymbols()).matches(EMOTICON_REGEX);
    }

    @Test
    void custom() {
        assertThat(faker.slackEmoji().custom()).matches(EMOTICON_REGEX);
    }

    @Test
    void emoji() {
        assertThat(faker.slackEmoji().emoji()).matches(EMOTICON_REGEX);
    }
}
