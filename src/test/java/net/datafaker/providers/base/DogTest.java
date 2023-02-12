package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class DogTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Dog dog = faker.dog();
        return List.of(TestSpec.of(dog::name, "creature.dog.name"),
            TestSpec.of(dog::breed, "creature.dog.breed"),
            TestSpec.of(dog::sound, "creature.dog.sound"),
            TestSpec.of(dog::memePhrase, "creature.dog.meme_phrase"),
            TestSpec.of(dog::age, "creature.dog.age"),
            TestSpec.of(dog::gender, "creature.dog.gender"),
            TestSpec.of(dog::coatLength, "creature.dog.coat_length"),
            TestSpec.of(dog::size, "creature.dog.size"));
    }
}
