package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

public class DoctorWhoTest extends EntertainmentFakerTest {

    private final DoctorWho doctorWho = getFaker().doctorWho();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(doctorWho::character, "dr_who.character"),
            TestSpec.of(doctorWho::doctor, "dr_who.the_doctors"),
            TestSpec.of(doctorWho::actor, "dr_who.actors"),
            TestSpec.of(doctorWho::catchPhrase, "dr_who.catch_phrases"),
            TestSpec.of(doctorWho::quote, "dr_who.quotes"),
            TestSpec.of(doctorWho::villain, "dr_who.villains"),
            TestSpec.of(doctorWho::species, "dr_who.species")
        );
    }
}
