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
		assertThat(constantsOf("select * from foo"), isEmpty());
		assertThat(constantsOf("select * from foo where bar = 1"),
				are(constant(1, 30, "1")));
		assertThat(constantsOf("select * from foo where bar = 1 or bar = 2"),
				are(constant(1, 30, "1"), constant(1, 41, "2")));
	}

}
