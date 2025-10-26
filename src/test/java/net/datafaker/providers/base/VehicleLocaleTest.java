package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class VehicleLocaleTest extends BaseFakerLocaleTest {

    private static final String IRISH_VEHICLE_LICENCE_PLATE_REGEX = "[0-9]{2}[1|2]-[A-Z]{1,2}-[0-9]{1,6}";
    private static final String UKRAINIAN_VEHICLE_LICENCE_PLATE_REGEX = "^(AA|KA|AB|KB|AC|KC|AE|KE|AH|KH|AI|KI|AK|KK|AM|KM|AO|KO|AP|KP|AT|KT|AX|KX|BA|HA|BB|HB|BC|HC|BE|HE|BH|HH|BI|HI|BK|HK|BM|HM|BO|HO|BT|HT|BX|HX|CA|IA|CB|IB|CE|IE|CH|IH)\\d{4}[A-Z]{2}$";


    private final Vehicle vehicleUA = fakerUA.vehicle();

    @Override
    protected Stream<Arguments> localeProviderListTest() {
        return Stream.of(
            arguments(TestSpec.of(vehicleUA::transmission, "vehicle.transmissions"), fakerUA),
            arguments(TestSpec.of(vehicleUA::fuelType, "vehicle.fuel_types"), fakerUA)
        );
    }

    @RepeatedTest(10)
    void testLicensePlate_Ukraine() {
        assertThat(fakerUA.vehicle().licensePlate()).matches(UKRAINIAN_VEHICLE_LICENCE_PLATE_REGEX);
    }

    @RepeatedTest(10)
    void testLicensePlate_Ireland() {
        assertThat(fakerIE.vehicle().licensePlate()).matches(IRISH_VEHICLE_LICENCE_PLATE_REGEX);
    }
}
