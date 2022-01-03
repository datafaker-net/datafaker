First and foremost thanks to anyone who contributes, very much appreciated.

## Guidelines

- If you add new faker classes like `Address`, `Country`, and `Number` they should be accompanied by a unit test. Where relevant please add more assertions to the `net.datafaker.FakerIT` class.
- If you add a new faker class, update the `README.md`.
- Submit a PR with your change and if there are no comments, changes will be merged in
- If you're not sure about the change, raise an issue and have a discussion before spending time coding it up
- Try and make one logical change per PR. That is not make many changes in one PR. Submit multiple PRs instead
- Java 11 is our target version. If you need anything older than that, we recommend using [Java Faker](https://github.com/DiUS/java-faker) instead.

## Building

- Should be as easy as running `mvn clean install` on the root directory
