# TE

Tank Engineer

### Running Command Line

```bash
mvn compile assembly:single
```

Run

```bash
java -jar -Djava.library.path=target/natives target/tank-engineer-1.0-SNAPSHOT-jar-with-dependencies.jar
```

### IDE

Set VM Argument

```
-Djava.library.path=target/natives
```

