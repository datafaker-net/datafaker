package net.datafaker.providers.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class VehicleListTest extends BaseFakerTest {
    private static final List<String> MAKES = List.of(
        "Audi", "Alfa-Romeo", "BMW", "Buick", "Chevy", "Citroën", "Dacia", "Dodge", "Fiat", "Ford", "Honda",
        "Hyundai", "Kia", "Lancia", "Lincoln", "Mazda", "Mercedes-Benz", "Nio", "Nissan", "Opel", "Peugeot",
        "Renault", "Seat", "Skoda", "Tesla", "Toyota", "Volkswagen", "Volvo", "Xpeng");

    @Override
    protected Collection<TestSpec> providerListTest() {
        Vehicle vehicle = faker.vehicle();
        List<TestSpec> specs = new ArrayList<>(List.of(
            TestSpec.of(vehicle::manufacturer, "vehicle.manufacture"),
            TestSpec.of(vehicle::style, "vehicle.styles"),
            TestSpec.of(vehicle::color, "vehicle.colors"),
            TestSpec.of(vehicle::upholsteryColor, "vehicle.upholstery_colors"),
            TestSpec.of(vehicle::upholsteryFabric, "vehicle.upholstery_fabrics"),
            TestSpec.of(vehicle::driveType, "vehicle.drive_types"),
            TestSpec.of(vehicle::carType, "vehicle.car_types"),
            TestSpec.of(vehicle::doors, "vehicle.doors")));
        for (String make : MAKES) {
            specs.add(TestSpec.of(() -> vehicle.model(make), "vehicle.models_by_make." + make));
        }
        return specs;
    }
}
