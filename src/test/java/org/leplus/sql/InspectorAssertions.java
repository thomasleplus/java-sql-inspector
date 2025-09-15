package org.leplus.sql;

import static org.hamcrest.Matchers.containsInAnyOrder;

import java.util.List;
import org.hamcrest.Matcher;
import org.hamcrest.collection.IsEmptyCollection;

public class InspectorAssertions {

  public static Matcher<? super List<Result>> are(Result... results) {
    return containsInAnyOrder(results);
  }

  public static Result constant(int line, int column, String value) {
    final Result result = new Result();
    result.setValue(value);
    result.setLine(line);
    result.setColumn(column);
    return result;
  }

  public static List<Result> constantsOf(String sqlStatement) {
    return new Inspector().findConstants(sqlStatement);
  }

  public static Matcher<? super List<Result>> isEmpty() {
    return IsEmptyCollection.empty();
  }

  private InspectorAssertions() {
    super();
  }
}
