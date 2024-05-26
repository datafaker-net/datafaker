package net.datafaker.providers.base;

import net.datafaker.idnumbers.AlbanianIdNumber;
import net.datafaker.idnumbers.BulgarianIdNumber;
import net.datafaker.idnumbers.EnIdNumber;
import net.datafaker.idnumbers.EnZAIdNumber;
import net.datafaker.idnumbers.EsMXIdNumber;
import net.datafaker.idnumbers.EstonianIdNumber;
import net.datafaker.idnumbers.IdNumbers;
import net.datafaker.idnumbers.KoKrIdNumber;
import net.datafaker.idnumbers.MacedonianIdNumber;
import net.datafaker.idnumbers.MoldovaIdNumber;
import net.datafaker.idnumbers.NricNumber;
import net.datafaker.idnumbers.NricNumber.Type;
import net.datafaker.idnumbers.PeselNumber;
import net.datafaker.idnumbers.PeselNumber.Gender;
import net.datafaker.idnumbers.PtNifIdNumber;
import net.datafaker.idnumbers.SvSEIdNumber;
import net.datafaker.idnumbers.ZhCnIdNumber;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @since 0.8.0
 */
public class IdNumber extends AbstractProvider<BaseProviders> {

    private final Map<Class<? extends IdNumbers>, IdNumbers> providers = new ConcurrentHashMap<>();

    protected IdNumber(BaseProviders faker) {
        super(faker);
    }

    public String valid() {
        return resolve("id_number.valid");
    }

    public String invalid() {
        return faker.numerify(faker.resolve("id_number.invalid"));
    }

    public String ssnValid() {
        return provider(EnIdNumber.class).getValidSsn(faker);
    }

    /**
     * Specified as #{IDNumber.valid_sv_se_ssn} in sv-SE.yml
     */
    public String validSvSeSsn() {
        return provider(SvSEIdNumber.class).getValidSsn(faker);
    }

    /**
     * Specified as #{IDNumber.valid_en_za_ssn} in en-ZA.yml
     */
    public String validEnZaSsn() {
        return provider(EnZAIdNumber.class).getValidSsn(faker);
    }

    /**
     * Specified as #{IDNumber.invalid_en_za_ssn} in en-ZA.yml
     */
    public String inValidEnZaSsn() {
        return provider(EnZAIdNumber.class).getInValidSsn(faker);
    }

    /**
     * Specified as #{IDNumber.invalid_sv_se_ssn} in sv-SE.yml
     */
    public String invalidSvSeSsn() {
        return provider(SvSEIdNumber.class).getInvalidSsn(faker);
    }

    public String singaporeanFin() {
        return NricNumber.getValidFIN(faker, Type.FOREIGNER_TWENTY_FIRST_CENTURY);
    }

    public String singaporeanFinBefore2000() {
        return NricNumber.getValidFIN(faker, Type.FOREIGNER_TWENTIETH_CENTURY);
    }

    public String singaporeanUin() {
        return NricNumber.getValidFIN(faker, Type.SINGAPOREAN_TWENTY_FIRST_CENTURY);
    }

    public String singaporeanUinBefore2000() {
        return NricNumber.getValidFIN(faker, Type.SINGAPOREAN_TWENTIETH_CENTURY);
    }

    /**
     * Generate a valid Zh-CN id number.
     *
     * @return A Zh-CN id number
     */
    public String validZhCNSsn() {
        return provider(ZhCnIdNumber.class).getValidSsn(faker);
    }

    public String validPtNif() {
        return provider(PtNifIdNumber.class).getValid(faker);
    }

    public String invalidPtNif() {
        return provider(PtNifIdNumber.class).getInvalid(faker);
    }


    /**
     * Specified as #{IDNumber.valid_es_mx_ssn} in es-MX.yml
     *
     * @return A valid MEX CURP.
     */
    public String validEsMXSsn() {
        return provider(EsMXIdNumber.class).get(faker);
    }

    /**
     * Specified as #{IDNumber.invalid_es_mx_ssn} in es-MX.yml
     *
     * @return A valid MEX CURP.
     */
    public String invalidEsMXSsn() {
        return provider(EsMXIdNumber.class).getWrong(faker);
    }

    /**
     * Generates a valid PESEL number for a person of random gender and age between
     * 0 and 100.
     *
     * @return A valid PESEL number
     */
    public String peselNumber() {
        return peselNumber(faker.date().birthday(0, 100).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
            Gender.ANY);
    }

    /**
     * Generates a valid PESEL number for a person with given gender and birth date.
     *
     * @param birthDate Given birth date
     * @param gender    Person's gender. Null value means {@link net.datafaker.idnumbers.PeselNumber.Gender#ANY}
     * @return A valid PESEL number
     */
    public String peselNumber(LocalDate birthDate, Gender gender) {
        return new PeselNumber(faker).get(birthDate, gender);
    }

    /**
     * Generates a valid RRN (Resident Registration Number) for a person of random binary gender and default random age
     *
     * @return A valid RRN
     * @since 1.8.0
     */
    public String validKoKrRrn() {
        return provider(KoKrIdNumber.class).getValidRrn(faker);
    }

    /**
     * Generates valid ID number for Georgian citizens and Residents
     *
     * @return A valid ID Number
     */
    public String validGeIDNumber() {
    	return faker.numerify("###########");
    }

    /**
     * Generates a valid ID number for Estonian citizens and residents
     * Specified as #{IDNumber.valid_estonian_personal_code} in et.yml
     * @return A valid ID Number
     */
    public String validEstonianPersonalCode() {
        return provider(EstonianIdNumber.class).getValid(faker);
    }

    /**
     * Generates an invalid ID number for Estonian citizens and residents
     * Specified as #{IDNumber.invalid_estonian_personal_code} in et.yml
     *
     * @return An invalid ID Number
     */
    public String invalidEstonianPersonalCode() {
        return provider(EstonianIdNumber.class).getInvalid(faker);
    }

    /**
     * Generates a valid ID number for Albania citizens and residents
     * Specified as #{IDNumber.valid_albanian_personal_code} in sq.yml
     * @return A valid ID Number
     */
    public String validAlbanianPersonalCode() {
        return provider(AlbanianIdNumber.class).getValid(faker);
    }

    /**
     * Generates a valid ID number for Albania citizens and residents
     * Specified as #{IDNumber.invalid_albanian_personal_code} in sq.yml
     * @return An invalid ID Number
     */
    public String invalidAlbanianPersonalCode() {
        return provider(AlbanianIdNumber.class).getInvalid(faker);
    }

    /**
     * Generates a valid ID number for Albania citizens and residents
     * Specified as #{IDNumber.valid_moldova_personal_code} in ro-MD.yml
     * @return A valid ID Number
     */
    public String validMoldovaPersonalCode() {
        return provider(MoldovaIdNumber.class).getValid(faker);
    }

    /**
     * Generates a valid ID number for Albania citizens and residents
     * Specified as #{IDNumber.invalid_moldova_personal_code} in ro-MD.yml
     * @return An invalid ID Number
     */
    public String invalidMoldovaPersonalCode() {
        return provider(MoldovaIdNumber.class).getInvalid(faker);
    }

    /**
     * Generates a valid ID number for Bulgaria citizens and residents
     * Specified as #{IDNumber.valid_bulgarian_personal_code} in bg.yml
     * @return A valid ID Number
     */
    public String validBulgarianPersonalCode() {
        return provider(BulgarianIdNumber.class).getValid(faker);
    }

    /**
     * Generates a valid ID number for Bulgaria citizens and residents
     * Specified as #{IDNumber.invalid_bulgarian_personal_code} in bg.yml
     * @return An invalid ID Number
     */
    public String invalidBulgarianPersonalCode() {
        return provider(BulgarianIdNumber.class).getInvalid(faker);
    }

    /**
     * Generates a valid ID number for North Macedonia citizens and residents
     * Specified as #{IDNumber.valid_macedonian_personal_code} in mk.yml
     * @return A valid ID Number
     */
    public String validMacedonianPersonalCode() {
        return provider(MacedonianIdNumber.class).getValid(faker);
    }

    /**
     * Generates a valid ID number for North Macedonia citizens and residents
     * Specified as #{IDNumber.invalid_macedonian_personal_code} in mk.yml
     * @return An invalid ID Number
     */
    public String invalidMacedonianPersonalCode() {
        return provider(MacedonianIdNumber.class).getInvalid(faker);
    }

    @SuppressWarnings("unchecked")
    private <T extends IdNumbers> T provider(Class<T> clazz) {
        return (T) providers.computeIfAbsent(clazz, aClass -> create(clazz));
    }

    private <T extends IdNumbers> T create(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("Failed to instantiate class " + clazz.getName(), e);
        }
    }
}
