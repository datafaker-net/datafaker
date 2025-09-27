package net.datafaker.providers.base;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType;
import com.google.i18n.phonenumbers.Phonemetadata;
import com.google.i18n.phonenumbers.Phonenumber;
import net.datafaker.annotations.InternalApi;
import net.datafaker.service.FakeValuesService;
import net.datafaker.service.FakerContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

class PhoneNumberGenerator {
    private static final PhoneNumberUtil libPhoneNumber = PhoneNumberUtil.getInstance();
    private static final Map<CacheKey, String> CACHE = new ConcurrentHashMap<>();
    private static final int MAX_RETRIES = 100;

    private final FakeValuesService fakeValuesService;
    private final FakerContext context;

    PhoneNumberGenerator(FakeValuesService fakeValuesService, FakerContext context) {
        this.fakeValuesService = fakeValuesService;
        this.context = context;
    }

    @InternalApi
    String randomPhoneNumber(String countryCodeIso2, PhoneNumberType type, PhoneNumberFormat format) {
        Phonenumber.PhoneNumber phoneNumber = randomPhoneNumber(countryCodeIso2, type);
        return libPhoneNumber.format(phoneNumber, format);
    }

    private Phonenumber.PhoneNumber randomPhoneNumber(String countryCodeIso2, PhoneNumberType type) {
        String pattern = CACHE.computeIfAbsent(new CacheKey(countryCodeIso2, type),
            (key) -> phoneNumberPattern(countryCodeIso2, type));
        Phonenumber.PhoneNumber candidate = generatePhoneNumber(countryCodeIso2, pattern);

        // in few cases, the generated phone number matches the country pattern,
        // but still is not valid because of mismatching area code.
        // For example, in Germany ("DE"), such invalid phone number is "28978023638".
        for (int i = 0; i < MAX_RETRIES && !libPhoneNumber.isValidNumber(candidate); i++) {
            candidate = generatePhoneNumber(countryCodeIso2, pattern);
        }
        return candidate;
    }

    private Phonenumber.PhoneNumber generatePhoneNumber(String countryCodeIso2, String phoneNumberPattern) {
        String nationalNumber = fakeValuesService.regexify(phoneNumberPattern, context);
        try {
            return libPhoneNumber.parse(nationalNumber, countryCodeIso2);
        } catch (NumberParseException e) {
            throw new RuntimeException("Failed to parse generated phone number %s".formatted(nationalNumber), e);
        }
    }

    private String phoneNumberPattern(String countryCodeIso2, PhoneNumberType type) {
        return getNumberDescriptionByType(countryCodeIso2, type).getNationalNumberPattern();
    }

    private Phonemetadata.PhoneNumberDesc getNumberDescriptionByType(String countryCodeIso2, PhoneNumberType type) {
        Phonemetadata.PhoneMetadata metadata = getPhoneMetadata(countryCodeIso2);
        return switch (type) {
            case MOBILE -> metadata.getMobile();
            case FIXED_LINE -> metadata.getFixedLine();
            default -> throw new IllegalArgumentException("Unsupported phone number type: " + type);
        };
    }

    private static Phonemetadata.PhoneMetadata getPhoneMetadata(String countryCodeIso2) {
        try {
            Method method = libPhoneNumber.getClass().getDeclaredMethod("getMetadataForRegion", String.class);
            method.setAccessible(true);
            Phonemetadata.PhoneMetadata metadata = (Phonemetadata.PhoneMetadata) method.invoke(libPhoneNumber, countryCodeIso2);
            return requireNonNull(metadata, () -> "Unsupported country code: %s".formatted(countryCodeIso2));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to extract phone number metadata for region " + countryCodeIso2, e);
        }
    }

    private record CacheKey(
        String countryCodeIso2,
        PhoneNumberType phoneNumberType
    ) {}

}
