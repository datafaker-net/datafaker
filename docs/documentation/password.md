# Advanced password and arbitrary string generation

## Require lowercase letters

Datafaker 3.0 adds an `includeLowercase` argument to `text().text()` and `credentials().password()`. The flags specify uppercase letters, special symbols, digits, and lowercase letters in that order. Each enabled flag requires at least one character from its category. Lowercase letters are eligible in either setting.

```java
Faker faker = new Faker();

String password = faker.credentials().password(12, 16, true, false, true, true);
String text = faker.text().text(12, 16, true, false, true, true);
```

Both calls produce 12 to 16 characters and require uppercase letters, digits, and lowercase letters. Use the builder below to require more than one character from a category or to define custom character sets.

## Set character counts and symbol sets

Starting 1.7.0 there is `Text` provider which allows to generate arbitrary strings with advanced rules.
Now it allows to specify different set of symbols which should be contained and minimum amount of symbols per such set.

Imagine there is a need to generate a password with length 8 and containing minimum 3 digits and 2 upper case symbols from en locale.

=== "Java"

    ```java
         var faker = new Faker();
         String password = faker.text().text(Text.TextSymbolsBuilder.builder()
                             .len(8)
                             .with(EN_UPPERCASE, 2)
                             .with(DIGITS, 3).build());
    ```
It also allows to use custom symbol sets. For example this will generate a string with length between 8 and 10. 
The string will contain min 3 lower case symbols from ru locale and minimum 5 symbols from the defined string `customSpecialSymbols`.

=== "Java"

    ```java
          final String ruLowerCase = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
          final String customSpecialSymbols = "!@#$%^*;'][{}";
          final int ruCnt = 3;
          final int specSmbCnt = 5;
          final Text.TextRuleConfig config = Text.TextSymbolsBuilder.builder()
              .len(faker.number().numberBetween(ruCnt + specSmbCnt, Math.max(ruCnt + specSmbCnt, 10)))
              .with(ruLowerCase, ruCnt)
              .with(customSpecialSymbols, specSmbCnt).build();
          final String text = faker.text().text(config);
    ```
