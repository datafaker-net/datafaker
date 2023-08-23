package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class TireTest extends BaseFakerTest<BaseFaker> {

    private final static String CODE_PATTERN = "\\d{3}/\\d{2,3}R\\d{2}\\.?\\d?";
    private final Tire tire = faker.tire();

    @Test
    void testDefaultPrefixedCode() {
        assertThat(tire.code(true)).matches("P" + CODE_PATTERN);
        assertThat(tire.code(false)).matches(CODE_PATTERN);
    }

    @Test
    void testMiscPrefixedCode() {
        String prefix = "misc";
        assertThat(tire.code(prefix)).matches(prefix + CODE_PATTERN);
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(tire::code, "tire.code", CODE_PATTERN),
                TestSpec.of(tire::vehicleType, "tire.vehicle_type", "[A-Z]{1,2}"),
                TestSpec.of(tire::width, "tire.width", "\\d{3}"),
                TestSpec.of(tire::aspectRatio, "tire.aspect_ratio", "\\d{2}"),
                TestSpec.of(tire::construction, "tire.construction"),
                TestSpec.of(tire::rimSize, "tire.rim_size", "\\d{2}\\.?\\d?"),
                TestSpec.of(tire::loadIndex, "tire.load_index", "\\d{2,3}"),
                TestSpec.of(tire::speedrating, "tire.speed_rating", "\\(?[A-Z][\\d)]?"));
    }
}
