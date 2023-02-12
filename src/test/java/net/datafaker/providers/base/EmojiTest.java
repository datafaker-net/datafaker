package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class EmojiTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Emoji emoji = faker.emoji();
        
        return List.of(TestSpec.of(emoji::smiley, "emoji.smileys"),
            TestSpec.of(emoji::cat, "emoji.cats"));
    }
}
