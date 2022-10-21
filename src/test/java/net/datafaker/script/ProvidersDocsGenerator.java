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
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
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
    private final Set<String> providersToExcludeFromGeneration = new HashSet<>(Arrays.asList("CustomFakerTest", "InsectFromFile", "Insect"));

    public static void main(String[] args) {
        ProvidersDocsGenerator providersDocsGenerator = new ProvidersDocsGenerator();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DESTINATION_PLACE_OF_PROVIDERS_FILE))) {
            providersDocsGenerator.constructHeaderInProvidersFile(writer);
            providersDocsGenerator.generateProvidersDocs(writer);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void generateProvidersDocs(BufferedWriter writer) throws IOException {
        subTypes.addAll(
            reflections.get(SubTypes.of(AbstractProvider.class).asClass())
                .stream()
                .filter(t -> !providersToExcludeFromGeneration.contains(t.getSimpleName()))
                .collect(Collectors.toSet()));

        Set<String> fakersWithoutSinceTag = new HashSet<>();
        for (Class<?> clazz : subTypes) {
            String name = clazz.getSimpleName();
            String packageName = clazz.getPackage().getName();
            // `packageName` should be such format: net.datafaker.providers.<groupName> (e.g. base, sport, movie)
            // And just splitting by '.' and getting groupName (e.g. base, sport, movie)
            String groupName = packageName.split("\\.")[3];
            String comment = extractCommentFromJavadoc("src/main/java/net/datafaker/providers/" + groupName + "/" + name + ".java", fakersWithoutSinceTag);
            writer.write(Column.generateRow(' ', name, comment, formatGroupName(groupName)));
        }
        System.out.println("Providers without '@since' tag: " + fakersWithoutSinceTag);
    }

    /**
     * The implementation assumes that the JavaDoc with the '@since' tag
     * will always be at the very beginning of a class.
     *
     * @param filePath              Path to the Faker(provider) to be searched
     * @param fakersWithoutSinceTag List in which add Faker's names that don't have '@since' tag
     * @return Entire first JavaDoc in the class
     */
    private String extractCommentFromJavadoc(String filePath, Set<String> fakersWithoutSinceTag) {
        try {
            final File file = new File(filePath);

            Optional<CommentsCollection> commentsCollection =
                parser.parse(file).getCommentsCollection();

            if (!commentsCollection.isPresent()) {
                fakersWithoutSinceTag.add(filePath);
                return "";
            }

            Optional<JavadocComment> javadocComments = commentsCollection.get().getJavadocComments()
                .stream()
                .findFirst();

            if (!javadocComments.isPresent()) {
                fakersWithoutSinceTag.add(filePath);
                return "";
            }

            String comment = javadocComments.get().getContent();
            boolean isComment = comment.contains("@since");

            if (!isComment) {
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
        final String header = "# Fake Data Providers\n"
            + "\nDatafaker comes with the following list of data providers:" + "\n\n";

        writer.write(header);
        writer.write(Column.generateHeaderRow(' '));
        writer.write(Column.generateEmptyRow('-'));
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
            sb.append("|").append(generateColumn(getName(clazzName), padSymbol, NAME.length)).append("|");
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

        public static String getName(String clazzName) {
            return "[" + addSpaceBetweenNameOfProvider(clazzName) + "]({{ datafaker.javadoc }}/" + clazzName + ".html)";
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
}
