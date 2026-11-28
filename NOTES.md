# Notes

## Merging from Javafaker 

```
git remote add javafaker https://github.com/DiUS/java-faker.git\n
git fetch javafaker pull/693/head:pr693
git merge pr693
```

## Publish docs

See [`material/README.md`](material/README.md) for docs site maintenance, local development, and upgrade workflow.

```bash
python3 -m venv .venv-docs && source .venv-docs/bin/activate
pip install -r requirements-docs.txt
mkdocs serve
mkdocs gh-deploy
```

## Publish to maven central

```
mvn deploy
```
