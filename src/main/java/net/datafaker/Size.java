package net.datafaker;

public class Size {

    private final Faker faker;

    protected Size(Faker faker){
        this.faker = faker;
    }

    public String adjective(){
        return faker.fakeValuesService().resolve("size.adjective", this, faker);
    }

}
