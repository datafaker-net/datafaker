package net.datafaker.script;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.comments.CommentsCollection;
import com.github.javaparser.ast.comments.JavadocComment;
import com.google.common.base.Strings;
import net.datafaker.AbstractProvider;
import org.reflections.Reflections;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.reflections.scanners.Scanners.SubTypes;

public class ProvidersDocsGenerator {
    private static final Pattern pattern = Pattern.compile("\\d\\.\\d+\\.\\d");
    private final JavaParser parser = new JavaParser();

    // Specify destination of 'providers.md' file
    private final String destinationPlaceOfProvidersFile = "src/test/java/net/datafaker/script/providers.md";
    private final Reflections reflections = new Reflections("net.datafaker");
    private final Comparator<Class<?>> providersComparatorBySimpleName = Comparator
        .comparing(Class::getSimpleName);
    private final Set<Class<?>> subTypes = new TreeSet<>(providersComparatorBySimpleName);

    // Exclude non-providers from generation
    private final Set<String> providersToExcludeFromGeneration = new HashSet<>(Arrays.asList("CustomFakerTest", "InsectFromFile", "Insect"));
    private final int nameColLength = 93;
    private final int descColLength = 132;


    public static void main(String[] args) {
        ProvidersDocsGenerator providersDocsGenerator = new ProvidersDocsGenerator();
        providersDocsGenerator.constructHeaderInProvidersFile();
        providersDocsGenerator.generateProvidersDocs();
    }

    public void generateProvidersDocs() {
        fillTreeSet();

        Set<String> fakersWithoutSinceTag = new HashSet<>();
        for (Class<?> clazz : subTypes) {
            String name = clazz.getSimpleName();

            if (providersToExcludeFromGeneration.contains(name)) {
                continue;
            }

            String tag;
            try {
                tag = extractTagFromJavadoc("src/main/java/net/datafaker/" + name + ".java", fakersWithoutSinceTag);
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Error in supplied path to provider", e);
            }

            String sinceTag = matchSinceTag(tag);
            writeProviderToTable(name, sinceTag);
        }
        System.out.println("Providers without '@since' tag: " + fakersWithoutSinceTag);
    }

    /**
     * The implementation assumes that the JavaDoc with the '@since' tag
     * will always be at the very beginning of a class.
     *
     * @param filePath                              Path to the Faker(provider) to be searched
     * @param fakersWithoutSinceTag                 List in which add Faker's names that don't have '@since' tag
     * @return Entire first JavaDoc in the class
     * @throws FileNotFoundException
     */
    private String extractTagFromJavadoc(String filePath, Set<String> fakersWithoutSinceTag) throws FileNotFoundException {
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
    }
    public String generateColumn(String name, char padSymbol, int length) {
        if (name.length() >= length) return name;
        return padSymbol + name + Strings.repeat(String.valueOf(padSymbol), length - name.length() - 1);
    }
    /**
     * Writes header and table header to new 'providers.md'
     *
     * @see #writeToFile(String)
     */
    private void constructHeaderInProvidersFile() {
        final int sinceColLength = 7;
        final String header = "# Fake Data Providers\n" + "\n" + "Datafaker comes with the following list of data providers:" + "\n";
        final String tableHeader = "\n|" + generateColumn("Name", ' ', nameColLength) + "|" + generateColumn("Description", ' ', descColLength) + "|" + generateColumn("Since", ' ', sinceColLength) + "|\n";
        final String tableHeaderDelimiter = "|" + generateColumn("", '-', nameColLength) + "|" + generateColumn("", '-', descColLength)  + "|" + generateColumn("", '-', sinceColLength) + "|";

        try {
            writeToFile(header);
            writeToFile(tableHeader);
            writeToFile(tableHeaderDelimiter);
        } catch (IOException e) {
            throw new RuntimeException("Error on 'providers.md' file creation", e);
        }
    }

    private void writeProviderToTable(final String providerName, final String sinceTag) {
        String nameEntry = "\n|" + generateColumn("[" + addSpaceBetweenNameOfProvider(providerName) + "]" +
            "({{ datafaker.javadoc }}/" + providerName + ".html)", ' ', nameColLength) + "|";
        final String providerDescriptionSectionEntry = Strings.repeat(" ", descColLength) ;
        // Format of `@since tag` should be '#.#.#'
        String providerSinceTagSectionEntry = "|" + " " + sinceTag + " " + "|";
        try {
            writeToFile(nameEntry + providerDescriptionSectionEntry + providerSinceTagSectionEntry);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * When generating new file, the old one should be removed, because new data will be written in existing one.
     *
     * @param dataToWrite
     * @throws IOException
     */
    private void writeToFile(String dataToWrite) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(destinationPlaceOfProvidersFile, true))) {
            writer.write(dataToWrite);
        }
    }

    /**
     * Gets tag from JavaDoc comment
     *
     * @param javaDocComment            JavaDoc Comment which contains tag
     * @return tag in format "#.#.#". # - number
     */
    private String matchSinceTag(String javaDocComment) {
        Matcher comment = pattern.matcher(javaDocComment);

        if (comment.find()) {
            return comment.group();
        }
        return "";
    }

    /**
     * Fills <code>TreeSet<code/> with providers as Class<?>
     */
    private void fillTreeSet() {
        subTypes.addAll(reflections.get(SubTypes.of(AbstractProvider.class).asClass()));
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
