package net.datafaker.providers.base;

/**
 * Emojis picked from <a href="http://unicode.org/Public/emoji/1.0/emoji-data.txt">Emoji 1.0</a>.
 *
 * @since 1.7.0
 */
public class Emoji extends AbstractProvider<BaseProviders> {

    protected Emoji(BaseProviders faker) {
        super(faker);
    }

    public String smiley() {
        return resolve("emoji.smileys");
    }

    public String cat() {
        return resolve("emoji.cats");
    }

}
