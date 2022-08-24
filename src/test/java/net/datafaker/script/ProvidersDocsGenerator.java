package net.datafaker.script;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.comments.CommentsCollection;
import com.github.javaparser.ast.comments.JavadocComment;
import com.google.common.base.Strings;
import net.datafaker.AbstractProvider;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.reflections.scanners.Scanners.SubTypes;

public class ProvidersDocsGenerator {
    private static final Pattern sincePattern = Pattern.compile("@since\\s+\\d\\.\\d+\\.\\d");
    private static final Pattern descriptionPatterns = Pattern.compile(".* \\* ([A-Z].+)");
    private final JavaParser parser = new JavaParser();

    // Specify destination of 'providers.md' file
    private static final String DESTINATION_PLACE_OF_PROVIDERS_FILE = "target/test-classes/providers.md";
    private final Reflections reflections = new Reflections("net.datafaker");
    private final Comparator<Class<?>> providersComparatorBySimpleName = Comparator
        .comparing(Class::getSimpleName);
    private final Set<Class<?>> subTypes = new TreeSet<>(providersComparatorBySimpleName);

    // Exclude non-providers from generation
    private final Set<String> providersToExcludeFromGeneration = new HashSet<>(Arrays.asList("CustomFakerTest", "InsectFromFile", "Insect"));

    // TODO: get rid of this
    private final int nameColLength = 93;
    private final int descColLength = 132;

    public static void main(String[] args) {
        ProvidersDocsGenerator providersDocsGenerator = new ProvidersDocsGenerator();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DESTINATION_PLACE_OF_PROVIDERS_FILE))) {
            providersDocsGenerator.constructHeaderInProvidersFile(writer);
            providersDocsGenerator.generateProvidersDocs(writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateProvidersDocs(BufferedWriter writer) {
        subTypes.addAll(reflections.get(SubTypes.of(AbstractProvider.class).asClass()));

        Set<String> fakersWithoutSinceTag = new HashSet<>();
        for (Class<?> clazz : subTypes) {
            String name = clazz.getSimpleName();

            if (providersToExcludeFromGeneration.contains(name)) {
                continue;
            }

            String comment =  extractCommentFromJavadoc("src/main/java/net/datafaker/" + name + ".java", fakersWithoutSinceTag);

            String sinceTag = extractSinceVersion(comment);
            String description = extractDescription(comment);
            writeProviderToTable(writer, name, sinceTag, description);
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
    private String extractCommentFromJavadoc(String filePath, Set<String> fakersWithoutSinceTag)  {
        try {
            final File file = new File(filePath);

            Optional<CommentsCollection> commentsCollection = parser.parse(file).getCommentsCollection();

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

    public String generateColumn(String name, char padSymbol, int length) {
        if (name.length() >= length) return name;
        return padSymbol + name + Strings.repeat(String.valueOf(padSymbol), length - name.length() - 1);
    }

    /**
     * Writes header and table header to new 'providers.md'
     */
    private void constructHeaderInProvidersFile(Writer writer) throws IOException {
        final int sinceColLength = 7;
        final String header = "# Fake Data Providers\n"
            + "\nDatafaker comes with the following list of data providers:" + "\n";
        final String tableHeader = "\n|"
            + generateColumn("Name", ' ', nameColLength) + "|"
            + generateColumn("Description", ' ', descColLength) + "|"
            + generateColumn("Since", ' ', sinceColLength) + "|\n";
        final String tableHeaderDelimiter = "|" + generateColumn("", '-', nameColLength) + "|"
            + generateColumn("", '-', descColLength) + "|"
            + generateColumn("", '-', sinceColLength) + "|";

        writeToFile(writer, header);
        writeToFile(writer, tableHeader);
        writeToFile(writer, tableHeaderDelimiter);
    }

    private void writeProviderToTable(Writer writer, String providerName, String sinceTag, String description) {
        String nameEntry = "\n|" + generateColumn("[" + addSpaceBetweenNameOfProvider(providerName) + "]" +
            "({{ datafaker.javadoc }}/" + providerName + ".html)", ' ', nameColLength) + "|";

        String providerDescriptionSectionEntry = description.isEmpty() ? " " : description;
        String providerSinceTagSectionEntry = "|" + " " + sinceTag + " " + "|";

        writeToFile(writer, nameEntry + providerDescriptionSectionEntry + providerSinceTagSectionEntry);
    }

    private void writeToFile(Writer writer, String dataToWrite)  {
        try {
            writer.write(dataToWrite);
            writer.flush();
        } catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Gets since version from JavaDoc comment
     *
     * @param javaDocComment JavaDoc Comment which contains tag
     * @return tag in format "#.#.#". # - number
     */
    private String extractSinceVersion(String javaDocComment) {
        Matcher comment = sincePattern.matcher(javaDocComment);

        if (comment.find()) {
            return comment.group().substring("@since".length()).trim();
        } else {
            return "";
        }
    }

    /**
     * Gets since version from JavaDoc comment
     *
     * @param javaDocComment JavaDoc Comment which contains tag
     * @return tag in format "#.#.#". # - number
     */
    private String extractDescription(String javaDocComment) {
        Matcher comment = descriptionPatterns.matcher(javaDocComment);

        if (comment.find()) {
            return comment.group(1).trim();
        } else {
            return "";
        }
    }

    private String addSpaceBetweenNameOfProvider(String providerName) {
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
