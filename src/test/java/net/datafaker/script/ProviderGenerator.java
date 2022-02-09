package net.datafaker.script;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.Set;

public class ProviderGenerator {


    @Test
    public void generateProvider() throws FileNotFoundException {
        File dir = new File("/Users/erikp/UserFiles/projects/datafaker/src/main/resources/en");

        File[] files = dir.listFiles((dir1, name) -> name.toLowerCase().endsWith("todo.yml"));

        System.out.println(files.length + " files");

        for (File file : files) {
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

        Set<String> strings = subject.keySet();

        System.out.println(strings);

        createCreator(file, key, strings);
        createTest(file, key, strings);

    }

    private void createCreator(File file, String key, Set<String> strings) {
        String className = toJavaConvention(file.getName().substring(0, file.getName().indexOf(".")));

        System.out.println("package net.datafaker;");
        System.out.println();
        System.out.println("public class " + className + " {");
        System.out.println();
        System.out.println("    private final Faker faker;");
        System.out.println();
        System.out.println("    protected " + className + "(Faker faker) {");
        System.out.println("        this.faker = faker;");
        System.out.println("    }");
        System.out.println();

        for (String string : strings) {
            String methodName = StringUtils.uncapitalize(toJavaConvention(string));

            System.out.println("    public String " + methodName + "() {\n" +
                    "        return faker.fakeValuesService().resolve(\"" + key + "." + string + "\", this, faker);\n" +
                    "    }");
            System.out.println();
        }

        System.out.println("}");
    }

    private void createTest(File file, String key, Set<String> strings) {
        String className = toJavaConvention(file.getName().substring(0, file.getName().indexOf(".")));
        // replace the first letter with a lowercase letter
        String methodName = StringUtils.uncapitalize(className);

        System.out.println("package net.datafaker;");
        System.out.println();
        System.out.println("import org.junit.Test;");
        System.out.println("import static org.hamcrest.MatcherAssert.assertThat;");
        System.out.println("import static org.hamcrest.Matchers.is;");
        System.out.println("import static org.hamcrest.Matchers.not;");
        System.out.println("import static org.hamcrest.Matchers.emptyOrNullString;");

        System.out.println();
        System.out.println("public class " + className + "Test extends AbstractFakerTest {");
        System.out.println();

        for (String string : strings) {
            String testMethodName = StringUtils.uncapitalize(toJavaConvention(string));

            System.out.println("    @Test");
            System.out.println("    public void " + testMethodName + "() {");
            System.out.println("        assertThat(faker." + methodName + "()." + string +"(), not(is(emptyOrNullString())));");
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
