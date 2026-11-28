import net.datafaker.idnumbers.IdNumberGenerator;
import net.datafaker.providers.base.AbstractProvider;
import net.datafaker.providers.base.ProviderRegistration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GenerateNativeImageMetadata {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Path classes = Path.of(args[0]);
        Path metadata = classes.resolve("META-INF/native-image/net.datafaker/datafaker");
        Files.createDirectories(metadata);
        // Native Image for JDK 21 reads the separate reflection and resource files.
        Files.writeString(metadata.resolve("reflect-config.json"), reflectionConfig(classes));
        Files.writeString(metadata.resolve("resource-config.json"), resourceConfig(classes));
        // Exact reachability checking must also allow lookups for absent locale overrides.
        Files.writeString(metadata.resolve("reachability-metadata.json"), """
            {
              "resources": [
                {"glob": "*.yml"},
                {"glob": "en/*.yml"}
              ]
            }
            """);
    }

    private static String reflectionConfig(Path classes) throws IOException, ClassNotFoundException {
        Map<String, Set<String>> flagsByType = new TreeMap<>();
        try (var files = Files.walk(classes.resolve("net/datafaker"))) {
            for (Path file : files.filter(path -> path.toString().endsWith(".class")).toList()) {
                String name = classes.relativize(file).toString().replace(File.separatorChar, '.');
                Class<?> type = Class.forName(name.substring(0, name.length() - ".class".length()),
                    false, GenerateNativeImageMetadata.class.getClassLoader());

                if (AbstractProvider.class.isAssignableFrom(type) || ProviderRegistration.class.isAssignableFrom(type)) {
                    flags(flagsByType, type).add("allPublicMethods");
                    for (Method method : type.getMethods()) {
                        registerExpressionArguments(flagsByType, method);
                    }
                }
                if (IdNumberGenerator.class.isAssignableFrom(type) && !Modifier.isAbstract(type.getModifiers())) {
                    flags(flagsByType, type).add("allDeclaredConstructors");
                }
            }
        }

        return flagsByType.entrySet().stream()
            .map(entry -> "  {\"name\": " + json(entry.getKey()) + entry.getValue().stream()
                .map(flag -> ", " + json(flag) + ": true")
                .collect(Collectors.joining()) + "}")
            .collect(Collectors.joining(",\n", "[\n", "\n]\n"));
    }

    private static void registerExpressionArguments(Map<String, Set<String>> flagsByType, Method method) {
        Class<?>[] parameters = method.getParameterTypes();
        for (int i = 0; i < parameters.length; i++) {
            Class<?> type = parameters[i];
            if (method.isVarArgs() && i == parameters.length - 1) {
                flags(flagsByType, type);
                type = type.getComponentType();
            }
            if (type.isEnum()) {
                flags(flagsByType, type).add("allPublicMethods");
            } else if (Arrays.stream(type.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 1
                    && constructor.getParameterTypes()[0] == String.class)) {
                flags(flagsByType, type).add("allPublicConstructors");
            }
        }
    }

    private static Set<String> flags(Map<String, Set<String>> flagsByType, Class<?> type) {
        return flagsByType.computeIfAbsent(type.getTypeName(), key -> new TreeSet<>());
    }

    private static String resourceConfig(Path classes) throws IOException {
        Set<String> resources = new TreeSet<>();
        try (var files = Files.walk(classes)) {
            files.filter(Files::isRegularFile)
                .map(file -> classes.relativize(file).toString().replace(File.separatorChar, '/'))
                .filter(GenerateNativeImageMetadata::isResource)
                .forEach(resources::add);
        }
        for (String entry : System.getProperty("java.class.path").split(Pattern.quote(File.pathSeparator))) {
            if (entry.endsWith(".jar")) {
                try (JarFile jar = new JarFile(entry)) {
                    jar.stream().filter(file -> !file.isDirectory()).map(file -> file.getName())
                        .filter(GenerateNativeImageMetadata::isResource).forEach(resources::add);
                }
            }
        }

        return resources.stream()
            .map(name -> "    {\"pattern\": " + json(Pattern.quote(name)) + "}")
            .collect(Collectors.joining(",\n", "{\"resources\": {\"includes\": [\n", "\n]}}\n"));
    }

    private static boolean isResource(String name) {
        return !name.endsWith(".class") && !name.startsWith("META-INF/native-image/")
            && !name.equals("META-INF/MANIFEST.MF")
            && !name.endsWith(".SF") && !name.endsWith(".RSA") && !name.endsWith(".DSA");
    }

    private static String json(String value) {
        StringBuilder result = new StringBuilder("\"");
        for (char c : value.toCharArray()) {
            if (c == '"' || c == '\\') {
                result.append('\\').append(c);
            } else if (c < ' ') {
                result.append("\\u%04x".formatted((int) c));
            } else {
                result.append(c);
            }
        }
        return result.append('"').toString();
    }
}
