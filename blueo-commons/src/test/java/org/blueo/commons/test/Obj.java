package org.blueo.commons.test;

public class Obj {
	private int index;
	private int level;

	public Obj(int index, int level) {
		super();
		this.index = index;
		this.level = level;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Obj [index=");
		builder.append(index);
		builder.append(", level=");
		builder.append(level);
		builder.append("]");
		return builder.toString();
	}

}
