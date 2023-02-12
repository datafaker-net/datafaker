package net.datafaker.script;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.comments.CommentsCollection;
import com.github.javaparser.ast.comments.JavadocComment;
import com.google.common.base.Strings;
import net.datafaker.providers.base.AbstractProvider;
import org.reflections.Reflections;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.reflections.scanners.Scanners.SubTypes;

public class ProvidersDocsGenerator {
    private final JavaParser parser = new JavaParser();

    // Specify destination of 'providers.md' file
    private static final String DESTINATION_PLACE_OF_PROVIDERS_FILE = "target/test-classes/providers.md";
    private final Reflections reflections = new Reflections("net.datafaker.providers");
    private final Comparator<Class<?>> providersComparatorBySimpleName = Comparator
        .comparing(Class::getSimpleName);
    private final Set<Class<?>> subTypes = new TreeSet<>(providersComparatorBySimpleName);

    // Exclude non-providers from generation
    private final Set<String> providersToExcludeFromGeneration = Set.of("CustomFakerTest", "InsectFromFile", "Insect");

    private final Set<String> fakersWithoutSinceTag = new HashSet<>();

    public static void main(String[] args) {
        ProvidersDocsGenerator providersDocsGenerator = new ProvidersDocsGenerator();
        providersDocsGenerator.initSubtypes();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DESTINATION_PLACE_OF_PROVIDERS_FILE))) {
            providersDocsGenerator.constructHeaderInProvidersFile(writer);
            providersDocsGenerator.generateProvidersDocs(writer);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initSubtypes() {
        subTypes.addAll(
            reflections.get(SubTypes.of(AbstractProvider.class).asClass())
                .stream()
                .filter(t -> !providersToExcludeFromGeneration.contains(t.getSimpleName()))
                .collect(Collectors.toSet()));
    }

    void generateProvidersDocs(BufferedWriter writer) throws IOException {
        for (Class<?> clazz : subTypes) {
            String groupName = extractGroupName(clazz);
            String comment = extractCommentFromJavadoc("src/main/java/net/datafaker/providers/" + groupName + "/" + clazz.getSimpleName() + ".java");
            writer.write(Column.generateRow(' ', clazz.getSimpleName(), comment, formatGroupName(groupName)));
        }
        System.out.println("Providers without '@since' tag: " + fakersWithoutSinceTag);
    }

    private String extractGroupName(Class<?> clazz) {
        // `packageName` should be such format: net.datafaker.providers.<groupName> (e.g. base, sport, movie)
        String packageName = clazz.getPackage().getName();
        // And just splitting by '.' we're getting groupName (e.g. base, sport, movie)
        return packageName.split("\\.")[3];
    }

    /**
     * The implementation assumes that the JavaDoc with the '@since' tag
     * will always be at the very beginning of a class.
     *
     * @param filePath              Path to the Faker(provider) to be searched
     * @return Entire first JavaDoc in the class
     */
    private String extractCommentFromJavadoc(String filePath) {
        try {
            final File file = new File(filePath);

            Optional<CommentsCollection> commentsCollection =
                parser.parse(file).getCommentsCollection();

            if (commentsCollection.isEmpty()) {
                fakersWithoutSinceTag.add(filePath);
                return "";
            }

            Optional<JavadocComment> javadocComments = commentsCollection.get().getJavadocComments()
                .stream()
                .findFirst();

            if (javadocComments.isEmpty()) {
                fakersWithoutSinceTag.add(filePath);
                return "";
            }

            String comment = javadocComments.get().getContent();
            boolean containsSinceTag = comment.contains("@since");

            if (!containsSinceTag) {
                fakersWithoutSinceTag.add(filePath);
            }

            return comment;
        } catch (FileNotFoundException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Writes header and table header to new 'providers.md'
     */
    private void constructHeaderInProvidersFile(Writer writer) throws IOException {
        final String header = TextBlock.HEADER.getText();
        final String groupDescriptions = TextBlock.GROUP_DESCRIPTIONS.getText();
        final String providersPerVersionTable = providersPerVersionTable();
        final String totalProviders = "\nDatafaker comes with a total of " + subTypes.size() + " data providers:" + "\n\n";

        writer.write(header);
        writer.write(groupDescriptions);
        writer.write(providersPerVersionTable);
        writer.write(totalProviders);
        writer.write(Column.generateHeaderRow(' '));
        writer.write(Column.generateEmptyRow('-'));
    }

    private String providersPerVersionTable() {
        Map<String, Integer> providersPerVersion = extractProvidersPerVersion();

        StringBuilder sb = new StringBuilder()
            .append("\nNumber of providers per Datafaker version\n")
            .append("\n| Version | Number of new providers | Total number of providers |")
            .append("\n|---------|-------------------------|---------------------------|\n");

        int cumulativeCountOfProvidersPerVersion = 0;
        for (Map.Entry<String, Integer> entry : providersPerVersion.entrySet()) {
            cumulativeCountOfProvidersPerVersion += entry.getValue();
            sb.append("| ").append(entry.getKey()).append(" | ")
                .append(entry.getValue().toString()).append(" | ")
                .append(cumulativeCountOfProvidersPerVersion).append(" |\n");
        }

        return sb.toString();
    }

    /**
     * Searching through all providers {@link subTypes}, getting since tag, and building
     * {@link TreeMap} with count of each since tag (version).
     * @return {@link TreeMap} with count of providers per each version
     */
    private Map<String, Integer> extractProvidersPerVersion() {
        Map<String, Integer> providersPerVersion = new TreeMap<>(Comparator.naturalOrder());

        for (Class<?> clazz : subTypes) {
            String groupName = extractGroupName(clazz);
            String comment = extractCommentFromJavadoc("src/main/java/net/datafaker/providers/" + groupName + "/" + clazz.getSimpleName() + ".java");
            String sinceTag = Column.SINCE.getValue(comment);

            if (!providersPerVersion.containsKey(sinceTag)) {
                providersPerVersion.put(sinceTag, 0);
            }
            providersPerVersion.computeIfPresent(sinceTag, (key, value) -> value + 1);
        }

        return providersPerVersion;
    }

    private String formatGroupName(String groupName) {
        return Character.toUpperCase(groupName.charAt(0)) + groupName.substring(1);
    }

    private enum Column {
        NAME("Name", 93),
        DESCRIPTION("Description", 132, Pattern.compile(".* \\* ([A-Z].+)"), comment -> comment.group(1).trim()),
        GROUP("Group", 50),
        SINCE("Since", 7, Pattern.compile("@since\\s+\\d\\.\\d+\\.\\d"), comment -> comment.group().substring("@since".length()).trim());
        private final String columnName;
        private final int length;
        private final Pattern pattern2extract;
        private final Function<Matcher, String> extractor;

        Column(String columnName, int length, Pattern pattern2extract, Function<Matcher, String> extractor) {
            this.columnName = columnName;
            this.length = length;
            this.pattern2extract = pattern2extract;
            this.extractor = extractor;
        }

        Column(String columnName, int length) {
            this(columnName, length, null, null);
        }

        public static String generateEmptyRow(char padSymbol) {
            return generateBaseHeaderRow(padSymbol, i -> "");
        }

        public static String generateHeaderRow(char padSymbol) {
            return generateBaseHeaderRow(padSymbol, i -> values()[i].columnName);
        }

        private static String generateBaseHeaderRow(char padSymbol, Function<Integer, String> int2Name) {
            StringBuilder sb = new StringBuilder();
            sb.append("|");
            for (int i = 0; i < values().length; i++) {
                sb.append(generateColumn(int2Name.apply(i), padSymbol, values()[i].length));
                sb.append("|");
            }
            return sb.append("\n").toString();
        }

        public static String generateRow(char padSymbol, String clazzName, String javaDocComment, String groupName) {
            StringBuilder sb = new StringBuilder();
            sb.append("|").append(generateColumn(getName(clazzName, groupName.toLowerCase(Locale.ROOT)), padSymbol, NAME.length)).append("|");
            for (int i = 1; i < values().length; i++) {
                if (values()[i] == GROUP) {
                    sb.append(generateColumn(groupName, padSymbol, GROUP.length)).append("|");
                    continue;
                }
                sb.append(generateColumn(values()[i].getValue(javaDocComment), padSymbol, values()[i].length));
                sb.append("|");
            }
            return sb.append("\n").toString();
        }

        public static String generateColumn(String name, char padSymbol, int length) {
            if (name.length() >= length) return name;
            return padSymbol + name + Strings.repeat(String.valueOf(padSymbol), length - name.length() - 1);
        }

        public String getValue(String javaDocComment) {
            Matcher comment = pattern2extract.matcher(javaDocComment);

            if (comment.find()) {
                return extractor.apply(comment);
            }
            return "";
        }

        public static String getName(String clazzName, String groupName) {
            return "[" + addSpaceBetweenNameOfProvider(clazzName) + "](https://javadoc.io/doc/net.datafaker/datafaker/latest/net/datafaker/providers/" + groupName + "/" + clazzName + ".html)";
        }

        private static String addSpaceBetweenNameOfProvider(String providerName) {
            if (providerName.isEmpty()) {
                throw new IllegalArgumentException("Supplied provider's name is empty!");
            }

            StringBuilder providerBuilder = new StringBuilder();
            providerBuilder.append(providerName.charAt(0));

            for (int i = 1; i < providerName.length(); i++) {
                if (Character.isUpperCase(providerName.charAt(i)) && Character.isLowerCase(providerName.charAt(i - 1))) {
                    providerBuilder.append(' ');
                }
                providerBuilder.append(providerName.charAt(i));
            }
            return providerBuilder.toString();
        }
    }

    private enum TextBlock {
        HEADER("# Fake Data Providers\n"),
        GROUP_DESCRIPTIONS("""

            #### Provider groups:
            - Base (Providers of everyday data)
            - Entertainment (Providers for movies, shows, books)
            - Food (Providers for different types of food)
            - Sport (Providers for different types of sport)
            - Videogame (Video game providers)
            """);

        private final String text;

        TextBlock(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

}
