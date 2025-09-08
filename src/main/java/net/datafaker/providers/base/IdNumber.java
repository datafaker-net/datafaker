package net.datafaker.providers.base;

import net.datafaker.idnumbers.AmericanIdNumber;
import net.datafaker.idnumbers.MexicanIdNumber;
import net.datafaker.idnumbers.IdNumberGenerator;
import net.datafaker.idnumbers.SouthKoreanIdNumber;
import net.datafaker.idnumbers.SingaporeIdNumber;
import net.datafaker.idnumbers.SingaporeIdNumber.Type;
import net.datafaker.idnumbers.PolishIdNumber;
import net.datafaker.idnumbers.PolishIdNumber.Gender;
import net.datafaker.idnumbers.PortugueseIdNumber;
import net.datafaker.idnumbers.SouthAfricanIdNumber;
import net.datafaker.idnumbers.SwedenIdNumber;
import net.datafaker.idnumbers.ChineseIdNumber;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @since 0.8.0
 */
public class IdNumber extends AbstractProvider<BaseProviders> {

    private final Map<Class<? extends IdNumberGenerator>, IdNumberGenerator> providers = new ConcurrentHashMap<>();
    private final Map<String, IdNumberGenerator> countryProviders = new ConcurrentHashMap<>();

    protected IdNumber(BaseProviders faker) {
        super(faker);
        List<IdNumberGenerator> idNumbers = loadGenerators(IdNumberGenerator.class);
        for (IdNumberGenerator idNumber : idNumbers) {
            countryProviders.put(idNumber.countryCode(), idNumber);
        }
    }

    public String valid() {
        return countryProvider()
            .map(p -> p.generateValid(faker))
            .orElseGet(() -> faker.numerify(faker.resolve("id_number.valid")));
    }

    public String invalid() {
        return countryProvider()
            .map(p -> p.generateInvalid(faker))
            .orElseGet(() -> faker.numerify(faker.resolve("id_number.invalid")));
    }

    public PersonIdNumber valid(IdNumberRequest request) {
        return countryProvider()
            .map(p -> p.generateValid(faker, request))
            .orElseThrow(() -> new IllegalArgumentException("ID Number generation not supported for country '%s'".formatted(country())));
    }

    public record IdNumberRequest(
        int minAge,
        int maxAge,
        GenderRequest gender
    ) {}

    public enum GenderRequest {
        FEMALE, MALE, ANY
    }

    private Optional<IdNumberGenerator> countryProvider() {
        return Optional.ofNullable(countryProviders.get(country()));
    }

    private String country() {
        return faker.getContext().getLocale().getCountry();
    }

    public String ssnValid() {
        return provider(AmericanIdNumber.class).generateValid(faker);
    }

    /**
     * @deprecated Instead of calling this method directly, use faker with locale:
     * <pre>
     * {@code
     *   Faker faker = new Faker(new Locale("sv", "SE"));
     *   String idNumber = faker.idNumber().valid();
     * }
     * </pre>
     */
    @Deprecated
    public String validSvSeSsn() {
        return provider(SwedenIdNumber.class).generateValid(faker);
    }

    @Deprecated
    public String invalidSvSeSsn() {
        return provider(SwedenIdNumber.class).generateInvalid(faker);
    }

    /**
     * @deprecated Instead of calling this method directly, use faker with locale:
     * <pre>
     * {@code
     *   Faker faker = new Faker(new Locale("en", "ZA"));
     *   String idNumber = faker.idNumber().valid();
     * }
     * </pre>
     */
    @Deprecated
    public String validEnZaSsn() {
        return provider(SouthAfricanIdNumber.class).getValidSsn(faker);
    }

    @Deprecated
    public String inValidEnZaSsn() {
        return provider(SouthAfricanIdNumber.class).getInValidSsn(faker);
    }

    public String singaporeanFin() {
        return SingaporeIdNumber.getValidFIN(faker, Type.FOREIGNER_TWENTY_FIRST_CENTURY);
    }

    public String singaporeanFinBefore2000() {
        return SingaporeIdNumber.getValidFIN(faker, Type.FOREIGNER_TWENTIETH_CENTURY);
    }

    public String singaporeanUin() {
        return SingaporeIdNumber.getValidFIN(faker, Type.SINGAPOREAN_TWENTY_FIRST_CENTURY);
    }

    public String singaporeanUinBefore2000() {
        return SingaporeIdNumber.getValidFIN(faker, Type.SINGAPOREAN_TWENTIETH_CENTURY);
    }

    /**
     * Generate a valid Chinese id number
     *
     * @deprecated Instead of calling this method directly, use faker with locale:
     * <pre>
     * {@code
     *   Faker faker = new Faker(new Locale("zh", "CN"));
     *   String idNumber = faker.idNumber().valid();
     * }
     * </pre>
     */
    @Deprecated
    public String validZhCNSsn() {
        return provider(ChineseIdNumber.class).generateValid(faker);
    }

    /**
     * Generate a valid Portuguese ID number
     *
     * @deprecated Instead of calling this method directly, use faker with locale:
     * <pre>
     * {@code
     *   Faker faker = new Faker(new Locale("pt", "PT"));
     *   String idNumber = faker.idNumber().valid();
     * }
     * </pre>
     */
    @Deprecated
    public String validPtNif() {
        return provider(PortugueseIdNumber.class).generateValid(faker);
    }

    @Deprecated
    public String invalidPtNif() {
        return provider(PortugueseIdNumber.class).generateInvalid(faker);
    }

    /**
     * @return A valid Mexican CURP
     *
     * @deprecated Instead of calling this method directly, use faker with locale:
     * <pre>
     * {@code
     *   Faker faker = new Faker(new Locale("es", "MX"));
     *   String idNumber = faker.idNumber().valid();
     * }
     * </pre>
     */
    @Deprecated
    public String validEsMXSsn() {
        return provider(MexicanIdNumber.class).get(faker);
    }

    /**
     * @return An invalid Mexican CURP
     *
     * @deprecated Instead of calling this method directly, use faker with locale:
     * <pre>
     * {@code
     *   Faker faker = new Faker(new Locale("es", "MX"));
     *   String idNumber = faker.idNumber().invalid();
     * }
     * </pre>
     */
    @Deprecated
    public String invalidEsMXSsn() {
        return provider(MexicanIdNumber.class).generateInvalid(faker);
    }

    /**
     * Generates a valid PESEL number for a person of random gender and age between
     * 0 and 100.
     *
     * @deprecated Instead of calling this method directly, use faker with locale:
     * <pre>
     * {@code
     *   Faker faker = new Faker(new Locale("pl", "PL"));
     *   String idNumber = faker.idNumber().valid();
     * }
     * </pre>
     *
     * @return A valid PESEL number
     */
    @Deprecated
    @SuppressWarnings("DeprecatedIsStillUsed")
    public String peselNumber() {
        return peselNumber(faker.timeAndDate().birthday(0, 100), Gender.ANY);
    }

    /**
     * Generates a valid PESEL number for a person with given gender and birthdate.
     *
     * @param birthDate Given birthdate
     * @param gender    Person's gender. Null value means {@link PolishIdNumber.Gender#ANY}
     * @return A valid PESEL number
     *
     * @deprecated Instead of calling this method directly, use faker with locale and age/gender parameters:
     * <pre>
     * {@code
     *   Faker faker = new Faker(new Locale("pl", "PL"));
     *   String idNumber = faker.idNumber().valid(new IdNumberRequest(minAge, maxAge, gender));
     * }
     * </pre>
     */
    @Deprecated
    public String peselNumber(LocalDate birthDate, Gender gender) {
        return new PolishIdNumber().get(faker, birthDate, gender);
    }

    /**
     * Generates a valid RRN (Resident Registration Number) for a person of random binary gender and default random age
     *
     * @return A valid RRN
     * @since 1.8.0
     * @deprecated Instead of calling this method directly, use faker with locale:
     * <pre>
     * {@code
     *   Faker f = new Faker(new Locale("en", "KR"));
     *   String rrn = f.idNumber().valid();
     * }
     * </pre>
     */
    @Deprecated
    public String validKoKrRrn() {
        return provider(SouthKoreanIdNumber.class).getValidRrn(faker);
    }

    /**
     * Generates valid ID number for Georgian citizens and Residents
     *
     * @deprecated Instead of calling this method directly, use faker with locale:
     * <pre>
     * {@code
     *   Faker f = new Faker(new Locale("en", "GE"));
     *   String idNumber = f.idNumber().valid();
     * }
     * </pre>
     */
    @Deprecated
    public String validGeIDNumber() {
    	return faker.numerify("###########");
    }

    @SuppressWarnings("unchecked")
    private <T extends IdNumberGenerator> T provider(Class<T> clazz) {
        return (T) providers.computeIfAbsent(clazz, aClass -> create(clazz));
    }

    private <T extends IdNumberGenerator> T create(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("Failed to instantiate class " + clazz.getName(), e);
        }
    }
}
