First and foremost thanks to anyone who contributes, very much appreciated.

## Guidelines

- If you add new faker classes like `Address`, `Country`, and `Number` they should be accompanied by a unit test.
- New faker classes should be placed in the relevant group of providers. For example: `Minecraft` class will be in  the `videogame` group, `Address` in the `base` group.
- If you add a new provider, update the `README.md`. We prefer US English when naming classes or methods.
- Submit a PR with your change and if there are no comments, changes will be merged in.
- If you're not sure about the change, raise an issue and have a discussion before spending time coding it up.
- Try and make one logical change per PR. That is not make many changes in one PR. Submit multiple PRs instead.
- Starting with Datafaker 2.x, Java 17 is our target version. If you need anything older than that, we recommend using the 1.x versions of Datafaker instead. Note that while the 1.x is stable and free of known bugs, this branch won't receive future updates.

## Building

- Should be as easy as running `mvnw clean install` on the root directory.

## Dependencies

In order to keep Datafaker slim and focused the project generally attempts to avoid introducing new dependencies. Should a reasonable argument be made to including a library as a dependency it should meet the following criteria (subject to change without notice):

- Minimal size (<1MB)
- No dependency tree (single level, the library itself) 
- Fills a need we can't do "simply"

## Languages and countries

Localization files are located in folder `src/main/resources`
1. Country-specific values are stored in files `_COUNTRY.yml` (e.g. `_AL.yml` for Albania)
   * these are value that are the same in all languages in this country (e.g. phone country code, passport number pattern etc.)
2. Language-specific values are stored in files `language.yml` (e.g. `it.yml` for Italian)
   * these are cities, streets, names etc. in that languages.
3. In files `language-COUNTRY.yml`, we store values specific for this language and country
   * e.g. `ru-MD.yml` contains Moldovan city names in Russian language (that is widely used in Moldova).

After adding a new country or language, don't forget to update
* constant `net.datafaker.providers.base.Locality.LOCALES`
* tests in `LocalityTest.java`
