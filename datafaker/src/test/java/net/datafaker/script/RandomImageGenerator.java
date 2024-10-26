package net.datafaker.script;

import net.datafaker.Faker;

public class RandomImageGenerator {

    public static void main(String[] args) {
        Faker faker = new Faker();

        System.out.println("PNG Image:");
        System.out.println(faker.image().base64PNG());
        System.out.println("JPG Image:");
        System.out.println(faker.image().base64JPEG());
        System.out.println("GIF Image:");
        System.out.println(faker.image().base64GIF());
        System.out.println("SVG Image:");
        System.out.println(faker.image().base64SVG());
    }
}
