# Java SQL Inspector

Utility to test Java code for SQL injection vulnerabilities. It uses [Antlr](https://www.antlr.org) to parse SQL queries and detects any literal value  in the statement that should be handled via a prepared statement bind variable instead (using '?' as a placeholder).

[![Maven](https://github.com/thomasleplus/java-sql-inspector/workflows/Maven/badge.svg)](https://github.com/thomasleplus/java-sql-inspector/actions?query=workflow:"Maven")
[![CodeQL](https://github.com/thomasleplus/java-sql-inspector/workflows/CodeQL/badge.svg)](https://github.com/thomasleplus/java-sql-inspector/actions?query=workflow:"CodeQL")
