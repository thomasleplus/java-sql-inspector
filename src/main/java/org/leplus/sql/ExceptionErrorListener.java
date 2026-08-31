package org.leplus.sql;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/**
 * An ANTLR error listener that makes parsing fail fast: instead of ANTLR's default "recover and
 * continue", it rethrows the recognition exception on the first syntax error.
 */
public class ExceptionErrorListener extends BaseErrorListener {

  public ExceptionErrorListener() {
    super();
  }

  @Override
  public void syntaxError(
      final Recognizer<?, ?> recognizer,
      final Object offendingSymbol,
      final int line,
      final int charPositionInLine,
      final String msg,
      final RecognitionException e) {
    throw e;
  }
}
