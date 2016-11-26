package org.leplus.sql;

import java.util.ArrayList;
import java.util.List;

import org.leplus.antlr4.PLSQLBaseListener;
import org.leplus.antlr4.PLSQLParser.ConstantContext;

public class ConstantListener extends PLSQLBaseListener {

	private final List<Result> results = new ArrayList<>();

	private void addResult(ConstantContext ctx) {
		final Result result = new Result();
		result.setValue(ctx.getText());
		result.setLine(ctx.getStart().getLine());
		result.setColumn(ctx.getStart().getStartIndex());
		results.add(result);
	}

	@Override
	public void enterConstant(ConstantContext ctx) {
		addResult(ctx);
	}

	public List<Result> getResults() {
		return results;
	}

}