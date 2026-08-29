package org.leplus.sql;

import java.util.List;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.leplus.antlr4.PLSQLLexer;
import org.leplus.antlr4.PLSQLParser;

/** Inspects SQL statements for literal constants, using an ANTLR4 PL/SQL grammar. */
public class Inspector {

  public Inspector() {
    super();
  }

  /**
   * Parses a SQL statement and returns the literal constants it contains, in
   * source order. Parsing fails fast: the first syntax error is rethrown (see
   * {@link ExceptionErrorListener}).
   *
   * @param sqlStatement the SQL text to inspect.
   * @return the constants found.
   */
  public List<Result> findConstants(final String sqlStatement) {
    final PLSQLLexer lexer = new PLSQLLexer(CharStreams.fromString(sqlStatement));
    final CommonTokenStream tokens = new CommonTokenStream(lexer);
    final PLSQLParser parser = new PLSQLParser(tokens);
    parser.removeErrorListeners();
    parser.addErrorListener(new ExceptionErrorListener());
    final ParseTreeWalker walker = new ParseTreeWalker();
    final ConstantListener listener = new ConstantListener();
    walker.walk(listener, parser.data_manipulation_language_statements());
    return listener.getResults();
  }
}
