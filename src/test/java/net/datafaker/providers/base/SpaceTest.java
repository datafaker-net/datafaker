package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Collection;

class SpaceTest extends BaseFakerTest<BaseFaker> {

    private static final String SPACE_REGEX = "(?:\\w+ ?){2,3}";
    
    @Override
    protected Collection<TestSpec> providerListTest() {
        Space space = faker.space();
        return List.of(TestSpec.of(space::planet, "space.planet", SPACE_REGEX),
            TestSpec.of(space::moon, "space.moon", SPACE_REGEX),
            TestSpec.of(space::galaxy, "space.galaxy", SPACE_REGEX),
            TestSpec.of(space::nebula, "space.nebula", SPACE_REGEX),
            TestSpec.of(space::starCluster, "space.star_cluster", "(?:\\w+[ -]?){1,3}"),
            TestSpec.of(space::constellation, "space.constellation", SPACE_REGEX),
            TestSpec.of(space::star, "space.star", "(\\w+[ -]?){2,3}"),
            TestSpec.of(space::agency, "space.agency", "(?:\\w+ ?){2,5}"),
            TestSpec.of(space::agencyAbbreviation, "space.agency_abv", SPACE_REGEX),
            TestSpec.of(space::nasaSpaceCraft, "space.nasa_space_craft", SPACE_REGEX),
            TestSpec.of(space::company, "space.company", "(?:(?:\\w|')+ ?){2,4}"),
            TestSpec.of(space::meteorite, "space.meteorite", "(?U)(?:[\\w()]+[ -–]?){1,4}"));
    }

    @Test
    void distanceMeasurement() {
        assertThat(faker.space().distanceMeasurement()).matches("(?:\\w+ ?){2,3}");
    }
}
