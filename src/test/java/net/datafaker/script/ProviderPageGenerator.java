package net.datafaker.script;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Generator for the providers.md page.
 */
public class ProviderPageGenerator {

    public static void main(String[] args) {
        new ProviderPageGenerator().generate();
    }

    private void generate() {




    }

    public List<Provider> getProviders() throws IOException {
        File[] files = new File("/Users/erikp/UserFiles/projects/datafaker/src/main/java/net/datafaker").listFiles(File::isFile);

        List<Provider> results = new ArrayList<>();

        for (File file : files) {
            String text = Files.readString(file.toPath());

            Provider e = new Provider();
            e.name = file.getName();
            e.description = "";
            e.version = "0.8.0";
            results.add(e);
        }

        return null;
    }
}


class Provider {
    String name;
    String description;
    String version;

}
