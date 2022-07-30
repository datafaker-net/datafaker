package net.datafaker;

/**
 * @since 0.8.0
 */
public class Dog extends AbstractProvider {

    protected Dog(Faker faker) {
        super(faker);
    }

    public String name() {
        return faker.fakeValuesService().resolve("creature.dog.name", this);
    }

    public String breed() {
        return faker.fakeValuesService().resolve("creature.dog.breed", this);
    }

    public String sound() {
        return faker.fakeValuesService().resolve("creature.dog.sound", this);
    }

    public String memePhrase() {
        return faker.fakeValuesService().resolve("creature.dog.meme_phrase", this);
    }

    public String age() {
        return faker.fakeValuesService().resolve("creature.dog.age", this);
    }

    public String coatLength() {
        return faker.fakeValuesService().resolve("creature.dog.coat_length", this);
    }

    public String gender() {
        return faker.fakeValuesService().resolve("creature.dog.gender", this);
    }

    public String size() {
        return faker.fakeValuesService().resolve("creature.dog.size", this);
    }
}
