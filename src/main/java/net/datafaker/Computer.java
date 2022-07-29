package net.datafaker;


import java.util.ArrayList;
import java.util.List;

public class Computer {

    private final Faker faker;

    protected Computer(Faker faker) {
        this.faker = faker;
    }

    public String type() {
        return faker.resolve("computer.type");
    }

    public String platform() {
        return faker.resolve("computer.platform");
    }

    public String operatingSystem() {
        List<String> operatingSystems = new ArrayList<>();
        operatingSystems.add("linux");
        operatingSystems.add("macos");
        operatingSystems.add("windows");
        return faker.resolve("computer.os." + operatingSystems.get(faker.random().nextInt(0, 2)));
    }

    public String linux() {
        return faker.resolve("computer.os.linux");
    }

    public String macos() {
        return faker.resolve("computer.os.macos");
    }

    public String windows() {
        return faker.resolve("computer.os.windows");
    }
}
