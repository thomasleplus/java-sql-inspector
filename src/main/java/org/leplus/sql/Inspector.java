package org.leplus.sql;

import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.leplus.antlr4.PLSQLLexer;
import org.leplus.antlr4.PLSQLParser;

public class Inspector {

	public Inspector() {
		super();
	}

	public List<Result> findConstants(String sqlStatement) {
		final PLSQLLexer lexer = new PLSQLLexer(new ANTLRInputStream(sqlStatement));
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
