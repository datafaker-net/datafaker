package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class EmojiTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Emoji emoji = faker.emoji();
        
        return Arrays.asList(TestSpec.of(emoji::smiley, "emoji.smileys"),
            TestSpec.of(emoji::cat, "emoji.cats"));
    }
}
