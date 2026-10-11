package net.datafaker.providers.base;

import net.datafaker.idnumbers.AmericanIdNumber;
import net.datafaker.idnumbers.IdNumberGenerator;
import net.datafaker.idnumbers.SingaporeIdNumber;
import net.datafaker.idnumbers.SingaporeIdNumber.Type;

import java.lang.reflect.InvocationTargetException;
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
