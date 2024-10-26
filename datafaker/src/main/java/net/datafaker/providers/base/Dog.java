package net.datafaker.providers.base;

/**
 * @since 0.8.0
 */
public class Dog extends AbstractProvider<BaseProviders> {

    protected Dog(BaseProviders faker) {
        super(faker);
    }

    public String name() {
        return resolve("creature.dog.name");
    }

    public String breed() {
        return resolve("creature.dog.breed");
    }

    public String sound() {
        return resolve("creature.dog.sound");
    }

    public String memePhrase() {
        return resolve("creature.dog.meme_phrase");
    }

    public String age() {
        return resolve("creature.dog.age");
    }

    public String coatLength() {
        return resolve("creature.dog.coat_length");
    }

    public String gender() {
        return resolve("creature.dog.gender");
    }

    public String size() {
        return resolve("creature.dog.size");
    }
}
