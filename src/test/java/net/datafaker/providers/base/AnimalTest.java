package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Collection;

class AnimalTest extends BaseFakerTest<BaseFaker> {

    private final Animal animal = faker.animal();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(animal::name, "creature.animal.name"));
    }

    @RepeatedTest(100)
    void scientificName() {
        assertThat(animal.scientificName()).matches("[A-Z][a-z]+ [a-z]+");
    }

    @RepeatedTest(100)
    void genus() {
        assertThat(animal.genus()).matches("[A-Z][a-z]+");
    }

    @RepeatedTest(100)
    void species() {
        assertThat(animal.species()).matches("[a-z]+");
    }
}
