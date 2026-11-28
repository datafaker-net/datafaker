import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Turns the code samples in the documentation into Java, Kotlin and Groovy sources that can be compiled and run.
 *
 * <p>Usage: {@code java ExtractDocSamples.java <docs dir> <output dir>}
 *
 * <p>Every tab group in the Markdown files ({@code === "Java"}, {@code === "Kotlin"}, {@code === "Groovy"}) must
 * contain all three languages, otherwise generation fails. Within a page, samples share declarations, so a class
 * defined in one sample can be used in the next one. A {@code <!-- doc-samples: new scope -->} comment starts a new
 * scope, for pages that define the same class twice.
 *
 * <p>Each scope becomes a {@code Harness} class per language. Declarations (classes, enums, methods) are kept as
 * they are; every other sample becomes its own method. Generated {@code Main} classes run all samples and exit with
 * status 1 if any of them throws.
 */
public class ExtractDocSamples {

    private static final List<String> LANGUAGES = List.of("Java", "Kotlin", "Groovy");
    private static final String SCOPE_MARKER = "<!-- doc-samples: new scope -->";
    private static final Pattern TAB = Pattern.compile(
        "=== \"(Java|Kotlin|Groovy)\"\\n\\n {4}```(java|kotlin|groovy)\\n(.*?)\\n {4}```\\n", Pattern.DOTALL);
    private static final Pattern FAKE_FOR_SCHEMA = Pattern.compile("@FakeForSchema\\(\"[^\"#]*#(\\w+)\"\\)");

    private static final Map<String, Pattern> DECLARATION = Map.of(
        "Java", Pattern.compile("^(public |private |protected |static |abstract |final class |class |enum |record |interface |@)"),
        "Kotlin", Pattern.compile("^(class |data class |enum class |object |interface |fun |@)"),
        "Groovy", Pattern.compile("^(class |enum |record |interface |static |@)"));

    record Sample(String page, int line, String code) {
        String location() {
            return page + ":" + line;
        }
    }

    static final class Scope {
        final String name;
        final Map<String, List<Sample>> declarations = new LinkedHashMap<>();
        final Map<String, List<Sample>> statements = new LinkedHashMap<>();

        Scope(String name) {
            this.name = name;
            for (String language : LANGUAGES) {
                declarations.put(language, new ArrayList<>());
                statements.put(language, new ArrayList<>());
            }
        }

        boolean isEmpty() {
            return declarations.values().stream().allMatch(List::isEmpty)
                && statements.values().stream().allMatch(List::isEmpty);
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: java ExtractDocSamples.java <docs dir> <output dir>");
            System.exit(2);
        }
        Path docs = Path.of(args[0]);
        Path out = Path.of(args[1]);

        List<Scope> scopes = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        try (Stream<Path> files = Files.list(docs)) {
            for (Path file : files.filter(f -> f.toString().endsWith(".md")).sorted().toList()) {
                scopes.addAll(readPage(file, errors));
            }
        }
        if (!errors.isEmpty()) {
            errors.forEach(e -> System.err.println("ERROR " + e));
            System.exit(1);
        }

        for (Scope scope : scopes) {
            write(out.resolve("java/javasamples/" + scope.name + "/Harness.java"), javaHarness(scope));
            write(out.resolve("kotlin/kotlinsamples/" + scope.name + "/Harness.kt"), kotlinHarness(scope));
            write(out.resolve("groovy/groovysamples/" + scope.name + "/Harness.groovy"), groovyHarness(scope));
        }
        List<String> names = scopes.stream().map(s -> s.name).toList();
        write(out.resolve("java/javasamples/Main.java"), javaMain(names));
        write(out.resolve("kotlin/kotlinsamples/Main.kt"), kotlinMain(names));
        write(out.resolve("groovy/groovysamples/Main.groovy"), groovyMain(names));

        long samples = scopes.stream()
            .mapToLong(s -> s.declarations.get("Java").size() + s.statements.get("Java").size()).sum();
        System.out.printf("Generated %d samples per language in %d scopes%n", samples, scopes.size());
    }

    // ---------------------------------------------------------------- reading

    static List<Scope> readPage(Path file, List<String> errors) throws IOException {
        String page = file.getFileName().toString();
        String base = page.replaceFirst("\\.md$", "").replaceAll("[^A-Za-z0-9]", "_");
        String text = Files.readString(file).replace("\r\n", "\n");

        List<Scope> scopes = new ArrayList<>();
        String[] parts = text.split(Pattern.quote(SCOPE_MARKER), -1);
        int offset = 0;
        for (int i = 0; i < parts.length; i++) {
            Scope scope = new Scope(parts.length == 1 ? base : base + "_" + (i + 1));
            readScope(page, text, parts[i], offset, scope, errors);
            if (!scope.isEmpty()) {
                scopes.add(scope);
            }
            offset += parts[i].length() + SCOPE_MARKER.length();
        }
        return scopes;
    }

    static void readScope(String page, String fullText, String text, int offset, Scope scope, List<String> errors) {
        Matcher m = TAB.matcher(text);
        Set<String> group = new TreeSet<>();
        int groupLine = -1;
        int previousEnd = -1;
        while (m.find()) {
            int line = lineOf(fullText, offset + m.start());
            boolean sameGroup = previousEnd >= 0 && text.substring(previousEnd, m.start()).isBlank();
            if (!sameGroup) {
                checkGroup(page, groupLine, group, errors);
                group.clear();
                groupLine = line;
            }
            String language = m.group(1);
            if (!group.add(language)) {
                errors.add(page + ":" + line + ": the " + language + " tab appears twice in one group");
            }
            addSample(scope, language, new Sample(page, line, clean(m.group(3))), errors);
            previousEnd = m.end();
        }
        checkGroup(page, groupLine, group, errors);
    }

    static void checkGroup(String page, int line, Set<String> group, List<String> errors) {
        if (group.isEmpty()) {
            return;
        }
        for (String language : LANGUAGES) {
            if (!group.contains(language)) {
                errors.add(page + ":" + line + ": this sample has no " + language + " version");
            }
        }
    }

    static int lineOf(String text, int index) {
        int line = 1;
        for (int i = 0; i < index; i++) {
            if (text.charAt(i) == '\n') {
                line++;
            }
        }
        return line;
    }

    /** Removes the tab indentation, import statements (the harness has its own) and "..." placeholder lines. */
    static String clean(String code) {
        StringBuilder sb = new StringBuilder();
        for (String line : code.split("\n", -1)) {
            String l = line.startsWith("    ") ? line.substring(4) : line;
            if (l.strip().equals("...") || l.startsWith("import ")) {
                continue;
            }
            sb.append(l).append('\n');
        }
        return sb.toString().strip();
    }

    /** Splits a sample into declarations (by brace matching) and the statements that remain. */
    static void addSample(Scope scope, String language, Sample sample, List<String> errors) {
        Pattern declaration = DECLARATION.get(language);
        List<String> lines = List.of(sample.code().split("\n", -1));
        StringBuilder decl = new StringBuilder();
        StringBuilder stmts = new StringBuilder();
        int depth = 0;
        boolean inDeclaration = false;
        for (String line : lines) {
            if (!inDeclaration && depth == 0 && declaration.matcher(line).find()) {
                inDeclaration = true;
            }
            if (inDeclaration) {
                decl.append(line).append('\n');
                depth += depthChange(line);
                boolean annotationOnly = line.strip().startsWith("@") && depth == 0;
                if (depth == 0 && !annotationOnly) {
                    inDeclaration = false;
                    decl.append('\n');
                }
            } else {
                stmts.append(line).append('\n');
                depth += depthChange(line);
            }
        }
        if (depth != 0) {
            errors.add(sample.location() + ": unbalanced brackets in the " + language + " sample");
        }
        if (!decl.toString().isBlank()) {
            scope.declarations.get(language).add(new Sample(sample.page(), sample.line(), decl.toString().strip()));
        }
        if (!stmts.toString().isBlank()) {
            scope.statements.get(language).add(new Sample(sample.page(), sample.line(), stmts.toString().strip()));
        }
    }

    /** Counts opening minus closing brackets, ignoring string and character literals and line comments. */
    static int depthChange(String line) {
        int change = 0;
        char quote = 0;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (quote != 0) {
                if (c == '\\') {
                    i++;
                } else if (c == quote) {
                    quote = 0;
                }
            } else if (c == '"' || c == '\'') {
                quote = c;
            } else if (c == '/' && i + 1 < line.length() && line.charAt(i + 1) == '/') {
                break;
            } else if (c == '{' || c == '(' || c == '[') {
                change++;
            } else if (c == '}' || c == ')' || c == ']') {
                change--;
            }
        }
        return change;
    }

    // ---------------------------------------------------------------- writing

    static void write(Path path, String content) throws IOException {
        Files.createDirectories(path.getParent());
        Files.writeString(path, content);
    }

    static String indent(String code, int spaces) {
        String pad = " ".repeat(spaces);
        StringBuilder sb = new StringBuilder();
        for (String line : code.split("\n", -1)) {
            sb.append(line.isEmpty() ? "" : pad + line).append('\n');
        }
        return sb.toString();
    }

    /** Points {@code @FakeForSchema("some.Class#method")} at the harness that holds the method. */
    static String fakeForSchema(String code, String owner) {
        return FAKE_FOR_SCHEMA.matcher(code).replaceAll("@FakeForSchema(\"" + owner + "#$1\")");
    }

    static String quoted(String s) {
        return "\"" + s.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    }

    private static final String JAVA_IMPORTS = """
        import net.datafaker.Faker;
        import net.datafaker.annotations.FakeForSchema;
        import net.datafaker.providers.base.*;
        import net.datafaker.sequence.FakeSequence;
        import net.datafaker.service.RandomService;
        import net.datafaker.service.WeightedRandomSelector;
        import net.datafaker.transformations.*;
        import net.datafaker.transformations.sql.*;
        import java.nio.file.Paths;
        import java.time.DayOfWeek;
        import java.time.LocalDate;
        import java.time.temporal.ChronoUnit;
        import java.util.*;
        import java.util.concurrent.TimeUnit;
        import java.util.function.Function;
        import java.util.function.Supplier;
        import java.util.stream.Stream;

        import static net.datafaker.providers.base.Text.DIGITS;
        import static net.datafaker.providers.base.Text.EN_UPPERCASE;
        import static net.datafaker.transformations.Field.compositeField;
        import static net.datafaker.transformations.Field.field;
        """;

    private static final String KOTLIN_IMPORTS = """
        import net.datafaker.Faker
        import net.datafaker.annotations.FakeForSchema
        import net.datafaker.providers.base.*
        import net.datafaker.providers.base.Text.DIGITS
        import net.datafaker.providers.base.Text.EN_UPPERCASE
        import net.datafaker.sequence.FakeSequence
        import net.datafaker.service.RandomService
        import net.datafaker.service.WeightedRandomSelector
        import net.datafaker.transformations.*
        import net.datafaker.transformations.Field.compositeField
        import net.datafaker.transformations.Field.field
        import net.datafaker.transformations.sql.*
        import java.nio.file.Paths
        import java.time.DayOfWeek
        import java.time.LocalDate
        import java.time.temporal.ChronoUnit
        import java.util.*
        import java.util.concurrent.TimeUnit
        import java.util.function.Function
        import java.util.function.Supplier
        import java.util.stream.Stream
        """;

    private static final String GROOVY_IMPORTS = """
        import net.datafaker.Faker
        import net.datafaker.annotations.FakeForSchema
        import net.datafaker.providers.base.*
        import net.datafaker.sequence.FakeSequence
        import net.datafaker.service.RandomService
        import net.datafaker.service.WeightedRandomSelector
        import net.datafaker.transformations.*
        import net.datafaker.transformations.sql.*
        import java.nio.file.Paths
        import java.time.DayOfWeek
        import java.time.LocalDate
        import java.time.temporal.ChronoUnit
        import java.util.concurrent.TimeUnit
        import java.util.function.Function
        import java.util.function.Supplier
        import java.util.stream.Stream

        import static net.datafaker.providers.base.Text.DIGITS
        import static net.datafaker.providers.base.Text.EN_UPPERCASE
        import static net.datafaker.transformations.Field.compositeField
        import static net.datafaker.transformations.Field.field
        """;

    static String javaHarness(Scope scope) {
        String pkg = "javasamples." + scope.name;
        StringBuilder sb = new StringBuilder("package " + pkg + ";\n\n" + JAVA_IMPORTS + "\n");
        sb.append("""
            @SuppressWarnings({"unused", "unchecked", "rawtypes"})
            public class Harness {
                // Variables that samples use without declaring them
                static Faker faker = new Faker();
                static String separator = " ; ";
                static int limit = 3;
                static String tableSchemaName = "public";
                static Schema<String, String> schema = Schema.of(field("name", () -> faker.name().firstName()));
                static CsvTransformer<String> transformer = CsvTransformer.<String>builder().build();

            """);
        for (Sample d : scope.declarations.get("Java")) {
            // Samples show top-level classes; in the harness they are nested, so they must be static
            String code = d.code().replaceFirst("(?m)^public class ", "public static class ")
                .replaceFirst("(?m)^class ", "static class ");
            sb.append("    // ").append(d.location()).append('\n')
                .append(indent(fakeForSchema(code, pkg + ".Harness"), 4)).append('\n');
        }
        List<Sample> statements = scope.statements.get("Java");
        for (int i = 0; i < statements.size(); i++) {
            sb.append("    // ").append(statements.get(i).location()).append('\n')
                .append("    static void sample").append(i).append("() throws Exception {\n")
                .append(indent(statements.get(i).code(), 8)).append("    }\n\n");
        }
        sb.append("    public static int runSamples() {\n        int failed = 0;\n");
        for (int i = 0; i < statements.size(); i++) {
            sb.append("        failed += Main.run(").append(quoted(statements.get(i).location()))
                .append(", Harness::sample").append(i).append(");\n");
        }
        sb.append("        return failed;\n    }\n}\n");
        return sb.toString().replace("Main.run(", "javasamples.Main.run(");
    }

    static String javaMain(List<String> scopes) {
        StringBuilder sb = new StringBuilder("""
            package javasamples;

            import java.io.OutputStream;
            import java.io.PrintStream;

            public class Main {
                public interface Sample {
                    void run() throws Exception;
                }

                public static int run(String location, Sample sample) {
                    PrintStream out = System.out;
                    System.setOut(new PrintStream(OutputStream.nullOutputStream()));
                    try {
                        sample.run();
                        System.setOut(out);
                        System.out.println("OK   Java   " + location);
                        return 0;
                    } catch (Throwable t) {
                        System.setOut(out);
                        System.out.println("FAIL Java   " + location + ": " + t);
                        t.printStackTrace(System.out);
                        return 1;
                    }
                }

                public static void main(String[] args) {
                    int failed = 0;
            """);
        for (String scope : scopes) {
            sb.append("        failed += javasamples.").append(scope).append(".Harness.runSamples();\n");
        }
        sb.append("""
                    if (failed > 0) {
                        System.out.println(failed + " Java sample(s) failed");
                        System.exit(1);
                    }
                }
            }
            """);
        return sb.toString();
    }

    static String kotlinHarness(Scope scope) {
        String pkg = "kotlinsamples." + scope.name;
        StringBuilder sb = new StringBuilder("@file:Suppress(\"UNUSED_VARIABLE\", \"UNUSED_EXPRESSION\", \"NAME_SHADOWING\")\n\n"
            + "package " + pkg + "\n\n" + KOTLIN_IMPORTS + "\n");
        sb.append("""
            // Variables that samples use without declaring them
            val faker = Faker()
            val separator = " ; "
            val limit = 3
            val tableSchemaName = "public"
            val schema: Schema<String, String> = Schema.of(field("name", Supplier { faker.name().firstName() }))
            val transformer = CsvTransformer.builder<String>().build()

            """);
        for (Sample d : scope.declarations.get("Kotlin")) {
            sb.append("// ").append(d.location()).append('\n')
                .append(fakeForSchema(d.code(), pkg + ".HarnessKt")).append("\n\n");
        }
        List<Sample> statements = scope.statements.get("Kotlin");
        for (int i = 0; i < statements.size(); i++) {
            sb.append("// ").append(statements.get(i).location()).append('\n')
                .append("fun sample").append(i).append("() {\n")
                .append(indent(statements.get(i).code(), 4)).append("}\n\n");
        }
        sb.append("fun runSamples(): Int {\n    var failed = 0\n");
        for (int i = 0; i < statements.size(); i++) {
            sb.append("    failed += kotlinsamples.run(").append(quoted(statements.get(i).location()))
                .append(", ::sample").append(i).append(")\n");
        }
        sb.append("    return failed\n}\n");
        return sb.toString();
    }

    static String kotlinMain(List<String> scopes) {
        StringBuilder sb = new StringBuilder("""
            package kotlinsamples

            import java.io.OutputStream
            import java.io.PrintStream
            import kotlin.system.exitProcess

            fun run(location: String, sample: () -> Unit): Int {
                val out = System.out
                System.setOut(PrintStream(OutputStream.nullOutputStream()))
                return try {
                    sample()
                    System.setOut(out)
                    println("OK   Kotlin " + location)
                    0
                } catch (t: Throwable) {
                    System.setOut(out)
                    println("FAIL Kotlin " + location + ": " + t)
                    t.printStackTrace(System.out)
                    1
                }
            }

            fun main() {
                var failed = 0
            """);
        for (String scope : scopes) {
            sb.append("    failed += kotlinsamples.").append(scope).append(".runSamples()\n");
        }
        sb.append("""
                if (failed > 0) {
                    println("$failed Kotlin sample(s) failed")
                    exitProcess(1)
                }
            }
            """);
        return sb.toString();
    }

    static String groovyHarness(Scope scope) {
        String pkg = "groovysamples." + scope.name;
        StringBuilder outside = new StringBuilder();
        StringBuilder inside = new StringBuilder();
        for (Sample d : scope.declarations.get("Groovy")) {
            String code = fakeForSchema(d.code(), pkg + ".Harness");
            if (code.startsWith("static ")) {
                inside.append("    // ").append(d.location()).append('\n').append(indent(code, 4)).append('\n');
            } else {
                outside.append("// ").append(d.location()).append('\n').append(code).append("\n\n");
            }
        }
        StringBuilder sb = new StringBuilder("package " + pkg + "\n\n" + GROOVY_IMPORTS + "\n");
        sb.append(outside);
        sb.append("""
            class Harness {
                // Variables that samples use without declaring them
                static Faker faker = new Faker()
                static String separator = " ; "
                static int limit = 3
                static String tableSchemaName = "public"
                static Schema<String, String> schema = Schema.of(field("name", { -> faker.name().firstName() }))
                static CsvTransformer<String> transformer = CsvTransformer.builder().build()

            """);
        sb.append(inside);
        List<Sample> statements = scope.statements.get("Groovy");
        for (int i = 0; i < statements.size(); i++) {
            sb.append("    // ").append(statements.get(i).location()).append('\n')
                .append("    static void sample").append(i).append("() {\n")
                .append(indent(statements.get(i).code(), 8)).append("    }\n\n");
        }
        sb.append("    static int runSamples() {\n        int failed = 0\n");
        for (int i = 0; i < statements.size(); i++) {
            sb.append("        failed += groovysamples.Main.run(").append(quoted(statements.get(i).location()))
                .append(", { -> sample").append(i).append("() })\n");
        }
        sb.append("        failed\n    }\n}\n");
        return sb.toString();
    }

    static String groovyMain(List<String> scopes) {
        StringBuilder sb = new StringBuilder("""
            package groovysamples

            class Main {
                static int run(String location, Closure sample) {
                    PrintStream out = System.out
                    System.setOut(new PrintStream(OutputStream.nullOutputStream()))
                    try {
                        sample()
                        System.setOut(out)
                        System.out.println("OK   Groovy " + location)
                        return 0
                    } catch (Throwable t) {
                        System.setOut(out)
                        System.out.println("FAIL Groovy " + location + ": " + t)
                        t.printStackTrace(System.out)
                        return 1
                    }
                }

                static void main(String[] args) {
                    int failed = 0
            """);
        for (String scope : scopes) {
            sb.append("        failed += groovysamples.").append(scope).append(".Harness.runSamples()\n");
        }
        sb.append("""
                    if (failed > 0) {
                        System.out.println(failed + " Groovy sample(s) failed")
                        System.exit(1)
                    }
                }
            }
            """);
        return sb.toString();
    }
}
