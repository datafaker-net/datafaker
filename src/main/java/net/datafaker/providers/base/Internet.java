package net.datafaker.providers.base;

import net.datafaker.internal.helper.FakerIDN;
import net.datafaker.service.RandomService;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @since 0.8.0
 */
public class Internet extends AbstractProvider<BaseProviders> {
    private static final Pattern COLON = Pattern.compile(":");
    private static final List<String> HTTP_SCHEMES = List.of("http://", "https://");
    private static final int MIN_PORT_NUMBER = 0;
    private static final int MAX_PORT_NUMBER = 65535;

    protected Internet(BaseProviders faker) {
        super(faker);
    }

    /**
     * A lowercase username composed of the first_name and last_name joined with a '.'. Some examples are:
     * <ul>
     *     <li>(template) {@link Name#firstName()}.{@link Name#lastName()}</li>
     *     <li>jim.jones</li>
     *     <li>jason.leigh</li>
     *     <li>tracy.jordan</li>
     * </ul>
     *
     * @return a random two part username.
     * @see Name#firstName()
     * @see Name#lastName()
     */
    @Deprecated(since = "2.5.0", forRemoval = true)
    public String username() {
        StringBuilder result = new StringBuilder();
        final Name name = faker.name();
        final String firstName = name.firstName().toLowerCase(faker.getContext().getLocale())
            + "." + name.lastName().toLowerCase(faker.getContext().getLocale());
        for (int i = 0; i < firstName.length(); i++) {
            final char c = firstName.charAt(i);
            if (c == '\'' || Character.isWhitespace(c)) {
                continue;
            }
            result.append(c);
        }
        return result.toString();
    }

    public String emailAddress() {
        return emailAddress(username());
    }

    /**
     * Returns an email address based on the provided name and a random domain (from "internet.free_email").
     * <p>
     * Example usage:
     * </p>
     * <pre>
     * {@code
     * faker.internet().emailAddress("Hal")); // hal@gmail.com
     * faker.internet().emailAddress("John McClane")); // john.mcclane@yahoo.com
     * faker.internet().emailAddress("Stephen Vincent Strange")); // stephen.strange@hotmail.com
     * faker.internet().emailAddress("Dr. Henry Indiana Jones Jr.")); // henry.jones@gmail.com
     * faker.internet().emailAddress("Jeanne d'Arc")); // jeanne.darc@yahoo.com
     * }
     * </pre>
     *
     * @param name The name to be used for generating the local part of the email address.
     * @return A String representing an email address.
     * @see Name#name()
     * @see Name#nameWithMiddle()
     */
    public String emailAddress(String name) {
        return emailAddress(toLocalPart(name), FakerIDN.toASCII(faker.resolve("internet.free_email")));
    }

    public String safeEmailAddress() {
        return safeEmailAddress(username());
    }

    /**
     * Returns an email address based on the provided name and a safe domain (from "internet.safe_email").
     * <pre>
     * {@code
     * faker.internet().safeEmailAddress("John McClane")); // john.mcclane@example.com
     * }
     * </pre>
     * @param name The name to be used for generating the local part of the email address.
     * @return A String representing an email address.
     * @see Name#name()
     * @see Name#nameWithMiddle()
     * @see #emailAddress(String)
     */
    public String safeEmailAddress(String name) {
        return emailAddress(toLocalPart(name), FakerIDN.toASCII(faker.resolve("internet.safe_email")));
    }

    private String emailAddress(String localPart, String domain) {
        return String.join("", stripAccents(localPart), "@", domain);
    }

    public String emailSubject() {
        return resolve("internet.email_subject");
    }

    public static final Pattern DIACRITICS_AND_FRIENDS
        = Pattern.compile("[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+");

    private String stripAccents(String input) {
        // strip accents from input
        String str = Normalizer.normalize(input, Normalizer.Form.NFD);
        str = DIACRITICS_AND_FRIENDS.matcher(str).replaceAll("");
        return str;
    }

    private static final Pattern LOCALPART = Pattern.compile("[^a-z0-9\\.]");

    /**
     * Converts a name to a local part (the part before the '@') of an email
     * address.
     *
     * Will use the first and last names of the provided name, ignoring middle
     * names, and will remove any prefixes or suffixes that are defined in the
     * faker's configuration.
     *
     * @param name The name ({@link Name}) to be converted to a local part.
     * @return A String representing the local part of an email address.
     * @since 2.5.0
     */
    private String toLocalPart(String name) {
        String[] parts = stripAccents(name).split(" ");

        Object prefixObj = faker.fakeValuesService().fetchObject("name.prefix", faker.getContext());
        final List<String> prefixList = (prefixObj instanceof List<?> list
                && list.stream().allMatch(String.class::isInstance))
                        ? list.stream().map(String.class::cast).toList()
                        : Collections.emptyList();
        if (prefixList.contains(parts[0])) {
            parts = Arrays.copyOfRange(parts, 1, parts.length);
        }

        Object suffixObj = faker.fakeValuesService().fetchObject("name.suffix", faker.getContext());
        final List<String> suffixList = (suffixObj instanceof List<?> list
                && list.stream().allMatch(String.class::isInstance))
                        ? list.stream().map(String.class::cast).toList()
                        : Collections.emptyList();
        if (suffixList.contains(parts[parts.length - 1])) {
            parts = Arrays.copyOfRange(parts, 0, parts.length - 1);
        }

        if (parts.length == 0) {
            return LOCALPART.matcher(name.toLowerCase(faker.getContext().getLocale())).replaceAll("");
        }

        if (parts.length == 1) {
            return LOCALPART.matcher(parts[0].toLowerCase(faker.getContext().getLocale())).replaceAll("");
        }

        return String.join(".",
            LOCALPART.matcher(parts[0].toLowerCase(faker.getContext().getLocale())).replaceAll(""),
            LOCALPART.matcher(parts[parts.length - 1].toLowerCase(faker.getContext().getLocale())).replaceAll(""));
    }

    public String domainName() {
        return domainWord() + "." + domainSuffix();
    }

    public String domainWord() {
        return FakerIDN.toASCII(
            faker.name().lastName().toLowerCase(faker.getContext().getLocale()).replace("'", ""));
    }

    public String domainSuffix() {
        return resolve("internet.domain_suffix");
    }

    /**
     * Returns a string representing a web URL, randomly including: http/https scheme, port, path
     * elements (2 or none), file element (1 or none), params (2 or none), anchor (1 or none).
     *
     * @return a web URL
     * @since 2.0.0
     */
    public String url() {
        final byte[] bts = faker.random().nextRandomBytes(6);
        return url(bts[0] % 2 == 0, bts[1] % 2 == 0,
            bts[2] % 2 == 0, bts[3] % 2 == 0,
            bts[4] % 2 == 0, bts[5] % 2 == 0);
    }

    /**
     * Returns a string representing a web URL, with various elements controlled by the caller.
     *
     * @param schemeChoice if true will be random http or https, if false will be https
     * @param portChoice   if true a random port will be included, if false no port will be included
     * @param pathChoice   if true two random path elements will be included, if false no path elements will be included
     * @param fileChoice   if true the path will end with a random word element instead of a slash, if false it will end with a slash
     * @param paramsChoice if true two random name value pairs will be included, if false no params will be included
     * @param anchorChoice if true a random anchor will be included, if false no anchor will be included
     * @return a web URL
     * @since 2.0.0
     */
    public String url(boolean schemeChoice, boolean portChoice, boolean pathChoice, boolean fileChoice, boolean paramsChoice, boolean anchorChoice) {
        String scheme = schemeChoice ? faker.options().nextElement(HTTP_SCHEMES) : "https://";
        String port = portChoice ? (":" + port()) : "";
        String path = pathChoice ? ("/" + slug(faker.lorem().words(2), "/")) : "/";
        String file = fileChoice ? faker.lorem().words(1).get(0) : "";
        String params = paramsChoice ? ("?" + slug(faker.lorem().words(2), "=") + "&" + slug(faker.lorem().words(2), "=")) : "";
        String anchor = anchorChoice ? ("#" + faker.lorem().words(1).get(0)) : "";
        return scheme + webdomain() + port + path + file + params + anchor;
    }

    /**
     * Returns a web domain.
     *
     * @return a web domain in the form "www.example.com"
     * @since 2.0.0
     */
    public String webdomain() {
        return String.join("",
            "www", ".",
            FakerIDN.toASCII(
                faker.name().firstName().toLowerCase(
                    faker.getContext().getLocale()).replace("'", "") + "-" +
                    domainWord()
            ),
            ".",
            domainSuffix()
        );
    }

    public String image() {
        String[] dimension = resolve("internet.image_dimension").split("x");
        if (dimension.length == 0) {
            return "";
        } else {
            return image(Integer.parseInt(dimension[0].trim()), Integer.parseInt(dimension[1].trim()));
        }
    }

    public String image(int width, int height) {
        return "https://picsum.photos/%s/%s".formatted(width, height);
    }

    public String image(int width, int height, String seed) {
        return "https://picsum.photos/seed/%s/%s/%s".formatted(seed, width, height);
    }

    public String httpMethod() {
        return resolve("internet.http_method");
    }

    /**
     * @deprecated since 2.5.0. Use {@link net.datafaker.providers.base.Credentials#password()} instead.
     */
    @Deprecated(since = "2.5.0", forRemoval = true)
    public String password() {
        return password(8, 16);
    }

    /**
     * @deprecated since 2.5.0. Use {@link net.datafaker.providers.base.Credentials#password(boolean)} instead.
     */
    @Deprecated(since = "2.5.0", forRemoval = true)
    public String password(boolean includeDigit) {
        return password(8, 16, false, false, includeDigit);
    }

    /**
     * @deprecated since 2.5.0. Use {@link net.datafaker.providers.base.Credentials#password(int, int)} instead.
     */
    @Deprecated(since = "2.5.0", forRemoval = true)
    public String password(int minimumLength, int maximumLength) {
        return password(minimumLength, maximumLength, false);
    }

    /**
     * @deprecated since 2.5.0. Use {@link net.datafaker.providers.base.Credentials#password(int, int, boolean)} instead.
     */
    @Deprecated(since = "2.5.0", forRemoval = true)
    public String password(int minimumLength, int maximumLength, boolean includeUppercase) {
        return password(minimumLength, maximumLength, includeUppercase, false);
    }

    /**
     * @deprecated since 2.5.0. Use {@link net.datafaker.providers.base.Credentials#password(int, int, boolean, boolean)} instead.
     */
    @Deprecated(since = "2.5.0", forRemoval = true)
    public String password(int minimumLength, int maximumLength, boolean includeUppercase, boolean includeSpecial) {
        return password(minimumLength, maximumLength, includeUppercase, includeSpecial, true);
    }

    /**
     * @deprecated since 2.5.0. Use {@link net.datafaker.providers.base.Credentials#password(int, int, boolean, boolean, boolean)} instead.
     */
    @Deprecated(since = "2.5.0", forRemoval = true)
    public String password(int minimumLength, int maximumLength, boolean includeUppercase, boolean includeSpecial, boolean includeDigit) {
        return faker.text().text(minimumLength, maximumLength, includeUppercase, includeSpecial, includeDigit);
    }

    /**
     * Returns a port number between 0 and 65535
     *
     * @return a port number
     */
    public int port() {
        return port(MIN_PORT_NUMBER, MAX_PORT_NUMBER);
    }

    /**
     * Returns a port number within given range
     *
     * @param range either Well-Known Ports (0-1023), Registered Ports (1024-49151) or Dynamic/Private Ports (49152-65535)
     * @return a port number
     */
    public int port(PortRange range) {
        return port(range.from, range.to);
    }

    /**
     * Returns a port number between {@code from} and {@code to} (inclusive)
     *
     * @param from minimum port number (must not be less than 0)
     * @param to maximum port number (must not be greater than 65535)
     * @return a port number within given range
     */
    public int port(int from, int to) {
        if (from < MIN_PORT_NUMBER)
            throw new IllegalArgumentException("Port number %s cannot be less than %s".formatted(from, MIN_PORT_NUMBER));
        if (to > MAX_PORT_NUMBER)
            throw new IllegalArgumentException("Port number %s cannot be greater than %s".formatted(to, MAX_PORT_NUMBER));
        return faker.random().nextInt(from, to);
    }

    /**
     * <p>Returns a MAC address in the following format: 6-bytes in MM:MM:MM:SS:SS:SS format.</p>
     *
     * @param prefix a prefix to put on the front of the address
     * @return a correctly formatted MAC address
     */
    public String macAddress(String prefix) {
        final String tmp = (prefix == null) ? "" : prefix;
        final int prefixLength = tmp.trim().isEmpty()
            ? 0
            : COLON.split(tmp).length;

        final StringBuilder out = new StringBuilder(tmp);
        for (int i = 0; i < 6 - prefixLength; i++) {
            if (!out.isEmpty()) {
                out.append(':');
            }
            out.append(Integer.toHexString(faker.random().nextInt(16)));
            out.append(Integer.toHexString(faker.random().nextInt(16)));
        }
        return out.toString();
    }

    /**
     * @see Internet#macAddress(String)
     */
    public String macAddress() {
        return macAddress("");
    }

    /**
     * returns an IPv4 address in dot separated octets.
     *
     * @return a correctly formatted IPv4 address.
     */
    public String ipV4Address() {
        return getIpV4Address().getHostAddress();
    }

    /**
     * returns an IPv4 address.
     *
     * @return an IPv4 address.
     */
    public InetAddress getIpV4Address() {
        return inet4Address(
            (byte) (faker.random().nextInt(254) + 2),
            (byte) (faker.random().nextInt(254) + 2),
            (byte) (faker.random().nextInt(254) + 2),
            (byte) (faker.random().nextInt(254) + 2)
        );
    }

    /**
     * @return a valid private IPV4 address in dot notation
     */
    public String privateIpV4Address() {
        return getPrivateIpV4Address().getHostAddress();
    }

    /**
     * @return a private IPV4 address
     */
    public InetAddress getPrivateIpV4Address() {
        final Byte[] PRIVATE_FIRST_OCTET = {10, 127, (byte) 169, (byte) 192, (byte) 172};
        final Byte[] PRIVATE_SECOND_OCTET_172 = {16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};

        final RandomService r = faker.random();
        byte first = random(PRIVATE_FIRST_OCTET),
            second = (byte) r.nextInt(256),
            third = (byte) r.nextInt(256),
            fourth = (byte) r.nextInt(256);

        switch (first) {
            case (byte) 172 -> second = random(PRIVATE_SECOND_OCTET_172);
            case (byte) 192 -> second = (byte) 168;
            case (byte) 169 -> second = (byte) 254;
        }
        return inet4Address(first, second, third, fourth);
    }

    /**
     * @return a valid public IPV4 address in dot notation
     */
    public String publicIpV4Address() {
        return getPublicIpV4Address().getHostAddress();
    }

    /**
     * @return a valid public IPV4 address
     */
    public InetAddress getPublicIpV4Address() {
        final RandomService r = faker.random();

        final byte[] PRIVATE_FIRST_OCTET = {10, 127, (byte) 169, (byte) 192, (byte) 172};

        byte first = (byte) r.nextInt(256),
            second = (byte) r.nextInt(256),
            third = (byte) r.nextInt(256),
            fourth = (byte) r.nextInt(256);

        while (Arrays.binarySearch(PRIVATE_FIRST_OCTET, first) > 0) {
            first = (byte) r.nextInt(256);
        }
        return inet4Address(first, second, third, fourth);
    }

    /**
     * @return a valid IPV4 CIDR
     */
    public String ipV4Cidr() {
        return ipV4Address() +
            '/' +
            (faker.random().nextInt(31) + 1);
    }

    /**
     * <p>Returns an IPv6 address in hh:hh:hh:hh:hh:hh:hh:hh format.</p>
     *
     * @return a correctly formatted IPv6 address.
     */
    public String ipV6Address() {
        return getIpV6Address().getHostAddress();
    }

    /**
     * <p>Returns an IPv6 address in hh:hh:hh:hh:hh:hh:hh:hh format.</p>
     *
     * @return a IPV6 address.
     */
    public InetAddress getIpV6Address() {
        final RandomService random = faker.random();
        final char[] res = new char[4 * 8 + 7];
        for (int i = 0; i < 8; i++) {
            int offset = 4 * i;
            if (i > 0) {
                res[i - 1 + offset] = ':';
            }
            char[] hex = random.hex(4, false).toCharArray();
            System.arraycopy(hex, 0, res, i + offset, hex.length);
        }
        return inet6Address(String.valueOf(res));
    }

    /**
     * @return a valid IPV6 CIDR
     */
    public String ipV6Cidr() {
        return ipV6Address() +
            '/' +
            (faker.random().nextInt(127) + 1);
    }

    /**
     * @return a slug using '_' as the word separator and two {@link Lorem} words as the values
     */
    public String slug() {
        return slug(faker.lorem().words(2), "_");
    }

    /**
     * @param wordsOrNull if null, then 2 {@link Lorem} words
     * @param glueOrNull  if null, "_"
     * @return a slug string combining wordsOrNull with glueOrNull (ex. x_y)
     */
    public String slug(List<String> wordsOrNull, String glueOrNull) {
        final String glue = glueOrNull == null
            ? "_"
            : glueOrNull;
        final List<String> words = wordsOrNull == null
            ? faker.lorem().words(2)
            : wordsOrNull;

        final StringBuilder slug = new StringBuilder();
        for (int i = 0; i < words.size(); i++) {
            if (i > 0) {
                slug.append(glue);
            }
            slug.append(words.get(i));
        }
        return slug.toString();
    }

    /**
     * Returns a UUID (type 3) as String.
     * Use this method (instead of {@link #uuid() uuid}) if you are
     * using a constant random seed and require the same output for different faker instances.     *
     *
     * @return a uuid as string.
     */
    public String uuidv3() {
        return UUID.nameUUIDFromBytes(faker.random().nextRandomBytes(16)).toString();
    }

    public String uuidv4() {
        return uuid();
    }

    public String uuidv7() {
        // Get the current timestamp in milliseconds since Unix epoch
        long timestamp = faker.random().nextLong();

        // Generate random bits
        long randomBits1 = faker.random().nextLong();
        long randomBits2 = faker.random().nextLong();

        // Combine timestamp and random bits
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(timestamp);
        bb.putLong(randomBits1 ^ randomBits2);

        long mostSigBits = bb.getLong(0);
        long leastSigBits = bb.getLong(8);

        // Set the version to 7 (bits 4-7 of the time_hi_and_version field)
        mostSigBits &= ~(0xF000L);  // clear version
        mostSigBits |= 0x7000L;     // set to version 7

        // Set the variant to IETF variant (bits 6-7 of the clock_seq_hi_and_reserved field)
        leastSigBits &= ~(0xC000000000000000L); // clear variant
        leastSigBits |= 0x8000000000000000L;    // set to IETF variant

        return new UUID(mostSigBits, leastSigBits).toString();
    }

    /**
     * Returns a UUID (type 4) as String.
     * <p>
     * This returns a repeatable version of a version 4 UUID, which is a bit against the idea of a version 4 UUID,
     * but this is a faker library, not a UUID library.
     *
     * @return a v4 uuid as string.
     */
    public String uuid() {
        String uuidv3 = uuidv3();
        return uuidv3.substring(0, 14) + '4' + uuidv3.substring(15);
    }

    private <T> T random(T[] src) {
        return src[faker.random().nextInt(src.length)];
    }

    public String userAgent(UserAgent userAgent) {
        UserAgent agent = userAgent;

        if (agent == null) {
            agent = UserAgent.any(faker);
        }

        String userAgentKey = "internet.user_agent." + agent.toString();
        return resolve(userAgentKey);
    }

    public String userAgent() {
        return userAgent(null);
    }

    public enum UserAgent {
        AOL("aol"),
        CHROME("chrome"),
        FIREFOX("firefox"),
        INTERNET_EXPLORER("internet_explorer"),
        NETSCAPE("netscape"),
        OPERA("opera"),
        SAFARI("safari");

        //Browser's name in corresponding yaml (internet.yml) file.
        private final String browserName;

        UserAgent(String browserName) {
            this.browserName = browserName;
        }

        private static UserAgent any(BaseProviders faker) {
            UserAgent[] agents = UserAgent.values();
            int randomIndex = (int) (faker.random().nextDouble() * agents.length);
            return agents[randomIndex];
        }

        @Override
        public String toString() {
            return browserName;
        }
    }

    public String botUserAgent(BotUserAgent vendor) {
        BotUserAgent agent = vendor;

        if (agent == null) {
            agent = BotUserAgent.any(faker);
        }

        String userAgentKey = "internet.bot_user_agent." + agent.toString();
        return resolve(userAgentKey);
    }

    public String botUserAgentAny() {
        return botUserAgent(null);
    }

    public enum BotUserAgent {
        GOOGLEBOT("googlebot"),
        BINGBOT("bingbot"),
        DUCKDUCKBOT("duckduckbot"),
        BAIDUSPIDER("baiduspider"),
        YANDEXBOT("yandexbot");

        //Browser's name in corresponding yaml (internet.yml) file.
        private final String browserName;

        BotUserAgent(String browserName) {
            this.browserName = browserName;
        }

        private static BotUserAgent any(BaseProviders faker) {
            BotUserAgent[] agents = BotUserAgent.values();
            int randomIndex = (int) (faker.random().nextDouble() * agents.length);
            return agents[randomIndex];
        }

        @Override
        public String toString() {
            return browserName;
        }
    }

    private static InetAddress inet4Address(byte first, byte second, byte third, byte fourth) {
        try {
            return Inet4Address.getByAddress(new byte[]{first, second, third, fourth});
        } catch (UnknownHostException e) {
            throw new RuntimeException("Failed to create Inet4Address from %s %s %s %s".formatted(first, second, third, fourth), e);
        }
    }

    private static InetAddress inet6Address(String host) {
        try {
            return Inet6Address.getByName(host);
        } catch (UnknownHostException e) {
            throw new RuntimeException("Failed to create Inet6Address from host '%s'".formatted(host), e);
        }
    }

    public enum PortRange {
        WellKnownPorts(0, 1023),
        RegisteredPorts(1024, 49151),
        DynamicPrivatePorts(49152, 65535);

        private final int from;
        private final int to;

        PortRange(int from, int to) {
            this.from = from;
            this.to = to;
        }
    }
}
