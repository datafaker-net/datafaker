package net.datafaker.providers.base;


import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GarmentSizeTest extends BaseFakerTest<BaseFaker> {

    @Test
    void testSize() {
        // Given
        List<String> actualSizes = getActualSizes();
        // When
        String size = faker.garmentSize().size();
        // Then
        assertThat(actualSizes).anyMatch(size::equals);
    }

    @SuppressWarnings("unchecked")
    private List<String> getActualSizes() {
        return (List<String>) faker.fakeValuesService().fetchObject("garments_sizes.sizes", faker.getContext());
    }
}
