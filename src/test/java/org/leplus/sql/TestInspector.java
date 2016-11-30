package org.leplus.sql;

import static org.junit.Assert.assertThat;
import static org.leplus.sql.InspectorAssertions.are;
import static org.leplus.sql.InspectorAssertions.constant;
import static org.leplus.sql.InspectorAssertions.constantsOf;
import static org.leplus.sql.InspectorAssertions.isEmpty;

import org.junit.Test;

public class TestInspector {

	public TestInspector() {
		super();
	}

	@Test
	public void testFindConstants() {
		assertThat(constantsOf("SELECT 1 FROM dual"), are(constant(1, 7, "1")));
		assertThat(constantsOf("SELECT * FROM foo"), isEmpty());
		assertThat(constantsOf("SELECT * FROM foo WHERE bar IS NULL"), isEmpty());
		assertThat(constantsOf("SELECT * FROM foo WHERE bar IS NOT NULL"), isEmpty());
		assertThat(constantsOf("SELECT * FROM foo WHERE bar = NULL"), isEmpty());
		assertThat(constantsOf("SELECT * FROM foo WHERE bar != NULL"), isEmpty());
		assertThat(constantsOf("SELECT * FROM foo WHERE bar <> NULL"), isEmpty());
		assertThat(constantsOf("SELECT * FROM foo WHERE bar = 1"), are(constant(1, 30, "1")));
		assertThat(constantsOf("SELECT * FROM foo WHERE bar = 1 OR bar = 2"),
				are(constant(1, 30, "1"), constant(1, 41, "2")));
		assertThat(constantsOf("SELECT * FROM foo WHERE bar = 1 AND bar = 2"),
				are(constant(1, 30, "1"), constant(1, 42, "2")));
		assertThat(constantsOf("SELECT * FROM foo WHERE bar BETWEEN 1 AND 2"),
				are(constant(1, 36, "1"), constant(1, 42, "2")));
		assertThat(constantsOf("SELECT * FROM foo WHERE bar LIKE '%x%'"), are(constant(1, 33, "'%x%'")));
		assertThat(constantsOf("SELECT * FROM foo WHERE bar LIKE '_x_'"), are(constant(1, 33, "'_x_'")));
		assertThat(constantsOf("SELECT * FROM foo WHERE bar IN (1, 2)"),
				are(constant(1, 32, "1"), constant(1, 35, "2")));
		assertThat(constantsOf("SELECT * FROM foo WHERE bar <> 1"), are(constant(1, 31, "1")));
		assertThat(constantsOf("SELECT * FROM foo WHERE bar > 1"), are(constant(1, 30, "1")));
		assertThat(constantsOf("SELECT * FROM foo WHERE bar < 1"), are(constant(1, 30, "1")));
		assertThat(constantsOf("SELECT * FROM foo WHERE bar >= 1"), are(constant(1, 31, "1")));
		assertThat(constantsOf("SELECT * FROM foo WHERE bar <= 1"), are(constant(1, 31, "1")));
		assertThat(constantsOf("SELECT * FROM foo WHERE bar != 1"), are(constant(1, 31, "1")));
		assertThat(constantsOf("SELECT * FROM foo WHERE bar = 1 + 2"), are(constant(1, 30, "1"), constant(1, 34, "2")));
		assertThat(constantsOf("SELECT * FROM foo WHERE bar = 1 - 2"), are(constant(1, 30, "1"), constant(1, 34, "2")));
		assertThat(constantsOf("SELECT * FROM foo WHERE bar = 1 * 2"), are(constant(1, 30, "1"), constant(1, 34, "2")));
		assertThat(constantsOf("SELECT * FROM foo WHERE bar = 1 / 2"), are(constant(1, 30, "1"), constant(1, 34, "2")));
		assertThat(constantsOf("SELECT * FROM foo WHERE bar = 'x'"), are(constant(1, 30, "'x'")));
		assertThat(constantsOf("SELECT * FROM foo WHERE bar = 'x' + 'y'"),
				are(constant(1, 30, "'x'"), constant(1, 36, "'y'")));
		assertThat(constantsOf("UPDATE foo SET bar = bar WHERE bar IS NULL"), isEmpty());
		assertThat(constantsOf("UPDATE foo SET bar = bar WHERE bar IS NOT NULL"), isEmpty());
		assertThat(constantsOf("UPDATE foo SET bar = bar WHERE bar = NULL"), isEmpty());
		assertThat(constantsOf("UPDATE foo SET bar = bar WHERE bar != NULL"), isEmpty());
		assertThat(constantsOf("UPDATE foo SET bar = bar WHERE bar <> NULL"), isEmpty());
		assertThat(constantsOf("UPDATE foo SET bar = bar WHERE bar = 1"), are(constant(1, 37, "1")));
		assertThat(constantsOf("UPDATE foo SET bar = bar WHERE bar = 1 OR bar = 2"),
				are(constant(1, 37, "1"), constant(1, 48, "2")));
		assertThat(constantsOf("UPDATE foo SET bar = bar WHERE bar = 1 AND bar = 2"),
				are(constant(1, 37, "1"), constant(1, 49, "2")));
		assertThat(constantsOf("UPDATE foo SET bar = bar WHERE bar BETWEEN 1 AND 2"),
				are(constant(1, 43, "1"), constant(1, 49, "2")));
		assertThat(constantsOf("UPDATE foo SET bar = bar WHERE bar LIKE '%x%'"), are(constant(1, 40, "'%x%'")));
		assertThat(constantsOf("UPDATE foo SET bar = bar WHERE bar LIKE '_x_'"), are(constant(1, 40, "'_x_'")));
		assertThat(constantsOf("UPDATE foo SET bar = bar WHERE bar IN (1, 2)"),
				are(constant(1, 39, "1"), constant(1, 42, "2")));
		assertThat(constantsOf("UPDATE foo SET bar = bar WHERE bar <> 1"), are(constant(1, 38, "1")));
		assertThat(constantsOf("UPDATE foo SET bar = bar WHERE bar > 1"), are(constant(1, 37, "1")));
		assertThat(constantsOf("UPDATE foo SET bar = bar WHERE bar < 1"), are(constant(1, 37, "1")));
		assertThat(constantsOf("UPDATE foo SET bar = bar WHERE bar >= 1"), are(constant(1, 38, "1")));
		assertThat(constantsOf("UPDATE foo SET bar = bar WHERE bar <= 1"), are(constant(1, 38, "1")));
		assertThat(constantsOf("UPDATE foo SET bar = bar WHERE bar != 1"), are(constant(1, 38, "1")));
		assertThat(constantsOf("UPDATE foo SET bar = bar WHERE bar = 1 + 2"),
				are(constant(1, 37, "1"), constant(1, 41, "2")));
		assertThat(constantsOf("UPDATE foo SET bar = bar WHERE bar = 1 - 2"),
				are(constant(1, 37, "1"), constant(1, 41, "2")));
		assertThat(constantsOf("UPDATE foo SET bar = bar WHERE bar = 1 * 2"),
				are(constant(1, 37, "1"), constant(1, 41, "2")));
		assertThat(constantsOf("UPDATE foo SET bar = bar WHERE bar = 1 / 2"),
				are(constant(1, 37, "1"), constant(1, 41, "2")));
		assertThat(constantsOf("UPDATE foo SET bar = bar WHERE bar = 'x'"), are(constant(1, 37, "'x'")));
		assertThat(constantsOf("UPDATE foo SET bar = bar WHERE bar = 'x' + 'y'"),
				are(constant(1, 37, "'x'"), constant(1, 43, "'y'")));
		assertThat(constantsOf("UPDATE foo SET bar = NULL"), isEmpty());
		assertThat(constantsOf("UPDATE foo SET bar = 1"), are(constant(1, 21, "1")));
		assertThat(constantsOf("UPDATE foo SET bar = 1 + 2"), are(constant(1, 21, "1"), constant(1, 25, "2")));
		assertThat(constantsOf("UPDATE foo SET bar = 1 - 2"), are(constant(1, 21, "1"), constant(1, 25, "2")));
		assertThat(constantsOf("UPDATE foo SET bar = 1 * 2"), are(constant(1, 21, "1"), constant(1, 25, "2")));
		assertThat(constantsOf("UPDATE foo SET bar = 1 / 2"), are(constant(1, 21, "1"), constant(1, 25, "2")));
		assertThat(constantsOf("UPDATE foo SET bar = 'x'"), are(constant(1, 21, "'x'")));
		assertThat(constantsOf("UPDATE foo SET bar = 'x' + 'y'"), are(constant(1, 21, "'x'"), constant(1, 27, "'y'")));
		assertThat(constantsOf("INSERT INTO foo SET (bar) VALUES (1)"), are(constant(1, 34, "1")));
		assertThat(constantsOf("INSERT INTO foo SET (bar) VALUES (1 + 2)"),
				are(constant(1, 34, "1"), constant(1, 38, "2")));
		assertThat(constantsOf("INSERT INTO foo SET (bar) VALUES (1 - 2)"),
				are(constant(1, 34, "1"), constant(1, 38, "2")));
		assertThat(constantsOf("INSERT INTO foo SET (bar) VALUES (1 * 2)"),
				are(constant(1, 34, "1"), constant(1, 38, "2")));
		assertThat(constantsOf("INSERT INTO foo SET (bar) VALUES (1 / 2)"),
				are(constant(1, 34, "1"), constant(1, 38, "2")));
		assertThat(constantsOf("INSERT INTO foo SET (bar) VALUES ('x')"), are(constant(1, 34, "'x'")));
		assertThat(constantsOf("INSERT INTO foo SET (bar) VALUES ('x' + 'y')"),
				are(constant(1, 34, "'x'"), constant(1, 40, "'y'")));
		assertThat(constantsOf("DELETE FROM foo WHERE bar = 1"), are(constant(1, 28, "1")));
		assertThat(constantsOf("DELETE FROM foo WHERE bar = 1 OR bar = 2"),
				are(constant(1, 28, "1"), constant(1, 39, "2")));
		assertThat(constantsOf("DELETE FROM foo WHERE bar = 1 AND bar = 2"),
				are(constant(1, 28, "1"), constant(1, 40, "2")));
		assertThat(constantsOf("DELETE FROM foo WHERE bar BETWEEN 1 AND 2"),
				are(constant(1, 34, "1"), constant(1, 40, "2")));
		assertThat(constantsOf("DELETE FROM foo WHERE bar LIKE '%x%'"), are(constant(1, 31, "'%x%'")));
		assertThat(constantsOf("DELETE FROM foo WHERE bar LIKE '_x_'"), are(constant(1, 31, "'_x_'")));
		assertThat(constantsOf("DELETE FROM foo WHERE bar IN (1, 2)"), are(constant(1, 30, "1"), constant(1, 33, "2")));
		assertThat(constantsOf("DELETE FROM foo WHERE bar <> 1"), are(constant(1, 29, "1")));
		assertThat(constantsOf("DELETE FROM foo WHERE bar > 1"), are(constant(1, 28, "1")));
		assertThat(constantsOf("DELETE FROM foo WHERE bar < 1"), are(constant(1, 28, "1")));
		assertThat(constantsOf("DELETE FROM foo WHERE bar >= 1"), are(constant(1, 29, "1")));
		assertThat(constantsOf("DELETE FROM foo WHERE bar <= 1"), are(constant(1, 29, "1")));
		assertThat(constantsOf("DELETE FROM foo WHERE bar != 1"), are(constant(1, 29, "1")));
		assertThat(constantsOf("DELETE FROM foo WHERE bar = 1 + 2"), are(constant(1, 28, "1"), constant(1, 32, "2")));
		assertThat(constantsOf("DELETE FROM foo WHERE bar = 1 - 2"), are(constant(1, 28, "1"), constant(1, 32, "2")));
		assertThat(constantsOf("DELETE FROM foo WHERE bar = 1 * 2"), are(constant(1, 28, "1"), constant(1, 32, "2")));
		assertThat(constantsOf("DELETE FROM foo WHERE bar = 1 / 2"), are(constant(1, 28, "1"), constant(1, 32, "2")));
		assertThat(constantsOf("DELETE FROM foo WHERE bar = 'x'"), are(constant(1, 28, "'x'")));
		assertThat(constantsOf("DELETE FROM foo WHERE bar = 'x' + 'y'"),
				are(constant(1, 28, "'x'"), constant(1, 34, "'y'")));
	}

}
