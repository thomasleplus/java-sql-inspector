package org.leplus.sql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.leplus.antlr4.PLSQLBaseListener;
import org.leplus.antlr4.PLSQLParser.ConstantContext;

/**
 * ANTLR listener that collects the literal constants of a PL/SQL parse tree. {@code NULL} literals
 * are skipped, since they denote absence rather than a value.
 */
public class ConstantListener extends PLSQLBaseListener {

  private static final String NULL = "NULL";

  private final List<Result> results = new ArrayList<>();

  private void addResult(final ConstantContext ctx) {
    if (!NULL.equalsIgnoreCase(ctx.getText())) {
      final Result result = new Result();
      result.setValue(ctx.getText());
      result.setLine(ctx.getStart().getLine());
      result.setColumn(ctx.getStart().getStartIndex());
      results.add(result);
    }
  }

  @Override
  public void enterConstant(final ConstantContext ctx) {
    addResult(ctx);
  }

  /**
   * @return an unmodifiable view of the constants collected so far.
   */
  public List<Result> getResults() {
    return Collections.unmodifiableList(results);
  }
}
