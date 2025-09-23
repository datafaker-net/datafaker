package net.datafaker.providers.base;

import net.datafaker.Faker;
import net.datafaker.providers.base.Internet.PortRange;
import net.datafaker.service.FakeValuesService;
import org.apache.commons.validator.routines.EmailValidator;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static net.datafaker.providers.base.Internet.PortRange.RegisteredPorts;
import static net.datafaker.providers.base.Internet.PortRange.WellKnownPorts;
import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class InternetTest {

    public static final Pattern IPV6_HOST_ADDRESS = Pattern.compile("[0-9a-fA-F]{1,4}(:([0-9a-fA-F]{1,4})){1,7}");
    private final Faker faker = new Faker();

    @RepeatedTest(10)
    @SuppressWarnings("removal")
    void testUsername() {
        assertThat(faker.internet().username()).matches("^(\\w+)\\.(\\w+)$");
    }

    @RepeatedTest(10)
    void emailSubject() {
        assertThat(faker.internet().emailSubject()).isNotBlank();
    }

    @ParameterizedTest
    @MethodSource("userNameWithSpacesProvider")
    @SuppressWarnings("removal")
    void testUsernameWithSpaces(String firstName, String lastName, String expected) {
        Locale locale = new Locale("TR");
        Name name = mock();
        doReturn(firstName).when(name).firstName();
        doReturn(lastName).when(name).lastName();

        BaseFaker mockedFaker = new BaseFaker(locale) {
            @Override
            public Name name() {
                return name;
            }
        };

        assertThat(mockedFaker.internet().username())
            .isEqualTo(expected);
    }

    private static Stream<Arguments> userNameWithSpacesProvider() {
        return Stream.of(
            Arguments.of("Jin Quan", "D'Artagnan", "jinquan.dartagnan"),
            Arguments.of("Ivan Ivanov", "Другой Язык", "ıvanıvanov.другойязык")
        );
    }

    @Test
    void testEmailAddress() {
        String emailAddress = faker.internet().emailAddress();
        assertThat(EmailValidator.getInstance().isValid(emailAddress)).isTrue();
    }

    @Test
    @SuppressWarnings("removal")
    void testEmailAddressWithNameParameter() {
        String username = faker.internet().username();
        String emailAddress = faker.internet().emailAddress(username);
        assertThat(emailAddress).startsWith(username + "@");
        assertThat(EmailValidator.getInstance().isValid(emailAddress)).isTrue();

        emailAddress = faker.internet().emailAddress("Hal");
        assertThat(emailAddress).startsWith("hal@");
        assertThat(EmailValidator.getInstance().isValid(emailAddress)).isTrue();

        emailAddress = faker.internet().emailAddress("T-800");
        assertThat(emailAddress).startsWith("t800@");
        assertThat(EmailValidator.getInstance().isValid(emailAddress)).isTrue();

        emailAddress = faker.internet().emailAddress("John McClane");
        assertThat(emailAddress).startsWith("john.mcclane@");
        assertThat(EmailValidator.getInstance().isValid(emailAddress)).isTrue();

        emailAddress = faker.internet().emailAddress("Tom Marvolo Riddle");
        assertThat(emailAddress).startsWith("tom.riddle@");
        assertThat(EmailValidator.getInstance().isValid(emailAddress)).isTrue();

        emailAddress = faker.internet().emailAddress("Dr. Emmett Brown");
        assertThat(emailAddress).startsWith("emmett.brown@");
        assertThat(EmailValidator.getInstance().isValid(emailAddress)).isTrue();

        emailAddress = faker.internet().emailAddress("Dr. Henry Indiana Jones Jr.");
        assertThat(emailAddress).startsWith("henry.jones@");
        assertThat(EmailValidator.getInstance().isValid(emailAddress)).isTrue();

        emailAddress = faker.internet().emailAddress("Jeanne d'Arc");
        assertThat(emailAddress).startsWith("jeanne.darc@");
        assertThat(EmailValidator.getInstance().isValid(emailAddress)).isTrue();

        emailAddress = faker.internet().emailAddress("Jeanne d’Arc");
        assertThat(emailAddress).startsWith("jeanne.darc@");
        assertThat(EmailValidator.getInstance().isValid(emailAddress)).isTrue();
    }

    @Test
    void testEmailAddressTypeSafety() {
        Faker spyedFaker = spy();
        FakeValuesService mockedFakeValuesService = mock();

        when(spyedFaker.fakeValuesService()).thenReturn(mockedFakeValuesService);
        when(mockedFakeValuesService.fetchObject("name.prefix", spyedFaker.getContext()))
            .thenReturn(Collections.emptySet());
        when(mockedFakeValuesService.fetchObject("name.suffix", spyedFaker.getContext()))
            .thenReturn(Collections.emptySet());

        String emailAddress = spyedFaker.internet().emailAddress("John McClane");
        assertThat(emailAddress).startsWith("john.mcclane@");
        assertThat(EmailValidator.getInstance().isValid(emailAddress)).isTrue();

        List<Integer> wrongListType = new ArrayList<>();
        wrongListType.add(1);

        when(mockedFakeValuesService.fetchObject("name.prefix", spyedFaker.getContext()))
            .thenReturn(wrongListType);
        when(mockedFakeValuesService.fetchObject("name.suffix", spyedFaker.getContext()))
            .thenReturn(wrongListType);

        emailAddress = spyedFaker.internet().emailAddress("John McClane");
        assertThat(emailAddress).startsWith("john.mcclane@");
        assertThat(EmailValidator.getInstance().isValid(emailAddress)).isTrue();
    }

    @Test
    void testSafeEmailAddress() {
        List<String> emails = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String emailAddress = faker.internet().safeEmailAddress();
            assertThat(EmailValidator.getInstance().isValid(emailAddress)).isTrue();
            emails.add(emailAddress);
        }
        final String safeDomain = faker.internet().resolve("internet.safe_email");

        assertThat(emails.stream().filter(t -> t.endsWith("@" + safeDomain)).collect(Collectors.toList()))
            .isNotEmpty();
    }

    @Test
    void testSafeEmailAddressWithNameParameter() {
        List<String> emails = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String emailAddress = faker.internet().safeEmailAddress("John McClane");
            assertThat(emailAddress).startsWith("john.mcclane@");
            assertThat(EmailValidator.getInstance().isValid(emailAddress)).isTrue();
            emails.add(emailAddress);
        }
        final String safeDomain = faker.internet().resolve("internet.safe_email");

        assertThat(emails.stream().filter(t -> t.endsWith("@" + safeDomain)).collect(Collectors.toList()))
            .isNotEmpty();
    }

    @Test
    void testEmailAddressDoesNotIncludeAccentsInTheLocalPart() {
        String emailAddress = faker.internet().emailAddress("áéíóú");
        assertThat(emailAddress).startsWith("aeiou@");
    }

    @Test
    void testSafeEmailAddressDoesNotIncludeAccentsInTheLocalPart() {
        String emailAddress = faker.internet().safeEmailAddress("áéíóú");
        assertThat(emailAddress).startsWith("aeiou@");
    }

    @Test
    void testWebdomain() {
        assertThat(faker.internet().webdomain()).matches("www\\.[\\w-]+\\.\\w+");
    }

    @RepeatedTest(10)
    void testUrl() {
        // This test assumes that java.net.URL has better validation than we can come up with in
        // regex.
        String url = faker.internet().url();
        assertDoesNotThrow(() -> new URL(url));
    }

    @Test
    void testImage() {
        String imageUrl = faker.internet().image();
        assertThat(imageUrl).matches("^https://picsum\\.photos/\\d{1,4}/\\d{1,4}$");
    }

    @Test
    void testDomainName() {
        assertThat(faker.internet().domainName()).matches("[a-z]+\\.\\w{2,4}");
    }

    @Test
    void testDomainWord() {
        assertThat(faker.internet().domainWord()).matches("[a-z]+");
    }

    @Test
    void testDomainSuffix() {
        assertThat(faker.internet().domainSuffix()).matches("\\w{2,4}");
    }

    @Test
    void testImageWithExplicitParams() {
        String imageUrl = faker.internet().image(800, 600, "lorem");
        assertThat(imageUrl).matches("^https://picsum\\.photos/seed/lorem/800/600$");
    }

    @Test
    void testHttpMethod() {
        assertThat(faker.internet().httpMethod()).isNotEmpty();
    }

    @Test
    @SuppressWarnings("removal")
    void testPassword() {
        assertThat(faker.internet().password()).matches("[a-z\\d]{8,16}");
    }

    @Test
    @SuppressWarnings("removal")
    void testPasswordWithFixedLength() {
        String password = new BaseFaker().internet().password(32, 32, true, true, true);
        assertThat(password).hasSize(32);
    }

    @Test
    @SuppressWarnings("removal")
    void testPasswordIncludeDigit() {
        assertThat(faker.internet().password()).matches("[a-z\\d]{8,16}");
        assertThat(faker.internet().password(false)).matches("[a-z]{8,16}");
    }

    @Test
    @SuppressWarnings("removal")
    void testPasswordMinLengthMaxLength() {
        assertThat(faker.internet().password(10, 25)).matches("[a-z\\d]{10,25}");
    }

    @Test
    @SuppressWarnings("removal")
    void testPasswordMinLengthMaxLengthIncludeUpperCase() {
        assertThat(faker.internet().password(1, 2, false)).matches("[a-z\\d]{1,2}");
        assertThat(faker.internet().password(10, 25, true)).matches("[a-zA-Z\\d]{10,25}");
    }

    @Test
    @SuppressWarnings("removal")
    void testPasswordMinLengthMaxLengthIncludeUpperCaseIncludeSpecial() {
        assertThat(faker.internet().password(10, 25, false, false)).matches("[a-z\\d]{10,25}");
        assertThat(faker.internet().password(10, 25, false, true)).matches("[a-z\\d!@#$%^&*]{10,25}");
        assertThat(faker.internet().password(10, 25, true, true)).matches("[a-zA-Z\\d!@#$%^&*]{10,25}");
    }

    @RepeatedTest(10)
    void testPort() {
        assertThat(faker.internet().port()).isBetween(0, 65535);
    }

    @RepeatedTest(10)
    void portWithinGivenBounds() {
        assertThat(faker.internet().port(0, 1)).isBetween(0, 1);
        assertThat(faker.internet().port(100, 200)).isBetween(100, 200);
        assertThat(faker.internet().port(1000, 1000)).isEqualTo(1000);
        assertThat(faker.internet().port(65535, 65535)).isEqualTo(65535);
    }

    @RepeatedTest(10)
    void portWithinGivenRange() {
        assertThat(faker.internet().port(WellKnownPorts)).isBetween(0, 1023);
        assertThat(faker.internet().port(RegisteredPorts)).isBetween(1024, 49151);
        assertThat(faker.internet().port(PortRange.DynamicPrivatePorts)).isBetween(49152, 65535);
    }

    @Test
    void portWithinGivenRange_validation() {
        assertThatThrownBy(() -> faker.internet().port(-1, 100))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Port number -1 cannot be less than 0");

        assertThatThrownBy(() -> faker.internet().port(100, 99))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Min (100) > Max (99)");

        assertThatThrownBy(() -> faker.internet().port(65535, 65536))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Port number 65536 cannot be greater than 65535");
    }

    @Test
    @SuppressWarnings("removal")
    void shouldGenerateAPasswordWithMinAndMaxLength() {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            results.add(faker.internet().password(1, 10));
        }

        final List<String> min = results.stream().filter(x -> x.length() == 1).toList();
        final List<String> max = results.stream().filter(x -> x.length() == 10).toList();

        assertThat(min.size()).isPositive();
        assertThat(max.size()).isPositive();
    }

    @Test
    @SuppressWarnings("removal")
    void testPasswordMinLengthMaxLengthIncludeUpperCaseIncludeSpecialIncludeDigit() {
        assertThat(faker.internet().password(10, 25, false, false, false)).matches("[a-z]{10,25}");
        assertThat(faker.internet().password(10, 25, false, true, true)).matches("[a-z\\d!@#$%^&*]{10,25}");
        assertThat(faker.internet().password(10, 25, true, true, false)).matches("[a-zA-Z!@#$%^&*]{10,25}");
        assertThat(faker.internet().password(10, 25, true, true, true)).matches("[a-zA-Z\\d!@#$%^&*]{10,25}");
    }

    private Condition<String> getCharacterCondition(char c, int expectedCnt) {
        return new Condition<>(s -> {
            int cnt = 0;
            if (s.length() < expectedCnt) {
                return false;
            }
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == c) {
                    cnt++;
                }
                if (cnt > expectedCnt) {
                    return false;
                }
            }
            return cnt == expectedCnt;
        }, "Expect " + expectedCnt + " colons");
    }

    @Test
    void testMacAddress() {
        Condition<String> colon = getCharacterCondition(':', 5);
        assertThat(faker.internet().macAddress()).is(colon);
        assertThat(faker.internet().macAddress("")).is(colon);

        assertThat(faker.internet().macAddress("fa:fa:fa"))
            .startsWith("fa:fa:fa")
            .is(colon);

        assertThat(faker.internet().macAddress("01:02"))
            .startsWith("01:02")
            .is(colon);

        // loop through 1000 times just to 'run it through the wringer'
        for (int i = 0; i < 1000; i++) {
            assertThat(faker.internet().macAddress()).matches("[0-9a-fA-F]{2}(:([0-9a-fA-F]{1,4})){5}");
        }
    }

    @Test
    void testIpV4Address() {
        Condition<String> colon = getCharacterCondition('.', 3);
        assertThat(faker.internet().ipV4Address()).is(colon);
        for (int i = 0; i < 100; i++) {
            final String[] octets = faker.internet().getIpV4Address().getHostAddress().split("\\.");
            assertThat(parseInt(octets[0])).isBetween(0, 255);
            assertThat(parseInt(octets[1])).isBetween(0, 255);
            assertThat(parseInt(octets[2])).isBetween(0, 255);
            assertThat(parseInt(octets[3])).isBetween(0, 255);
        }
    }

    @Test
    void testIpV4Cidr() {
        assertThat(faker.internet().ipV4Cidr())
            .is(getCharacterCondition('.', 3))
            .is(getCharacterCondition('/', 1));

        for (int i = 0; i < 1000; i++) {
            assertThat(parseInt(faker.internet().ipV4Cidr().split("/")[1]))
                .isBetween(1, 32);
        }
    }

    @Test
    void testPrivateIpV4Address() {
        String tenDot = "^10\\..+";
        String oneTwoSeven = "^127\\..+";
        String oneSixNine = "^169\\.254\\..+";
        String oneNineTwo = "^192\\.168\\..+";
        String oneSevenTwo = "^172\\.(16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31)\\..+";

        for (int i = 0; i < 1000; i++) {
            String addr = faker.internet().getPrivateIpV4Address().getHostAddress();
            assertThat(addr).is(anyOf(
                new Condition<>(s -> s.matches(tenDot), "tenDot"),
                new Condition<>(s -> s.matches(oneTwoSeven), "oneTwoSeven"),
                new Condition<>(s -> s.matches(oneSixNine), "oneSixNine"),
                new Condition<>(s -> s.matches(oneNineTwo), "oneNineTwo"),
                new Condition<>(s -> s.matches(oneSevenTwo), "oneSevenTwo")
            ));
        }
    }

    @Test
    void testPublicIpV4Address() {
        String tenDot = "^10\\.";
        String oneTwoSeven = "^127\\.";
        String oneSixNine = "^169\\.254";
        String oneNineTwo = "^192\\.168\\.";
        String oneSevenTwo = "^172\\.(16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31)\\.";

        for (int i = 0; i < 1000; i++) {
            String addr = faker.internet().getPublicIpV4Address().getHostAddress();
            assertThat(addr).doesNotMatch(tenDot)
                .doesNotMatch(oneTwoSeven)
                .doesNotMatch(oneSixNine)
                .doesNotMatch(oneNineTwo)
                .doesNotMatch(oneSevenTwo);
        }
    }

    @Test
    void testIpV6() {
        assertThat(faker.internet().ipV6Address()).is(getCharacterCondition(':', 7));

        for (int i = 0; i < 1000; i++) {
            assertThat(faker.internet().getIpV6Address().getHostAddress()).matches(IPV6_HOST_ADDRESS);
        }
    }

    @Test
    void testIpV6Cidr() {
        assertThat(faker.internet().ipV6Cidr())
            .is(getCharacterCondition(':', 7))
            .is(getCharacterCondition('/', 1));

        for (int i = 0; i < 1000; i++) {
            assertThat(parseInt(faker.internet().ipV6Cidr().split("/")[1]))
                .isBetween(1, 128);
        }
    }

    @RepeatedTest(10)
    void testSlugWithParams() {
        assertThat(faker.internet().slug(List.of("a", "b"), "-")).matches("[a-zA-Z]+-[a-zA-Z]+");
    }

    @RepeatedTest(10)
    void testSlug() {
        assertThat(faker.internet().slug()).matches("[a-zA-Z]+_[a-zA-Z]+");
    }

    @Test
    void testUuidv3ConstantRandomSeed() {
        final int randomSeed = 42;
        // Two fakers, same random seed.
        final BaseFaker faker1 = new BaseFaker(new Random(randomSeed));
        final BaseFaker faker2 = new BaseFaker(new Random((randomSeed)));
        // Keep it simple and without loops, three examples should suffice to act as the general case.
        final String faker1Uuidv3First = faker1.internet().uuidv3();
        final String faker1Uuidv3Second = faker1.internet().uuidv3();
        final String faker1Uuidv3Third = faker1.internet().uuidv3();
        final String faker2Uuidv3First = faker2.internet().uuidv3();
        final String faker2Uuidv3Second = faker2.internet().uuidv3();
        final String faker2Uuidv3Third = faker2.internet().uuidv3();
        // Two different fakers with the same random seed should produce the same uuids.
        assertThat(faker1Uuidv3First).isEqualTo(faker2Uuidv3First);
        assertThat(faker1Uuidv3Second).isEqualTo(faker2Uuidv3Second);
        assertThat(faker1Uuidv3Third).isEqualTo(faker2Uuidv3Third);
    }

    @RepeatedTest(10)
    void testUuidv3() {
        assertThat(faker.internet().uuidv3()).matches("^[0-9a-f]{8}-[0-9a-f]{4}-3[0-9a-f]{3}-[0-9a-f]{4}-[0-9a-f]{12}$");
    }

    @RepeatedTest(10)
    void testUuid() {
        assertThat(faker.internet().uuid()).matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$");
    }

    @RepeatedTest(10)
    void testUuidv4() {
        assertThat(faker.internet().uuidv4()).matches("^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[0-9a-f]{4}-[0-9a-f]{12}$");
    }

    @RepeatedTest(10)
    void testUuidv7() {
        assertThat(faker.internet().uuidv7()).matches("^[0-9a-f]{8}-[0-9a-f]{4}-7[0-9a-f]{3}-[0-9a-f]{4}-[0-9a-f]{12}$");
    }

    @RepeatedTest(10)
    void testFarsiIDNs() {
        // in this case, we're just making sure Farsi doesn't blow up.
        // there have been issues with Farsi not being produced.
        final BaseFaker f = new BaseFaker(new Locale("fa"));
        assertThat(f.internet().domainName()).isNotEmpty();
        assertThat(f.internet().emailAddress()).isNotEmpty();
        assertThat(f.internet().safeEmailAddress()).isNotEmpty();
        assertThat(f.internet().webdomain()).isNotEmpty();
    }

    @Test
    void testUserAgent() {
        Internet.UserAgent[] agents = Internet.UserAgent.values();
        for (Internet.UserAgent agent : agents) {
            assertThat(faker.internet().userAgent(agent)).isNotEmpty();
        }

        //Test faker.internet().userAgentAny() for random user_agent retrieval.
        assertThat(faker.internet().userAgent()).isNotEmpty();
    }

    @Test
    void testBotUserAgent() {
        Internet.BotUserAgent[] agents = Internet.BotUserAgent.values();
        for (Internet.BotUserAgent agent : agents) {
            assertThat(faker.internet().botUserAgent(agent)).isNotEmpty();
        }

        //Test faker.internet().userAgentAny() for random user_agent retrieval.
        assertThat(faker.internet().botUserAgentAny()).isNotEmpty();
    }

    @Test
    void testSlugWithNull() {
        assertThat(faker.internet().slug(null, "_")).isNotNull();
    }
}
