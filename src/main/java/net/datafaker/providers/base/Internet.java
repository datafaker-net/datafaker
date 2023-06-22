package net.datafaker.providers.base;

import net.datafaker.internal.helper.FakerIDN;
import net.datafaker.service.RandomService;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @since 0.8.0
 */
public class Internet extends AbstractProvider<BaseProviders> {
    private static final Pattern SINGLE_QUOTE = Pattern.compile("'");
    private static final Pattern COLON = Pattern.compile(":");
    private static final List<String> HTTP_SCHEMES = List.of("http://", "https://");

    protected Internet(BaseProviders faker) {
        super(faker);
    }

    public String emailAddress() {
        return emailAddress(faker.name().username());
    }

    public String emailAddress(String localPart) {
        return emailAddress(localPart, FakerIDN.toASCII(faker.resolve("internet.free_email")));
    }

    public String safeEmailAddress() {
        return safeEmailAddress(faker.name().username());
    }

    public String safeEmailAddress(String localPart) {
        return emailAddress(localPart, FakerIDN.toASCII(faker.resolve("internet.safe_email")));
    }

    private String emailAddress(String localPart, String domain) {
        return String.join("", stripAccents(localPart), "@", domain);
    }

    public static final Pattern DIACRITICS_AND_FRIENDS
        = Pattern.compile("[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+");

    private String stripAccents(String input) {
        // strip accents from input
        String str = Normalizer.normalize(input, Normalizer.Form.NFD);
        str = DIACRITICS_AND_FRIENDS.matcher(str).replaceAll("");
        return str;
    }

    public String domainName() {
        return domainWord() + "." + domainSuffix();
    }

    public String domainWord() {
        return FakerIDN.toASCII(SINGLE_QUOTE.matcher(faker.name().lastName().toLowerCase()).replaceAll(""));
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
     * @param portChoice if true a random port will be included, if false no port will be included
     * @param pathChoice if true two random path elements will be included, if false no path elements will be included
     * @param fileChoice if true the path will end with a random word element instead of a slash, if false it will end with a slash
     * @param paramsChoice if true two random name value pairs will be included, if false no params will be included
     * @param anchorChoice if true a random anchor will be included, if false no anchor will be included
     * @return a web URL
     * @since 2.0.0
     */
    public String url(boolean schemeChoice, boolean portChoice, boolean pathChoice, boolean fileChoice, boolean paramsChoice, boolean anchorChoice) {
        String scheme = schemeChoice ? HTTP_SCHEMES.get(faker.random().nextInt(0, 1)) : "https://";
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
            "www",
            ".",
            FakerIDN.toASCII(
                SINGLE_QUOTE.matcher(faker.name().firstName().toLowerCase()).replaceAll("") +
                    "-" +
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

    public String password() {
        return password(8, 16);
    }

    public String password(boolean includeDigit) {
        return password(8, 16, false, false, includeDigit);
    }

    public String password(int minimumLength, int maximumLength) {
        return password(minimumLength, maximumLength, false);
    }

    public String password(int minimumLength, int maximumLength, boolean includeUppercase) {
        return password(minimumLength, maximumLength, includeUppercase, false);
    }

    public String password(int minimumLength, int maximumLength, boolean includeUppercase, boolean includeSpecial) {
        return password(minimumLength, maximumLength, includeUppercase, includeSpecial, true);
    }

    public String password(int minimumLength, int maximumLength, boolean includeUppercase, boolean includeSpecial, boolean includeDigit) {
        return faker.text().text(minimumLength, maximumLength, includeUppercase, includeSpecial, includeDigit);
    }

    /**
     * Returns a port number between 0 and 65535
     *
     * @return a port number
     */
    public int port() {
        return faker.random().nextInt(0, 65535);
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
        try {
            return getIpV4Address().getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }

    /**
     * returns an IPv4 address.
     *
     * @return an IPv4 address.
     */
    public InetAddress getIpV4Address() throws UnknownHostException {
        return Inet4Address.getByAddress(new byte[]{
            (byte) (faker.random().nextInt(254) + 2),
            (byte) (faker.random().nextInt(254) + 2),
            (byte) (faker.random().nextInt(254) + 2),
            (byte) (faker.random().nextInt(254) + 2)});
    }

    /**
     * @return a valid private IPV4 address in dot notation
     */
    public String privateIpV4Address() {
        try {
            return getPrivateIpV4Address().getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }

    /**
     * @return a private IPV4 address
     */
    public InetAddress getPrivateIpV4Address() throws UnknownHostException {
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
        return Inet4Address.getByAddress(new byte[]{first, second, third, fourth});
    }

    /**
     * @return a valid public IPV4 address in dot notation
     */
    public String publicIpV4Address() {
        try {
            return getPublicIpV4Address().getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }

    /**
     * @return a valid public IPV4 address
     */
    public InetAddress getPublicIpV4Address() throws UnknownHostException {
        final RandomService r = faker.random();

        final byte[] PRIVATE_FIRST_OCTET = {10, 127, (byte) 169, (byte) 192, (byte) 172};

        byte first = (byte) r.nextInt(256),
            second = (byte) r.nextInt(256),
            third = (byte) r.nextInt(256),
            fourth = (byte) r.nextInt(256);

        while (Arrays.binarySearch(PRIVATE_FIRST_OCTET, first) > 0) {
            first = (byte) r.nextInt(256);
        }
        return Inet4Address.getByAddress(new byte[]{first, second, third, fourth});
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
        try {
            return getIpV6Address().getHostAddress();
        } catch (UnknownHostException e) {
            return "0:0:0:0:0:0:0:1";
        }
    }

    /**
     * <p>Returns an IPv6 address in hh:hh:hh:hh:hh:hh:hh:hh format.</p>
     *
     * @return a IPV6 address.
     */
    public InetAddress getIpV6Address() throws UnknownHostException {
        final StringBuilder tmp = new StringBuilder(2 * 8 + 7);
        for (int i = 0; i < 8; i++) {
            if (i > 0) {
                tmp.append(":");
            }
            tmp.append(Integer.toHexString(faker.random().nextInt(16)));
            tmp.append(Integer.toHexString(faker.random().nextInt(16)));
            tmp.append(Integer.toHexString(faker.random().nextInt(16)));
            tmp.append(Integer.toHexString(faker.random().nextInt(16)));
        }
        return Inet6Address.getByName(tmp.toString());
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
}
