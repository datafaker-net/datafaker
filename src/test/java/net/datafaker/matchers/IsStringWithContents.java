package net.datafaker.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsStringWithContents extends TypeSafeMatcher<String> {

    @Override
    protected boolean matchesSafely(String s) {
        if (s == null) return false;

        return s.trim().length() > 0;
    }


    @Override
    public void describeTo(Description description) {
        description.appendText("is string with contents");
    }

    public static Matcher<String> isStringWithContents() {
        return new IsStringWithContents();
    }

}
