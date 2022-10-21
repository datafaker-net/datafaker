First and foremost thanks to anyone who contributes, very much appreciated.

## Guidelines

- If you add new faker classes like `Address`, `Country`, and `Number` they should be accompanied by a unit test.
- New faker classes should be placed in the relevant group of providers. For example: `Minecraft` class will be in  the `videogame` group, `Address` in the `base` group.
- If you add a new faker class, update the `README.md`.
- Submit a PR with your change and if there are no comments, changes will be merged in.
- If you're not sure about the change, raise an issue and have a discussion before spending time coding it up.
- Try and make one logical change per PR. That is not make many changes in one PR. Submit multiple PRs instead.
- Java 8 is our target version. If you need anything older than that, we recommend using [Java Faker](https://github.com/DiUS/java-faker) instead.

## Building

- Should be as easy as running `mvn clean install` on the root directory.
- When you do not have [GnuPG](https://gnupg.org/) in your path, and you cannot install it, to be able to build without error you may
  - specify the property `gpg.skip=true` when running maven, for example `mvn clean install '-Dgpg.skip=true'`.
  - use the profile `noGpg`, for example `mvn clean install -PnoGpg`
