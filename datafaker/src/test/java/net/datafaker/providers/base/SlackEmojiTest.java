package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SlackEmojiTest extends BaseFakerTest<BaseFaker> {

    private static final String EMOTICON_REGEX = ":(?:[\\w-]+):";
    private final SlackEmoji slackEmoji = faker.slackEmoji();

    @Test
    void people() {
        assertThat(slackEmoji.people()).matches(EMOTICON_REGEX);
    }

    @Test
    void nature() {
        assertThat(slackEmoji.nature()).matches(EMOTICON_REGEX);
    }

    @Test
    void food_and_drink() {
        assertThat(slackEmoji.foodAndDrink()).matches(EMOTICON_REGEX);
    }

    @Test
    void celebration() {
        assertThat(slackEmoji.celebration()).matches(EMOTICON_REGEX);
    }

    @Test
    void activity() {
        assertThat(slackEmoji.activity()).matches(EMOTICON_REGEX);
    }

    @Test
    void travel_and_places() {
        assertThat(slackEmoji.travelAndPlaces()).matches(EMOTICON_REGEX);
    }

    @Test
    void objects_and_symbols() {
        assertThat(slackEmoji.objectsAndSymbols()).matches(EMOTICON_REGEX);
    }

    @Test
    void custom() {
        assertThat(slackEmoji.custom()).matches(EMOTICON_REGEX);
    }

    @Test
    void emoji() {
        assertThat(slackEmoji.emoji()).matches(EMOTICON_REGEX);
    }
}
