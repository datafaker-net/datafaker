# Expressions

Datafaker supports different kind of expressions which allows to customise the output. 

## Letterify
This one will replace `?` symbols with latin letters e.g.
```java
Faker faker = new Faker();
faker.expression("#{letterify 'test????test'}"); // could give e.g. testqwastest
// Also there could a third argument telling if characters should be uppercase
faker.expression("#{letterify 'test????test','true'}"); // could give e.g. testSKDLtest
```

## Numerify
This one will replace `#` symbols with digits e.g.
```java
Faker faker = new Faker();
faker.expression("#{numerify '#test#'}"); // could give e.g. 3test5
faker.expression("#{numerify '####'}"); // could give e.g. 1234
```

##Bothify
Applies both letterify and numerify e.g.
```java
Faker faker = new Faker();
faker.expression("#{bothify '?#?#?#?#'}"); // could give a1b2c3d4
faker.expression("#{bothify '?#?#?#?#', 'true'}"); // could give A1B2C3D4
```

## Templatify
This is available since 1.2.0

This one will replace symbol mentioned in the second args with one of symbols mentioned after it.
```java
Faker faker = new Faker();
// e.g. there is expression test and we want to replace t with q or @
faker.expression("#{templatify 'test','t','q','@'}"); // could give @esq
// another example there is expression test and we want to replace t with q or @ or $ or *
faker.expression("#{templatify 'test','t','q','@','$','*'}"); // could give @esq
```

## Examplify
This one will replace symbols by example: uppercase with uppercase, digit with digit, lowercase with lowercase.
```java
Faker faker = new Faker();
faker.expression("#{examplify 'ABC'}"); // could give QWE
faker.expression("#{examplify 'test'}"); // could give ghjk
```

## Regexify
This one allows generating output based on regexp, e.g.
```java
Faker faker = new Faker();
faker.expression("#{regexify '(a|b){2,3}'}"); // could give ab
faker.regexify("[a-z]{4,10}"); // could give wbevoa
```

## Options
This is available since 1.2.0

This will return one from the provided options e.g.
```java
Faker faker = new Faker();
faker.expression("#{options.option 'ABC','2','5','$'}"); // could give $
faker.expression("#{options.option '23','2','5','$','%','*'}"); // could give *
```
## Csv
This is available since 1.4.0

The expression will return generated csv based on input parameters
```java
faker.expression("#{csv '1','name_column','#{Name.first_name}','last_name_column','#{Name.last_name}'}");
// "name_column","last_name_column"
// "Sabrina","Kihn"
faker.expression("#{csv ' ### ','\"','false','3','name_column','#{Name.first_name}','last_name_column','#{Name.last_name}'}");
// "Thad" ### "Crist"
// "Kathryne" ### "Wuckert"
// "Sybil" ### "Connelly"
```

## Json
This is available since 1.4.0

The expression will return generated json based on input parameters
```java
faker.expression("#{json 'person','#{json ''first_name'',''#{Name.first_name}'',''last_name'',''#{Name.last_name}''}','address','#{json ''country'',''#{Address.country}'',''city'',''#{Address.city}''}'}");
// {"person": {"first_name": "Barbie", "last_name": "Durgan"}, "address": {"country": "Albania", "city": "East Catarinahaven"}}

```

## Others
It is possible to call methods returning string values and taking primitive or string args via expressions e.g.
```java
Faker faker = new Faker();
faker.expression("#{date.birthday 'yy DDD hh:mm:ss'}");
faker.expression("#{color.name}");
```
