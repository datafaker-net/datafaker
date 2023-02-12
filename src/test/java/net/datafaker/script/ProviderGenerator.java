package net.datafaker.script;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

class ProviderGenerator {

    @SuppressWarnings("FieldCanBeLocal")
    private final ProviderType providerType = ProviderType.SHOW;

    public static void main(String[] args) throws FileNotFoundException {
        new ProviderGenerator().generateProvider();
    }

    void generateProvider() throws FileNotFoundException {
        File dir = new File("src/main/resources/en");

        File[] files = dir.listFiles((dir1, name) -> name.toLowerCase().contains("cowboy_bebop.yml"));

        List<File> fileList = Arrays.asList(files);
        Collections.shuffle(fileList);
        List<File> filesToProcess = fileList.stream().limit(5).toList();

        System.out.println(files.length + " files");

        for (File file : filesToProcess) {
            final Map<String, Object> valuesMap = new Yaml().loadAs(new FileReader(file), Map.class);

            Map<String, Object> en = (Map<String, Object>) valuesMap.get("en");
            Map<String, Object> faker = (Map<String, Object>) en.get("faker");

            System.out.println(file);
            processFaker(file, faker);
        }
    }

    private void processFaker(File file, Map<String, Object> faker) {
        String key = (String) faker.keySet().toArray()[0];
        Map<String, Object> subject = (Map<String, Object>) faker.get(key);

        // Special case for games
        if (key.equals("games")) {
            String key2 = subject.keySet().iterator().next();
            subject = (Map<String, Object>) subject.get(key2);
            key = key + "." + key2;
        }

        Set<String> strings = subject.keySet();

        createCreator(file, key, strings, providerType);
        createTest(file, strings, providerType);
        createFakerRegistration(file);
    }

    private void createFakerRegistration(File file) {
        String className = toJavaConvention(file.getName().substring(0, file.getName().indexOf(".")));
        String methodName = StringUtils.uncapitalize(toJavaConvention(className));

        System.out.println();
        System.out.println("default " + className + " " + methodName + "() {");
        System.out.println("    return getProvider(" + className + ".class, " + className + "::new);");
        System.out.println("}");
    }

    private void createCreator(File file, String key, Set<String> strings, ProviderType providerType) {
        String className = toJavaConvention(file.getName().substring(0, file.getName().indexOf(".")));

        System.out.println("package " + providerType.getPackageName() + ";");
        System.out.println();
        System.out.println("import net.datafaker.providers.base.AbstractProvider;");
        System.out.println();
        System.out.println("/**");
        System.out.println(" * @since 1.8.0");
        System.out.println(" */");
        System.out.println("public class " + className + " extends AbstractProvider<" + providerType.getProviderRegistryName() + "> {");
        System.out.println();
        System.out.println("    protected " + className + "(" + providerType.getProviderRegistryName() + " faker) {");
        System.out.println("        super(faker);");
        System.out.println("    }");
        System.out.println();

        for (String string : strings) {
            String methodName = StringUtils.uncapitalize(toJavaConvention(string));

            System.out.println("    public String " + methodName + "() {\n" +
                "        return resolve(\"" + key + "." + string + "\");\n" +
                "    }");
            System.out.println();
        }

        System.out.println("}");
    }

    private void createTest(File file, Set<String> strings, ProviderType providerType) {
        String className = toJavaConvention(file.getName().substring(0, file.getName().indexOf(".")));
        // replace the first letter with a lowercase letter
        String methodName = StringUtils.uncapitalize(toJavaConvention(className));

        System.out.println("package " + providerType.getPackageName() + ";");
        System.out.println();
        System.out.println("import org.junit.jupiter.api.Test;");
        System.out.println("import static org.assertj.core.api.AssertionsForClassTypes.assertThat;");

        System.out.println();
        System.out.println("class " + className + "Test extends " + providerType.getTestSuperclassName() + " {");
        System.out.println();

        for (String string : strings) {
            String testMethodName = StringUtils.uncapitalize(toJavaConvention(string));

            System.out.println("    @Test");
            System.out.println("    void " + testMethodName + "() {");
            System.out.println("        assertThat(faker." + methodName + "()." + testMethodName + "()).isNotEmpty();");
            System.out.println("    }");
            System.out.println();
        }

        System.out.println("}");
    }

    private String toJavaConvention(String baseName) {

        // replace underscores with spaces
        String withoutUnderscore = baseName.replaceAll("_", " ");
        // for every word in the name, capitalize the first letter
        String capitalizedWords = WordUtils.capitalize(withoutUnderscore);
        // remove all spaces
        return capitalizedWords.replaceAll(" ", "");
    }
}

enum ProviderType {
    SHOW("EntertainmentProviders", "net.datafaker.providers.entertainment", "EntertainmentFakerTest"),
    VIDEO_GAME("VideoGameProviders", "net.datafaker.providers.videogame", "VideoGameFakerTest"),
    ;

    private final String providerRegistryName;
    private final String packageName;
    private final String testSuperclassName;

    ProviderType(String providerRegistryName, String packageName, String testSuperclassName) {
        this.providerRegistryName = providerRegistryName;
        this.packageName = packageName;
        this.testSuperclassName = testSuperclassName;
    }

    public String getProviderRegistryName() {
        return providerRegistryName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getTestSuperclassName() {
        return testSuperclassName;
    }
}
