package net.datafaker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Locale;
import java.util.Random;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class FakerTest extends AbstractFakerTest {

    @Test
    public void examplifyUppercaseLetters() {
        assertThat(faker.examplify("ABC"), matchesRegularExpression("[A-Z]{3}"));
    }

    @Test
    public void examplifyLowercaseLetters() {
        assertThat(faker.examplify("abc"), matchesRegularExpression("[a-z]{3}"));
    }

    @Test
    public void examplifyNumbers() {
        assertThat(faker.examplify("489321"), matchesRegularExpression("[0-9]{6}"));
    }

    @Test
    public void examplifyMixed() {
        assertThat(faker.examplify("abc123ABC1zzz"), matchesRegularExpression("[a-z]{3}[0-9]{3}[A-Z]{3}[0-9][a-z]{3}"));
    }

    @Test
    public void examplifyWithSpacesAndSpecialCharacters() {
        assertThat(faker.examplify("The number 4!"), matchesRegularExpression("[A-Z][a-z]{2} [a-z]{6} [0-9]!"));
    }

    @Test
    public void bothifyShouldGenerateLettersAndNumbers() {
        assertThat(faker.bothify("????##@gmail.com"), matchesRegularExpression("\\w{4}\\d{2}@gmail.com"));
    }

    @Test
    public void letterifyShouldGenerateLetters() {
        assertThat(faker.bothify("????"), matchesRegularExpression("\\w{4}"));
    }

    @Test
    public void letterifyShouldGenerateUpperCaseLetters() {
        assertThat(faker.bothify("????", true), matchesRegularExpression("[A-Z]{4}"));
    }

    @Test
    public void letterifyShouldLeaveNonSpecialCharactersAlone() {
        assertThat(faker.bothify("ABC????DEF"), matchesRegularExpression("ABC\\w{4}DEF"));
    }

    @Test
    public void numerifyShouldGenerateNumbers() {
        assertThat(faker.numerify("####"), matchesRegularExpression("\\d{4}"));
    }

    @Test
    public void numerifyShouldLeaveNonSpecialCharactersAlone() {
        assertThat(faker.numerify("####123"), matchesRegularExpression("\\d{4}123"));
    }

    @Test
    public void templatify() {
        assertThat(faker.templatify("12??34", '?', "тест", "test", "测试测试").length(), equalTo(12));
        assertThat(faker.templatify("12??34",
                Collections.singletonMap('1', new String[]{"тест", "test", "测试测试"})).length(), equalTo(9));
        assertThat(faker.templatify("12??34",
                Collections.singletonMap('1', new String[]{""})).length(), equalTo(5));
    }

    @Test
    public void regexifyShouldGenerateNumbers() {
        assertThat(faker.regexify("\\d"), matchesRegularExpression("\\d"));
    }

    @Test
    public void regexifyShouldGenerateLetters() {
        assertThat(faker.regexify("\\w"), matchesRegularExpression("\\w"));
    }

    @Test
    public void regexifyShouldGenerateAlternations() {
        assertThat(faker.regexify("(a|b)"), matchesRegularExpression("(a|b)"));
    }

    @Test
    public void regexifyShouldGenerateBasicCharacterClasses() {
        assertThat(faker.regexify("(aeiou)"), matchesRegularExpression("(aeiou)"));
    }

    @Test
    public void regexifyShouldGenerateCharacterClassRanges() {
        assertThat(faker.regexify("[a-z]"), matchesRegularExpression("[a-z]"));
    }

    @Test
    public void regexifyShouldGenerateMultipleCharacterClassRanges() {
        assertThat(faker.regexify("[a-z1-9]"), matchesRegularExpression("[a-z1-9]"));
    }

    @Test
    public void regexifyShouldGenerateSingleCharacterQuantifiers() {
        assertThat(faker.regexify("a*b+c?"), matchesRegularExpression("a*b+c?"));
    }

    @Test
    public void regexifyShouldGenerateBracketsQuantifiers() {
        assertThat(faker.regexify("a{2}"), matchesRegularExpression("a{2}"));
    }

    @Test
    public void regexifyShouldGenerateMinMaxQuantifiers() {
        assertThat(faker.regexify("a{2,3}"), matchesRegularExpression("a{2,3}"));
    }

    @Test
    public void regexifyShouldGenerateBracketsQuantifiersOnBasicCharacterClasses() {
        assertThat(faker.regexify("[aeiou]{2,3}"), matchesRegularExpression("[aeiou]{2,3}"));
    }

    @Test
    public void regexifyShouldGenerateBracketsQuantifiersOnCharacterClassRanges() {
        assertThat(faker.regexify("[a-z]{2,3}"), matchesRegularExpression("[a-z]{2,3}"));
    }

    @Test
    public void regexifyShouldGenerateBracketsQuantifiersOnAlternations() {
        assertThat(faker.regexify("(a|b){2,3}"), matchesRegularExpression("(a|b){2,3}"));
    }

    @Test
    public void regexifyShouldGenerateEscapedCharacters() {
        assertThat(faker.regexify("\\.\\*\\?\\+"), matchesRegularExpression("\\.\\*\\?\\+"));
    }

    @Test
    public void badExpressionTooManyArgs() {
        Assertions.assertThrows(RuntimeException.class, () -> faker.expression("#{regexify 'a','a'}"));
    }

    @Test
    public void badExpressionTooFewArgs() {
        Assertions.assertThrows(RuntimeException.class, () -> faker.expression("#{regexify}"));
    }

    @Test
    public void badExpressionCouldntCoerce() {
        Assertions.assertThrows(RuntimeException.class, () -> faker.expression("#{number.number_between 'x','10'}"));
    }

    @Test
    public void expression() {
        assertThat(faker.expression("#{options.option 'a','b','c','d'}"), matchesRegularExpression("(a|b|c|d)"));
        assertThat(faker.expression("#{options.option '12','345','89','54321'}"), matchesRegularExpression("(12|345|89|54321)"));
        assertThat(faker.expression("#{regexify '(a|b){2,3}'}"), matchesRegularExpression("(a|b){2,3}"));
        assertThat(faker.expression("#{regexify '\\.\\*\\?\\+'}"), matchesRegularExpression("\\.\\*\\?\\+"));
        assertThat(faker.expression("#{bothify '????','true'}"), matchesRegularExpression("[A-Z]{4}"));
        assertThat(faker.expression("#{bothify '????','false'}"), matchesRegularExpression("[a-z]{4}"));
        assertThat(faker.expression("#{letterify '????','true'}"), matchesRegularExpression("[A-Z]{4}"));
        assertThat(faker.expression("#{templatify '????','?','1','2','q','r'}"), matchesRegularExpression("(1|2|q|r){4}"));
        assertThat(faker.expression("#{Name.first_name} #{Name.first_name} #{Name.last_name}"), matchesRegularExpression("[a-zA-Z']+ [a-zA-Z']+ [a-zA-Z']+"));
        assertThat(faker.expression("#{number.number_between '1','10'}"), matchesRegularExpression("[1-9]"));
        assertThat(faker.expression("#{color.name}"), matchesRegularExpression("[a-z\\s]+"));
        assertThat(faker.expression("#{date.past '15','SECONDS','dd/MM/yyyy hh:mm:ss'}"),
                matchesRegularExpression("[0-9]{2}/[0-9]{2}/[0-9]{4} [0-9]{2}:[0-9]{2}:[0-9]{2}"));
        assertThat(faker.expression("#{date.birthday 'yy DDD hh:mm:ss'}"),
                matchesRegularExpression("[0-9]{2} [0-9]{3} [0-9]{2}:[0-9]{2}:[0-9]{2}"));
    }

    @RepeatedTest(100)
    public void numberBetweenRepeated() {
        assertThat(faker.expression("#{number.number_between '1','10'}"), matchesRegularExpression("[1-9]"));
    }

    @Test
    public void regexifyShouldGenerateSameValueForFakerWithSameSeed() {
        long seed = 1L;
        String regex = "\\d";

        String firstResult = new Faker(new Random(seed)).regexify(regex);
        String secondResult = new Faker(new Random(seed)).regexify(regex);

        assertThat(secondResult, is(firstResult));
    }

    @Test
    public void resolveShouldReturnValueThatExists() {
        assertThat(faker.resolve("address.city_prefix"), not(is(emptyString())));
    }

    @Test
    public void resolveShouldThrowExceptionWhenPropertyDoesntExist() {
        Assertions.assertThrows(RuntimeException.class, () -> faker.resolve("address.nothing"));
    }

    @Test
    public void fakerInstanceCanBeAcquiredViaUtilityMethods() {
        assertThat(Faker.instance(), is(instanceOf(Faker.class)));
        assertThat(Faker.instance(Locale.CANADA), is(instanceOf(Faker.class)));
        assertThat(Faker.instance(new Random(1)), is(instanceOf(Faker.class)));
        assertThat(Faker.instance(Locale.CHINA, new Random(2)), is(instanceOf(Faker.class)));
    }
}