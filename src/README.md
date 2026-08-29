# Source

Maven sources for `java-sql-inspector`, which parses SQL and inspects it.

- `main/antlr4/org/leplus/antlr4/PLSQL.g4` — the ANTLR4 PL/SQL grammar; the
  antlr4 Maven plugin generates the parser from it at build time.
- `main/java/org/leplus/sql/` — the inspector: `Inspector` drives parsing,
  `ConstantListener` walks the parse tree, `Result` holds findings, and
  `ExceptionErrorListener` turns parse errors into exceptions.
- `test/java/org/leplus/sql/` — JUnit tests and assertions.

Build/test with `./mvnw test` (grammar is compiled automatically).
