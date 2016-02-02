package org.blueo.commons.tostring;

public abstract class ToStringWrapper {
	private final Object target;

	public ToStringWrapper(Object target) {
		super();
		this.target = target;
	}
	
	@Override
	public String toString() {
		if (target == null) {
			return "null";
		}
		return this.notNulltoString(target);
	}
	
	public abstract String notNulltoString(Object target);
	
}
