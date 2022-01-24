# Notes

## Merging from Javafaker 

```
git remote add javafaker https://github.com/DiUS/java-faker.git\n
git fetch javafaker pull/693/head:pr693
git merge pr693
```

## Publish docs

```
mkdocs gh-deploy
```

## Publish to maven central

```
mvn deploy
```

