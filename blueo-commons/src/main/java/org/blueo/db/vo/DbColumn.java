package org.blueo.db.vo;

public class DbColumn {
	private String name;
	private String type;
	private String size;
	private boolean pk;
	private boolean nullable;
	private String comment;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public boolean isPk() {
		return pk;
	}

	public void setPk(boolean pk) {
		this.pk = pk;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DbColumn [name=");
		builder.append(name);
		builder.append(", type=");
		builder.append(type);
		builder.append(", size=");
		builder.append(size);
		builder.append(", pk=");
		builder.append(pk);
		builder.append(", nullable=");
		builder.append(nullable);
		builder.append(", comment=");
		builder.append(comment);
		builder.append("]");
		return builder.toString();
	}

}
