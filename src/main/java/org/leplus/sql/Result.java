package org.leplus.sql;

import java.io.Serializable;

public class Result implements Cloneable, Serializable, Comparable<Result> {

	private static final long serialVersionUID = 2722874450358413200L;

	private String value;
	private int line;
	private int column;

	public Result() {
		super();
	}

	@Override
	public int compareTo(Result o) {
		if (line != o.line) {
			return line - o.line;
		}
		if (column != o.column) {
			return column - o.column;
		}
		return value.compareTo(o.value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Result other = (Result) obj;
		if (column != other.column) {
			return false;
		}
		if (line != other.line) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	public int getColumn() {
		return column;
	}

	public int getLine() {
		return line;
	}

	public String getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + line;
		result = prime * result + (value == null ? 0 : value.hashCode());
		return result;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return line + ":" + column + ":" + value;
	}

	@Override
	protected Result clone() {
		try {
			return (Result) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError(e);
		}
	}

}
