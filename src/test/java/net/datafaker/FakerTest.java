package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;
import java.util.Locale;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FakerTest extends AbstractFakerTest {

    @Test
    public void examplifyUppercaseLetters() {
        assertThat(faker.examplify("ABC")).matches("[A-Z]{3}");
    }

    @Test
    public void examplifyLowercaseLetters() {
        assertThat(faker.examplify("abc")).matches("[a-z]{3}");
    }

    @Test
    public void examplifyNumbers() {
        assertThat(faker.examplify("489321")).matches("[0-9]{6}");
    }

    @Test
    public void examplifyMixed() {
        assertThat(faker.examplify("abc123ABC1zzz")).matches("[a-z]{3}[0-9]{3}[A-Z]{3}[0-9][a-z]{3}");
    }

    @Test
    public void examplifyWithSpacesAndSpecialCharacters() {
        assertThat(faker.examplify("The number 4!")).matches("[A-Z][a-z]{2} [a-z]{6} [0-9]!");
    }

    @Test
    public void bothifyShouldGenerateLettersAndNumbers() {
        assertThat(faker.bothify("????##@gmail.com")).matches("\\w{4}\\d{2}@gmail.com");
    }

    @Test
    public void letterifyShouldGenerateLetters() {
        assertThat(faker.bothify("????")).matches("\\w{4}");
    }

    @Test
    public void letterifyShouldGenerateUpperCaseLetters() {
        assertThat(faker.bothify("????", true)).matches("[A-Z]{4}");
    }

    @Test
    public void letterifyShouldLeaveNonSpecialCharactersAlone() {
        assertThat(faker.bothify("ABC????DEF")).matches("ABC\\w{4}DEF");
    }

    @Test
    public void numerifyShouldGenerateNumbers() {
        assertThat(faker.numerify("####")).matches("\\d{4}");
    }

    @Test
    public void numerifyShouldLeaveNonSpecialCharactersAlone() {
        assertThat(faker.numerify("####123")).matches("\\d{4}123");
    }

    @Test
    public void templatify() {
        assertThat(faker.templatify("12??34", '?', "тест", "test", "测试测试").length()).isEqualTo(12);
        assertThat(faker.templatify("12??34",
            Collections.singletonMap('1', new String[]{"тест", "test", "测试测试"})).length()).isEqualTo(9);
        assertThat(faker.templatify("12??34",
            Collections.singletonMap('1', new String[]{""})).length()).isEqualTo(5);
    }

    @Test
    public void regexifyShouldGenerateNumbers() {
        assertThat(faker.regexify("\\d")).matches("\\d");
    }

    @Test
    public void regexifyShouldGenerateLetters() {
        assertThat(faker.regexify("\\w")).matches("\\w");
    }

    @Test
    public void regexifyShouldGenerateAlternations() {
        assertThat(faker.regexify("(a|b)")).matches("([ab])");
    }

    @Test
    public void regexifyShouldGenerateBasicCharacterClasses() {
        assertThat(faker.regexify("(aeiou)")).matches("(aeiou)");
    }

    @Test
    public void regexifyShouldGenerateCharacterClassRanges() {
        assertThat(faker.regexify("[a-z]")).matches("[a-z]");
    }

    @Test
    public void regexifyShouldGenerateMultipleCharacterClassRanges() {
        assertThat(faker.regexify("[a-z1-9]")).matches("[a-z1-9]");
    }

    @Test
    public void regexifyShouldGenerateSingleCharacterQuantifiers() {
        assertThat(faker.regexify("a*b+c?")).matches("a*b+c?");
    }

    @Test
    public void regexifyShouldGenerateBracketsQuantifiers() {
        assertThat(faker.regexify("a{2}")).matches("a{2}");
    }

    @Test
    public void regexifyShouldGenerateMinMaxQuantifiers() {
        assertThat(faker.regexify("a{2,3}")).matches("a{2,3}");
    }

    @Test
    public void regexifyShouldGenerateBracketsQuantifiersOnBasicCharacterClasses() {
        assertThat(faker.regexify("[aeiou]{2,3}")).matches("[aeiou]{2,3}");
    }

    @Test
    public void regexifyShouldGenerateBracketsQuantifiersOnCharacterClassRanges() {
        assertThat(faker.regexify("[a-z]{2,3}")).matches("[a-z]{2,3}");
    }

    @Test
    public void regexifyShouldGenerateBracketsQuantifiersOnAlternations() {
        assertThat(faker.regexify("(a|b){2,3}")).matches("([ab]){2,3}");
    }

    @Test
    public void regexifyShouldGenerateEscapedCharacters() {
        assertThat(faker.regexify("\\.\\*\\?\\+")).matches("\\.\\*\\?\\+");
    }

    @Test
    public void badExpressionTooManyArgs() {
        assertThatThrownBy(() -> faker.expression("#{regexify 'a','a'}"))
            .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void badExpressionTooFewArgs() {
        assertThatThrownBy(() -> faker.expression("#{regexify}"))
            .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void badExpressionCouldntCoerce() {
        assertThatThrownBy(() -> faker.expression("#{number.number_between 'x','10'}"))
            .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void expression() {
        assertThat(faker.expression("#{options.option 'a','b','c','d'}")).matches("([abcd])");
        assertThat(faker.expression("#{options.option '12','345','89','54321'}")).matches("(12|345|89|54321)");
        assertThat(faker.expression("#{regexify '(a|b){2,3}'}")).matches("([ab]){2,3}");
        assertThat(faker.expression("#{regexify '\\.\\*\\?\\+'}")).matches("\\.\\*\\?\\+");
        assertThat(faker.expression("#{bothify '????','true'}")).matches("[A-Z]{4}");
        assertThat(faker.expression("#{bothify '????','false'}")).matches("[a-z]{4}");
        assertThat(faker.expression("#{letterify '????','true'}")).matches("[A-Z]{4}");
        assertThat(faker.expression("#{templatify '????','?','1','2','q','r'}")).matches("([12qr]){4}");
        assertThat(faker.expression("#{Name.first_name} #{Name.first_name} #{Name.last_name}")).matches("[a-zA-Z']+ [a-zA-Z']+ [a-zA-Z']+");
        assertThat(faker.expression("#{number.number_between '1','10'}")).matches("[1-9]");
        assertThat(faker.expression("#{color.name}")).matches("[a-z\\s]+");
        assertThat(faker.expression("#{date.past '15','SECONDS','dd/MM/yyyy hh:mm:ss'}"))
            .matches("[0-9]{2}/[0-9]{2}/[0-9]{4} [0-9]{2}:[0-9]{2}:[0-9]{2}");
        assertThat(faker.expression("#{date.birthday 'yy DDD hh:mm:ss'}"))
            .matches("[0-9]{2} [0-9]{3} [0-9]{2}:[0-9]{2}:[0-9]{2}");
    }

    @RepeatedTest(100)
    public void numberBetweenRepeated() {
        assertThat(faker.expression("#{number.number_between '1','10'}")).matches("[1-9]");
    }

    @Test
    public void regexifyShouldGenerateSameValueForFakerWithSameSeed() {
        long seed = 1L;
        String regex = "\\d";

        String firstResult = new Faker(new Random(seed)).regexify(regex);
        String secondResult = new Faker(new Random(seed)).regexify(regex);

        assertThat(secondResult).isEqualTo(firstResult);
    }

    @Test
    public void resolveShouldReturnValueThatExists() {
        assertThat(faker.resolve("address.city_prefix")).isNotEmpty();
    }

    @Test
    public void resolveShouldThrowExceptionWhenPropertyDoesntExist() {
        assertThatThrownBy(() -> faker.resolve("address.nothing"))
            .isInstanceOf(RuntimeException.class);
    }

    /*
    Test case for issue https://github.com/datafaker-net/datafaker/issues/87
     */
    @ParameterizedTest
    @ValueSource(strings = {"#{regexify '[a-z]{5}[A-Z]{5}'}", "#{Address.city}"})
    public void datafaker87(String expression) {
        int n = 10;
        int counter = 0;
        for (int i = 0; i < n; i++) {
            String expression1 = faker.expression(expression);
            String expression2 = faker.expression(expression);
            if (expression1.equals(expression2)) {
                counter++;
            }
        }

        assertThat(counter).isLessThan(n);
    }

    @Test
    public void fakerInstanceCanBeAcquiredViaUtilityMethods() {
        assertThat(Faker.instance()).isInstanceOf(Faker.class);
        assertThat(Faker.instance(Locale.CANADA)).isInstanceOf(Faker.class);
        assertThat(Faker.instance(new Random(1))).isInstanceOf(Faker.class);
        assertThat(Faker.instance(Locale.CHINA, new Random(2))).isInstanceOf(Faker.class);
    }
}
