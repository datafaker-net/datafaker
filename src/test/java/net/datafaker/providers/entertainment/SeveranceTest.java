package net.datafaker.providers.entertainment;

import net.datafaker.Faker;

import java.util.Collection;
import java.util.List;

class SeveranceTest extends EntertainmentFakerTest {

    private final Severance severance = getFaker().severance();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(severance::character, "severance.characters")
        );
    }

    public static void main(String[] args) {
        Faker f = new Faker();
        System.out.println(f.severance().character());
    }
}
