package net.datafaker;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;

public class DeviceTest extends AbstractFakerTest {

    @Test
    public void modelName() {
        assertThat(faker.device().modelName(), not(is(emptyOrNullString())));
    }

    @Test
    public void platform() {
        assertThat(faker.device().platform(), not(is(emptyOrNullString())));
    }

    @Test
    public void manufacturer() {
        assertThat(faker.device().manufacturer(), not(is(emptyOrNullString())));
    }

    @Test
    public void serial() {
        assertThat(faker.device().serial(), not(is(emptyOrNullString())));
    }

}
