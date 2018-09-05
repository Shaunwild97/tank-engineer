# TE

Tank Engineer

### Running

```bash
mvn compile assembly:single
```

Set

```
-Djava.library.path=target/natives
```

Run

```bash
java -jar -Djava.library.path=target/natives target/tank-engineer-1.0-SNAPSHOT-jar-with-dependencies.jar
```