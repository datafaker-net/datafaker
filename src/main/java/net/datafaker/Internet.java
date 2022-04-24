package net.datafaker;

import net.datafaker.service.FakerIDN;
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
public class Internet {
    private static final Pattern SINGLE_QUOTE = Pattern.compile("'");
    private static final Pattern COLON = Pattern.compile(":");
    private final Faker faker;

    protected Internet(Faker faker) {
        this.faker = faker;
    }

    public String emailAddress() {
        return emailAddress(faker.name().username());
    }

    public String emailAddress(String localPart) {
        return emailAddress(localPart, FakerIDN.toASCII(faker.fakeValuesService().resolve("internet.free_email", this, faker)));
    }

    public String safeEmailAddress() {
        return safeEmailAddress(faker.name().username());
    }

    public String safeEmailAddress(String localPart) {
        return emailAddress(localPart, FakerIDN.toASCII(faker.fakeValuesService().resolve("internet.safe_email", this, faker)));
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
        return faker.fakeValuesService().resolve("internet.domain_suffix", this, faker);
    }

    public String url() {
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

    /**
     * Generates a random avatar url based on a collection of profile pictures of real people. All this avatar have been
     * authorized by its awesome users to be used on live websites (not just mockups). For more information, please
     * visit: <a href="http://uifaces.com/authorized">http://uifaces.com/authorized</a>
     *
     * @return an url to a random avatar image.
     * @see <a href="http://uifaces.com/authorized">Authorized UI Faces</a>
     */
    public String avatar() {
        return "https://s3.amazonaws.com/uifaces/faces/twitter/" + faker.fakeValuesService().resolve("internet.avatar", this, faker);
    }

    /**
     * Generates a random image url based on the lorempixel service. All the images provided by this service are released
     * under the creative commons license (CC BY-SA). For more information, please visit: <a href="http://lorempixel.com/">http://lorempixel.com/</a>
     *
     * @return an url to a random image.
     * @see <a href="http://lorempixel.com/">lorempixel - Placeholder Images for every case</a>
     */
    public String image() {
        String[] dimension = faker.fakeValuesService().resolve("internet.image_dimension", this, faker).split("x");
        if (dimension.length == 0) {
            return "";
        } else {
            return image(
                Integer.valueOf(dimension[0].trim()), Integer.valueOf(dimension[1].trim()),
                faker.bool().bool(), null);
        }
    }

    /**
     * Same as image() but allows client code to choose a few image characteristics
     *
     * @param width  the image width
     * @param height the image height
     * @param gray   true for gray image and false for color image
     * @param text   optional custom text on the selected picture
     * @return an url to a random image with the given characteristics.
     */
    public String image(Integer width, Integer height, Boolean gray, String text) {
        return String.format("https://lorempixel.com/%s%s/%s/%s/%s",
            gray ? "g/" : "", width, height, faker.fakeValuesService().resolve("internet.image_category", this, faker),
            (text == null || text.length() == 0) ? "" : text);
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
        return faker.lorem().characters(minimumLength, maximumLength, includeUppercase, includeSpecial, includeDigit);
    }

    /**
     * <p>Returns a MAC address in the following format: 6-bytes in MM:MM:MM:SS:SS:SS format.</p>
     *
     * @param prefix a prefix to put on the front of the address
     * @return a correctly formatted MAC address
     */
    public String macAddress(String prefix) {
        final String tmp = (prefix == null) ? "" : prefix;
        final int prefixLength = tmp.trim().length() == 0
            ? 0
            : COLON.split(tmp).length;

        final StringBuilder out = new StringBuilder(tmp);
        for (int i = 0; i < 6 - prefixLength; i++) {
            if (out.length() > 0) {
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
            case (byte) 172:
                second = random(PRIVATE_SECOND_OCTET_172);
                break;
            case (byte) 192:
                second = (byte) 168;
                break;
            case (byte) 169:
                second = (byte) 254;
                break;
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

        final byte[] PRIVATE_FIRST_OCTET = {10, 127, (byte)169, (byte)192, (byte)172};

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
        final StringBuilder tmp = new StringBuilder();
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
     * Returns a UUID (type 4) as String.
     *
     * @return A UUID as String.
     */
    public String uuid() {
        return UUID.randomUUID().toString();
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
        return faker.fakeValuesService().resolve(userAgentKey, this, faker);
    }

    public String userAgentAny() {
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

        private static UserAgent any(Faker faker) {
            UserAgent[] agents = UserAgent.values();
            int randomIndex = (int) (faker.random().nextDouble() * agents.length);
            return agents[randomIndex];
        }

        @Override
        public String toString() {
            return browserName;
        }
    }
}
