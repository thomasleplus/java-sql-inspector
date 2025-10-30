# Java SQL Inspector

A security-focused Java library that detects SQL injection vulnerabilities by identifying literal values in SQL queries that should be parameterized using prepared statement bind variables.

[![Maven](https://github.com/thomasleplus/java-sql-inspector/workflows/Maven/badge.svg)](https://github.com/thomasleplus/java-sql-inspector/actions?query=workflow:"Maven")

## Overview

Java SQL Inspector uses [ANTLR 4](https://www.antlr.org) with a PL/SQL grammar to parse SQL statements and detect constants (string literals, numeric literals) that could lead to SQL injection vulnerabilities. The tool reports the exact location (line and column) of each problematic literal that should instead use the `?` placeholder in prepared statements.

## Features

- **Static SQL Analysis**: Parses SQL queries to identify hardcoded literals
- **Precise Location Reporting**: Reports line and column numbers for each detected constant
- **Smart Filtering**: Automatically excludes `NULL` values (which don't pose injection risks)
- **Comprehensive SQL Support**: Handles SELECT, INSERT, UPDATE, DELETE statements with complex expressions
- **Type Support**: Detects both string constants (`'value'`) and numeric literals (`123`)
- **Operator Coverage**: Supports WHERE clauses, BETWEEN, LIKE, IN, comparison and logical operators
- **Lightweight**: Pure Java library with minimal dependencies

## Requirements

- Java 17 or higher
- Maven 3.6.3 or higher (for building from source)

## Installation

Add the dependency to your Maven `pom.xml`:

```xml
<dependency>
  <groupId>org.leplus</groupId>
  <artifactId>java-sql-inspector</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
```

## Usage

### Basic Example

```java
import org.leplus.sql.Inspector;
import org.leplus.sql.Result;
import java.util.List;

public class Example {
    public static void main(String[] args) {
        Inspector inspector = new Inspector();

        String sql = "SELECT * FROM users WHERE id = 123 AND name = 'John'";
        List<Result> results = inspector.findConstants(sql);

        for (Result result : results) {
            System.out.printf("Found constant at line %d, column %d: %s%n",
                result.getLine(), result.getColumn(), result.getValue());
        }
    }
}
```

**Output:**
```
Found constant at line 1, column 32: 123
Found constant at line 1, column 46: 'John'
```

### Recommended Fix

The vulnerable SQL should be rewritten using prepared statements:

```java
// BEFORE (vulnerable)
String sql = "SELECT * FROM users WHERE id = 123 AND name = 'John'";

// AFTER (secure)
String sql = "SELECT * FROM users WHERE id = ? AND name = ?";
PreparedStatement stmt = connection.prepareStatement(sql);
stmt.setInt(1, 123);
stmt.setString(2, "John");
```

### Additional Examples

**Empty Results** (no vulnerabilities):
```java
inspector.findConstants("SELECT * FROM users"); // Returns empty list
inspector.findConstants("SELECT * FROM users WHERE id = ?"); // Returns empty list
inspector.findConstants("SELECT * FROM users WHERE status IS NULL"); // Returns empty list
```

**Multiple Constants**:
```java
String sql = "SELECT * FROM products WHERE price BETWEEN 10 AND 100";
List<Result> results = inspector.findConstants(sql);
// Returns 2 results: "10" and "100"
```

**LIKE Patterns**:
```java
String sql = "SELECT * FROM users WHERE email LIKE '%@example.com'";
List<Result> results = inspector.findConstants(sql);
// Returns 1 result: "'%@example.com'"
```

**Arithmetic Expressions**:
```java
String sql = "UPDATE accounts SET balance = balance - 50 WHERE id = 1";
List<Result> results = inspector.findConstants(sql);
// Returns 2 results: "50" and "1"
```

## API Reference

### Inspector Class

**Method:**
```java
public List<Result> findConstants(String sqlStatement)
```

Parses the provided SQL statement and returns a list of all detected constants.

- **Parameters:** `sqlStatement` - The SQL query to analyze
- **Returns:** List of `Result` objects containing details of each detected literal
- **Throws:** `RecognitionException` if the SQL syntax is invalid

### Result Class

Represents a detected constant in the SQL statement.

**Methods:**
- `int getLine()` - Returns the line number (1-based)
- `int getColumn()` - Returns the column position (0-based start index)
- `String getValue()` - Returns the literal value (e.g., `"123"`, `"'text'"`)
- `String toString()` - Returns formatted string: `"line:column:value"`

Results are comparable and sorted by: line → column → value (alphabetically).

## Supported SQL Statements

The tool supports PL/SQL syntax including:

- **DML Operations**: SELECT, INSERT, UPDATE, DELETE
- **WHERE Clauses**: All comparison operators (=, !=, <>, >, <, >=, <=)
- **Logical Operators**: AND, OR
- **Pattern Matching**: LIKE with '%' and '_' wildcards
- **Range Queries**: BETWEEN operator
- **List Matching**: IN operator
- **Arithmetic**: +, -, *, / operations in expressions

## Building from Source

Clone the repository and build with Maven:

```bash
git clone https://github.com/thomasleplus/java-sql-inspector.git
cd java-sql-inspector
./mvnw clean verify
```

Install to your local Maven repository:

```bash
./mvnw clean install
```

The build includes:
- JaCoCo code coverage analysis
- PMD code quality checks
- SpotBugs + FindSecBugs security scanning
- OWASP Dependency-Check for vulnerabilities
- Google Checkstyle validation

## Development

### Running Tests

The project includes 180+ test cases covering various SQL patterns:

```bash
./mvnw test
```

### Code Quality

Run all quality checks:

```bash
./mvnw verify
```

This executes:
- Unit tests with JUnit 5
- Code coverage reporting (JaCoCo)
- Static analysis (PMD, SpotBugs)
- Security scanning (FindSecBugs, Dependency-Check)
- Style validation (Checkstyle)

## Limitations

- **Grammar**: Currently uses PL/SQL grammar; other SQL dialects may have limited support
- **Static Analysis Only**: Cannot detect SQL injection in dynamically constructed queries
- **Concatenation**: Does not analyze string concatenation or StringBuilder patterns
- **Runtime Values**: Cannot inspect actual runtime query values

## Use Cases

- **Code Review Tools**: Integrate into static analysis pipelines
- **Testing Frameworks**: Add SQL security checks to test suites
- **CI/CD Pipelines**: Automated vulnerability detection in builds
- **Security Audits**: Batch analysis of SQL queries in codebases
- **Developer Tools**: IDE plugins for real-time SQL validation

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines on how to contribute to this project.

## License

See [LICENSE](LICENSE) for license information.

## Security

See [SECURITY.md](SECURITY.md) for information about reporting security vulnerabilities.

## Acknowledgments

- ANTLR 4 for the parsing framework
- PL/SQL grammar contributors
