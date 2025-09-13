package net.datafaker.providers.base;

/**
 * @since 0.8.0
 */
public class Name extends AbstractProvider<BaseProviders> {

    /**
     * Internal constructor, not to be used by clients.  Instances of {@link Name} should be accessed via
     * {@link BaseFaker#name()}.
     */
    protected Name(BaseProviders faker) {
        super(faker);
    }

    /**
     * A multipart name composed of an optional prefix, a firstname and a lastname
     * or other possible variances based on locale.  Examples:
     * <ul>
     *     <li>James Jones Jr.</li>
     *     <li>Julie Johnson</li>
     * </ul>
     *
     * @return a random name with given and family names and an optional suffix.
     */
    public String name() {
        return resolve("name.name");
    }

    /**
     * A multipart name composed of an optional prefix, a given and family name,
     * another 'firstname' for the middle name and an optional suffix such as Jr.
     * Examples:
     * <ul>
     *     <li>Mrs. Ella Geraldine Fitzgerald</li>
     *     <li>Jason Tom Sawyer Jr.</li>
     *     <li>Helen Jessica Troy</li>
     * </ul>
     *
     * @return a random name with a middle name component with optional prefix and suffix
     */
    public String nameWithMiddle() {
        return resolve("name.name_with_middle");
    }

    /**
     * Returns the same value as {@link #name()}
     *
     * @see Name#name()
     */
    public String fullName() {
        return name();
    }

    /**
     * Returns a random 'given' name such as Aaliyah, Aaron, Abagail or Abbey
     *
     * @return a 'given' name such as Aaliyah, Aaron, Abagail or Abbey
     */
    public String firstName() {
        return resolve("name.first_name");
    }

    /**
     * Returns a random female 'given' name.
     *
     * @return a female 'given' name
     */
    public String femaleFirstName() {
        return resolve("name.female_first_name");
    }

    /**
     * Returns a random male 'given' name.
     * @deprecated Use {@link #maleFirstName()} instead.
     *
     * @return a male 'given' name
     */
    @Deprecated(since = "2.4.3", forRemoval = true)
    @SuppressWarnings("SpellCheckingInspection")
    public String malefirstName() {
        return maleFirstName();
    }

    /**
     * Returns a random male 'given' name.
     *
     * @return a male 'given' name
     */
    public String maleFirstName() {
        return resolve("name.male_first_name");
    }

    /**
     * Returns a random last name such as Smith, Jones or Baldwin
     *
     * @return a random last name such as Smith, Jones or Baldwin
     */
    public String lastName() {
        return resolve("name.last_name");
    }

    /**
     * Returns a name prefix such as Mr., Mrs., Ms., Miss, or Dr.
     *
     * @return a name prefix such as Mr., Mrs., Ms., Miss, or Dr.
     */
    public String prefix() {
        return resolve("name.prefix");
    }

    /**
     * Returns a name suffix such as Jr., Sr., I, II, III, IV, V, MD, DDS, PhD or DVM
     *
     * @return a name suffix such as Jr., Sr., I, II, III, IV, V, MD, DDS, PhD or DVM
     */
    public String suffix() {
        return resolve("name.suffix");
    }

    /**
     * A three part title composed of a descriptor level and job.  Some examples are :
     * <ul>
     *   <li>(template) {descriptor} {level} {job}</li>
     *   <li>Lead Solutions Specialist</li>
     *   <li>National Marketing Manager</li>
     *   <li>Central Response Liaison</li>
     * </ul>
     *
     * @return a random three part job title
     */
    public String title() {
        return String.join(" ",
            resolve("name.title.descriptor"),
            resolve("name.title.level"),
            resolve("name.title.job")
        );
    }

    /**
     * A lowercase username composed of the first_name and last_name joined with a '.'. Some examples are:
     * <ul>
     *     <li>(template) {@link #firstName()}.{@link #lastName()}</li>
     *     <li>jim.jones</li>
     *     <li>jason.leigh</li>
     *     <li>tracy.jordan</li>
     * </ul>
     *
     * @deprecated Use {@link Credentials#username()} instead.
     * @return a random two part username.
     * @see Name#firstName()
     * @see Name#lastName()
     */
    @Deprecated(since = "2.5.0", forRemoval = true)
    @SuppressWarnings("removal")
    public String username() {
        return faker.internet().username();
    }
}
