package net.datafaker.providers.base;

import java.util.Collection;
import java.util.List;

class SlackEmojiListTest extends BaseFakerTest {

    @Override
    protected Collection<TestSpec> providerListTest() {
        SlackEmoji slackEmoji = faker.slackEmoji();
        return List.of(
            TestSpec.of(slackEmoji::people, "slack_emoji.people"),
            TestSpec.of(slackEmoji::nature, "slack_emoji.nature"),
            TestSpec.of(slackEmoji::foodAndDrink, "slack_emoji.food_and_drink"),
            TestSpec.of(slackEmoji::celebration, "slack_emoji.celebration"),
            TestSpec.of(slackEmoji::activity, "slack_emoji.activity"),
            TestSpec.of(slackEmoji::travelAndPlaces, "slack_emoji.travel_and_places"),
            TestSpec.of(slackEmoji::objectsAndSymbols, "slack_emoji.objects_and_symbols"),
            TestSpec.of(slackEmoji::custom, "slack_emoji.custom"));
    }
}
