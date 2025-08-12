package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Collection;

class AnimalTest extends BaseFakerTest {

    private final Animal animal = faker.animal();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(animal::name, "creature.animal.name"));
    }

    @RepeatedTest(10)
    void scientificName() {
        assertThat(animal.scientificName()).matches("[A-Z][a-z]+ [a-z]+");
    }

    @RepeatedTest(10)
    void genus() {
        assertThat(animal.genus()).matches("[A-Z][a-z]+");
    }

    @RepeatedTest(10)
    void species() {
        assertThat(animal.species()).matches("[a-z]+");
    }
}
