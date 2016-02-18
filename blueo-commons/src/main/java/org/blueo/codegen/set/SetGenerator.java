package org.blueo.codegen.set;

import java.util.Formatter;

public abstract class SetGenerator {
	protected Class<?> targetClass;
	protected String paramName;
	
	// -----------------------------
	// ----- Static Methods
	// -----------------------------

	public static void declaredFieldBased(Class<?> targetClass) {
		declaredFieldBased(targetClass, null);
	}
	
	public static void declaredFieldBased(Class<?> targetClass, String paramName) {
		new SetDeclaredFieldBased(targetClass, paramName).printToConsole();
	}
	
	public static void methodBased(Class<?> targetClass) {
		methodBased(targetClass, null);
	}
	
	public static void methodBased(Class<?> targetClass, String paramName) {
		new SetMethodBased(targetClass, paramName).printToConsole();
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

	public Class<?> getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SetGenerator [targetClass=");
		builder.append(targetClass);
		builder.append(", paramName=");
		builder.append(paramName);
		builder.append("]");
		return builder.toString();
	}

}
