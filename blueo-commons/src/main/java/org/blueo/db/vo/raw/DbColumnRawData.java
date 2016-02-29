package org.blueo.db.vo.raw;


public class DbColumnRawData {
	
	private String name;
	private String type;
	private String sqlType;
	private String length;
	private String pk;
	private String nullable;
	private String comment;
	private String enumType;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DbColumn [name=");
		builder.append(name);
		builder.append(", type=");
		builder.append(type);
		builder.append(", length=");
		builder.append(length);
		builder.append(", pk=");
		builder.append(pk);
		builder.append(", nullable=");
		builder.append(nullable);
		builder.append(", comment=");
		builder.append(comment);
		builder.append(", enumType=");
		builder.append(enumType);
		builder.append("]");
		return builder.toString();
	}
	
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

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getEnumType() {
		return enumType;
	}

	public void setEnumType(String enumType) {
		this.enumType = enumType;
	}

	public String getSqlType() {
		return sqlType;
	}

	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}

}
