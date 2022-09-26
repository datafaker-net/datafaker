package net.datafaker.base;

/**
 * Generates different attributes related to computers, such as operating systems, types and platforms.
 *
 * @since 1.5.0
 */
public class Computer extends AbstractProvider<BaseProviders> {

    protected Computer(BaseProviders faker) {
        super(faker);
    }

    public String type() {
        return resolve("computer.type");
    }

    public String platform() {
        return resolve("computer.platform");
    }

    public String operatingSystem() {
        return resolve("computer.os." + faker.options().option("linux", "macos", "windows"));
    }

    public String linux() {
        return resolve("computer.os.linux");
    }

    public String macos() {
        return resolve("computer.os.macos");
    }

    public String windows() {
        return resolve("computer.os.windows");
    }
}
