package net.datafaker;

public class NatoPhoneticAlphabet {

    private final Faker faker;

    protected NatoPhoneticAlphabet(Faker faker) {
        this.faker = faker;
    }

    public String codeWord() {
        return faker.fakeValuesService().resolve("nato_phonetic_alphabet.code_word", this, faker);
    }

}