package net.datafaker.assertj.base;

import org.assertj.core.api.StringAssert;

public class NameAssert extends StringAssert {

    public NameAssert(String s) {
        super(s);
    }

    public static NameAssert asserThat(String actual) {
        return new NameAssert(actual);
    }

    public NameAssert isName() {
        isNotNull();

        // check condition
        if (!actual.matches("([\\w']+\\.?( )?){2,4}")) {
            failWithMessage("Expected string to be a firstname, but it wasn't");
        }

        // return the current assertion for method chaining
        return this;
    }

    public NameAssert isNameWithMiddleName() {
        isNotNull();

        // check condition
        String regex = "^[A-Za-z.']+(?:\\s[A-Za-z.']+){2,}$";
        if (!actual.matches(regex)) {
            failWithMessage("Expected name with middle name to match <%s>, but was <%s>", regex, actual);
        }

        // return the current assertion for method chaining
        return this;
    }
}
