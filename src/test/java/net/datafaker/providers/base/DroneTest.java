package net.datafaker.providers.base;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import java.util.Collection;

class DroneTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(() -> faker.drone().name(), "drone.name"),
                TestSpec.of(() -> faker.drone().batteryType(), "drone.battery_type"),
                TestSpec.of(() -> faker.drone().iso(), "drone.iso"),
                TestSpec.of(() -> faker.drone().photoFormat(), "drone.photo_format"),
                TestSpec.of(() -> faker.drone().videoFormat(), "drone.video_format"),
                TestSpec.of(() -> faker.drone().maxShutterSpeed(), "drone.max_shutter_speed"),
                TestSpec.of(() -> faker.drone().minShutterSpeed(), "drone.min_shutter_speed"),
                TestSpec.of(() -> faker.drone().shutterSpeedUnits(), "drone.shutter_speed_units"));
    }

    @Test
    void weight() {
        assertThat(faker.drone().weight()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void maxAscentSpeed() {
        assertThat(faker.drone().maxAscentSpeed()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void maxDescentSpeed() {
        assertThat(faker.drone().maxDescentSpeed()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void flightTime() {
        assertThat(faker.drone().flightTime()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void maxAltitude() {
        assertThat(faker.drone().maxAltitude()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void maxFlightDistance() {
        assertThat(faker.drone().maxFlightDistance()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void maxSpeed() {
        assertThat(faker.drone().maxSpeed()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void maxWindResistance() {
        assertThat(faker.drone().maxWindResistance()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void maxAngularVelocity() {
        assertThat(faker.drone().maxAngularVelocity()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void maxTiltAngle() {
        assertThat(faker.drone().maxTiltAngle()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void operatingTemperature() {
        assertThat(faker.drone().operatingTemperature()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void batteryCapacity() {
        assertThat(faker.drone().batteryCapacity()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void batteryVoltage() {
        assertThat(faker.drone().batteryVoltage()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void batteryWeight() {
        assertThat(faker.drone().batteryWeight()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void chargingTemperature() {
        assertThat(faker.drone().chargingTemperature()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void maxChargingPower() {
        assertThat(faker.drone().maxChargingPower()).isNotEmpty().doesNotContain("#");
    }

    @Test
    void maxResolution() {
        assertThat(faker.drone().maxResolution()).isNotEmpty().doesNotContain("#");
    }

}
