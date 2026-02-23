# Traffic Counter (Java)

## Purpose

CLI application that reads half-hour traffic counts from a file and outputs:
- total cars
- daily totals
- top 3 half-hours by traffic
- the least-traffic 1.5-hour period (3 contiguous records)

## Stack

- Java 21
- Maven 3.9+
- Spring IoC (`spring-context`) for dependency wiring

## Quick Start

Run tests:

```bash
mvn test
```

Run the app with sample input:

```bash
mvn -q exec:java -Dexec.args="data/sample_input.txt"
```


## Docker Local Dev (Optional)

Open a shell:

```bash
docker compose run --rm app
```

Run commands directly:

```bash
docker compose run --rm app mvn test
docker compose run --rm app mvn -q exec:java -Dexec.args="data/sample_input.txt"
```


## Input Contract

Each non-empty input line must follow:

```text
YYYY-MM-DDTHH:MM:SS <count>
```

Example:

```text
2021-12-01T05:00:00 5
2021-12-01T05:30:00 12
```

Notes:
- Blank lines are ignored.
- Timestamp must be ISO-8601 local date-time.
- Count must be an integer.

## Output Contract

The app prints:
1. `Total cars: <number>`
2. Daily totals as `<yyyy-mm-dd> <number>`
3. Top 3 half-hours as `<timestamp> <count>`
4. Least traffic 1.5-hour period (3 contiguous records) plus period total

## Assumptions and Rules

- Input is processed in file order.
- "Contiguous records" means adjacent lines in file order.
- Top-3 tie-breaker: earlier timestamp wins when counts are equal.
- Least-period tie-breaker: first minimum window encountered is returned.

## Project Structure

```text
src/main/java/com/trafficcounter/
  app/
  analytics/
  domain/
  io/
  report/

src/test/java/com/trafficcounter/
  TrafficAnalyticsTest.java
  FileTrafficRecordsReaderTest.java
  IsoTrafficRecordParserTest.java
```
