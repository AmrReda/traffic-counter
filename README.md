# Traffic Counter (Java)

## Requirements

- JDK 21+
- Maven 3.9+
- Docker (optional, for containerized local dev)

## Run tests

```bash
mvn test
```

## Run app

```bash
mvn -q exec:java -Dexec.args="data/sample_input.txt"
```

## Docker local dev

Build and open a shell in the dev container:

```bash
docker compose run --rm app
```

From inside the container:

```bash
mvn test
mvn -q exec:java -Dexec.args="data/sample_input.txt"
```

Run a single command without entering a shell:

```bash
docker compose run --rm app mvn test
docker compose run --rm app mvn -q exec:java -Dexec.args="data/sample_input.txt"
```
