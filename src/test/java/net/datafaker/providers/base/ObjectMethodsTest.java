package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static net.datafaker.providers.base.ObjectMethods.executeMethodByReturnType;
import static net.datafaker.providers.base.ObjectMethods.getMethodByName;
import static org.assertj.core.api.Assertions.assertThat;

class ObjectMethodsTest {

    private final Person person = new Person();
    private final PersonName personName = new PersonName();

    @Test
    void methodByName() throws NoSuchMethodException {
        assertThat(getMethodByName(person, "age")).isEqualTo(Person.class.getMethod("age"));
        assertThat(getMethodByName(person, "name")).isEqualTo(Person.class.getMethod("name"));

        assertThat(getMethodByName(personName, "firstName")).isEqualTo(PersonName.class.getMethod("firstName"));
        assertThat(getMethodByName(personName, "lastName")).isEqualTo(PersonName.class.getMethod("lastName"));
    }

    @Test
    void methodByReturnType() {
        assertThat((Age) executeMethodByReturnType(person, "Age")).isInstanceOf(Age.class);
        assertThat((PersonName) executeMethodByReturnType(person, "PersonName")).isInstanceOf(PersonName.class);
        assertThat((String) executeMethodByReturnType(personName, "String")).isEqualTo("John");
        assertThat((CharSequence) executeMethodByReturnType(personName, "CharSequence")).isEqualTo("Smith");
    }

    private static class Person {
        public Age age() {
            return new Age();
        }
        public PersonName name() {
            return new PersonName();
        }
    }

    private static class Age {
        public int value() {
            return 23;
        }
    }

    private static class PersonName {
        public String firstName() {
            return "John";
        }
        public CharSequence lastName() {
            return "Smith";
        }
    }
}
