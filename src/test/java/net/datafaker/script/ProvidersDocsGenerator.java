package net.datafaker.script;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.comments.CommentsCollection;
import com.github.javaparser.ast.comments.JavadocComment;
import com.google.common.base.Strings;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProvidersDocsGenerator {

    private static final Pattern pattern = Pattern.compile("\\d\\.\\d\\.\\d");
    private final JavaParser parser = new JavaParser();

    // Specify destination of 'providers.md' file
    private final String destinationPlaceOfProvidersFile = "src/test/java/net/datafaker/script/providers.md";

    // Need to update this list when adding a new class in 'src.main.java.net.datafaker' which is not Faker.
    // Store files with '.java' extension in order to avoid unnecessary transformation (substring '.java')
    private final Set<String> fakersToExcludeFromGeneration = new HashSet<>(Arrays.asList("Faker.java", "CreditCardType.java", "AbstractProvider.java", "FakeCollection.java"));

    public static void main(String[] args) {
        ProvidersDocsGenerator providersDocsGenerator = new ProvidersDocsGenerator();
        providersDocsGenerator.constructHeaderInProvidersFile();
        providersDocsGenerator.generateProvidersDocsFile("src/main/java/net/datafaker/");
    }

    public void generateProvidersDocsFile(String dirPath) {
        final File path = new File(dirPath);

        File[] files = path.listFiles();

        StringBuilder providersResultString = new StringBuilder();

        List<String> fakersWithoutSinceTag = new ArrayList<>();

        for (File file : files) {
            if (Files.isRegularFile(file.toPath())) {
                String nameOfFile = file.getName();

                if (fakersToExcludeFromGeneration.contains(nameOfFile)) {
                    continue;
                }

                String tag;
                try {
                    tag = extractTagFromJavadoc(dirPath + nameOfFile, fakersWithoutSinceTag);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                String fileNameWithoutExtension = nameOfFile.substring(0, nameOfFile.length() - 5);
                String sinceTag = matchSinceTag(tag);
                providersResultString.append(fileNameWithoutExtension).append(" ");

                writeProviderToTable(fileNameWithoutExtension, sinceTag);
            }
        }
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
    private String extractTagFromJavadoc(String filePath, List<String> fakersWithoutSinceTag) throws FileNotFoundException {
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

    /**
     * Writes header and table header to new 'providers.md'
     *
     * @see #writeToFile(String)
     */
    private void constructHeaderInProvidersFile() {
        final String header = "# Fake Data Providers\n" + "\n" + "Datafaker comes with the following list of data providers:" + "\n";
        final String tableHeader = "\n" + "| Name" + Strings.repeat(" ", 88) + "|" + " " + "Description" + Strings.repeat(" ", 120) + "|" + " " + "Since" + " " + "|" + "\n";
        final String tableHeaderDelimiter = "|" + Strings.repeat("-", 93) + "|" + Strings.repeat("-", 132) + "|" + Strings.repeat("-", 7) + "|";

        try {
            writeToFile(header);
            writeToFile(tableHeader);
            writeToFile(tableHeaderDelimiter);
        } catch (IOException e) {
            throw new RuntimeException("Error on 'providers.md' file creation", e);
        }
    }

    private void writeProviderToTable(final String providerName, final String sinceTag) {
        String inCompleteProviderEntry = "| " + "[" + addSpaceBetweenNameOfProvider(providerName) + "]" +
            "({{ datafaker.javadoc }}/" + providerName + ".html)";
        int lengthOfNameSection = inCompleteProviderEntry.length();
        // Number of chars in 'Name' section, including first '|'.
        // '|' in front of 'Description' section not included in length
        final int totalLengthOfNameSection = 94;
        int lengthOfNameSectionToFill = totalLengthOfNameSection - lengthOfNameSection;

        final String completeNameSection = "\n" + inCompleteProviderEntry + Strings.repeat(" ", lengthOfNameSectionToFill) + "|";

        final String providerDescriptionSectionEntry = Strings.repeat(" ", 132) ;

        // Format of `@since tag` should be '#.#.#'
        String providerSinceTagSectionEntry = "|" + " " + sinceTag + " " + "|";

        try {
            writeToFile(completeNameSection + providerDescriptionSectionEntry + providerSinceTagSectionEntry);
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

    private String addSpaceBetweenNameOfProvider(String providerName) {
        if (providerName.isEmpty()) {
            throw new IllegalArgumentException("Supplied provider's name is empty!");
        }

        StringBuilder providerBuilder = new StringBuilder();
        providerBuilder.append(providerName.charAt(0));

        for (int i = 1; i < providerName.length(); i++) {
            if (Character.isUpperCase(providerName.charAt(i)) && providerName.charAt(i - 1) != ' ') {
                providerBuilder.append(' ');
            }
            providerBuilder.append(providerName.charAt(i));
        }
        return providerBuilder.toString();
    }
}
