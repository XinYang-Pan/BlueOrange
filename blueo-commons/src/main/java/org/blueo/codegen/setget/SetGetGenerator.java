package org.blueo.codegen.setget;

import java.util.Formatter;

public abstract class SetGetGenerator {

	protected Class<?> setClass;
	protected Class<?> getClass;
	protected String setParamName;
	protected String getParamName;

	// -----------------------------
	// ----- Static Methods
	// -----------------------------

	public static void declaredFieldBased(Class<?> setClass, Class<?> getClass) {
		declaredFieldBased(setClass, getClass, null, null);
	}

	public static void declaredFieldBased(Class<?> setClass, Class<?> getClass, String setParamName, String getParamName) {
		new SetGetDeclaredFieldBased(setClass, getClass, setParamName, getParamName).printToConsole();
	}

	public static void methodBased(Class<?> setClass, Class<?> getClass) {
		methodBased(setClass, getClass, null, null);
	}

	public static void methodBased(Class<?> setClass, Class<?> getClass, String setParamName, String getParamName) {
		new SetGetMethodBased(setClass, getClass, setParamName, getParamName).printToConsole();
	}

	// -----------------------------
	// ----- Non-Static Methods
	// -----------------------------

	public abstract void generate(Formatter formatter);

	public void printToConsole() {
		this.generate(new Formatter(System.out));
	}

	public String generateString() {
		StringBuilder sb = new StringBuilder();
		this.generate(new Formatter(sb));
		return sb.toString();
	}

	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------

	public Class<?> getSetClass() {
		return setClass;
	}

	public void setSetClass(Class<?> set) {
		this.setClass = set;
	}

	public Class<?> getGetClass() {
		return getClass;
	}

	public void setGetClass(Class<?> get) {
		this.getClass = get;
	}

	public String getSetParamName() {
		return setParamName;
	}

	public void setSetParamName(String setParamName) {
		this.setParamName = setParamName;
	}

	public String getGetParamName() {
		return getParamName;
	}

	public void setGetParamName(String getParamName) {
		this.getParamName = getParamName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SetGetGenerator [setClass=");
		builder.append(setClass);
		builder.append(", getClass=");
		builder.append(getClass);
		builder.append(", setParamName=");
		builder.append(setParamName);
		builder.append(", getParamName=");
		builder.append(getParamName);
		builder.append("]");
		return builder.toString();
	}

}
