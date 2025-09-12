package net.datafaker.providers.base;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Generates credentials such as usernames, uids and passwords.
 * 
 * @since 2.5.0
 */
public class Credentials extends AbstractProvider<BaseProviders> {

    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 16;

    protected Credentials(BaseProviders faker) {
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

    /**
     * Generates a password, only with lowercase letters, numbers and
     * with length between 8 and 16 characters.
     *
     * @return A randomly generated password
     */
    public String password() {
        return password(MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH);
    }

    /**
     * Generates a password, only with lowercase letters and optionally numbers
     * with length between 8 and 16 characters.
     *
     * @param includeDigit if true, the password will contain at least one digit
     * @return A randomly generated password
     */
    public String password(boolean includeDigit) {
        return password(MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH, false, false, includeDigit);
    }

    /**
     * Generates a password, only with lowercase letters, numbers and 
     * with min and max length defined by the user.
     *
     * @param minimumLength the minimum length of the password
     * @param maximumLength the maximum length of the password
     * @return A randomly generated password
     */
    public String password(int minimumLength, int maximumLength) {
        return password(minimumLength, maximumLength, false);
    }

    /**
     * Generates a password with lowercase and optionally uppercase letters, numbers
     * and with min and max length defined by the user.
     *
     * @param minimumLength    the minimum length of the password
     * @param maximumLength    the maximum length of the password
     * @param includeUppercase if true, the password will contain at least one uppercase letter
     * @return A randomly generated password
     */
    public String password(int minimumLength, int maximumLength, boolean includeUppercase) {
        return password(minimumLength, maximumLength, includeUppercase, false);
    }

    /**
     * Generates a password with lowercase letters, numbers and optionally uppercase letters and 
     * special characters and with min and max length defined by the user.
     *
     * @param minimumLength    the minimum length of the password
     * @param maximumLength    the maximum length of the password
     * @param includeUppercase if true, the password will contain at least one uppercase letter
     * @param includeSpecial   if true, the password will contain at least one special character
     * @return A randomly generated password
     */
    public String password(int minimumLength, int maximumLength, boolean includeUppercase, boolean includeSpecial) {
        return password(minimumLength, maximumLength, includeUppercase, includeSpecial, true);
    }

    /**
     * Generates a password with lowercase letters and optionally uppercase letters, numbers and 
     * special characters and with min and max length defined by the user.
     *
     * @param minimumLength    the minimum length of the password
     * @param maximumLength    the maximum length of the password
     * @param includeUppercase if true, the password will contain at least one uppercase letter
     * @param includeSpecial   if true, the password will contain at least one special character
     * @param includeDigit     if true, the password will contain at least one digit
     * @return A randomly generated password
     */
    public String password(int minimumLength, int maximumLength, boolean includeUppercase, boolean includeSpecial, boolean includeDigit) {
        return faker.text().text(minimumLength, maximumLength, includeUppercase, includeSpecial, includeDigit);
    }

    /**
     * Returns a weak password from a pre-defined list of common weak passwords.
     * 
     * Some examples are:
     * <ul>
     *     <li>123456</li>
     *     <li>password</li>
     *     <li>qwerty</li>
     * </ul>
     * 
     * @return a random weak password.
     */
    public String weakPassword() {
        return resolve("credentials.weak_password");
    }

    /**
     * Generates a user ID based on the regex pattern defined in the resource file.
     * If the regex is null or invalid, it returns null.
     *
     * @return A randomly generated user ID based on the regex or null if the regex is null or invalid
     */
    public String userId() {
        return userId(resolve("credentials.uid_pattern"));
    }

    /**
     * Generates a user ID based on the provided regex pattern.
     * If the regex is null or invalid, it returns null.
     *
     * @param regex The regex pattern to generate the user ID
     * @return A randomly generated user ID based on the regex or null if the regex is null or invalid
     */
    public String userId(String regex) {
        if(regex == null) {
            return null;
        }

        try {
            Pattern.compile(regex);
        } catch (PatternSyntaxException e) {
            return null;
        }

        return faker.regexify(regex);
    }
}
