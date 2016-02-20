package org.blueo.db.vo;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

public class DbColumn {
	private String name;
	private String type;
	private String length;
	private String pk;
	private String nullable;
	private String comment;
	private String enumType;
	
	public String getFullTypeStr() {
		String length = this.getLength();
		if (StringUtils.isBlank(length)) {
			return this.getType();
		} else {
			return String.format("%s(%s)", this.getType(), length);
		}
	}
	
	public SqlType getSqlType() {
		return SqlType.of(this);
	}

	public void setSqlType(SqlType sqlType) {
		this.type = sqlType.getType();
		this.length = sqlType.getLengthStr();
	}

	public Class<?> getJavaType() {
		return SqlType.of(this).getJavaType();
	}

	public boolean isPkInBool() {
		return BooleanUtils.toBoolean(ObjectUtils.firstNonNull(pk, "false"));
	}

	public boolean isNullableInBool() {
		return BooleanUtils.toBoolean(ObjectUtils.firstNonNull(nullable, "true"));
	}

	public void setPkInBool(boolean pkInBool) {
		this.pk = Boolean.toString(pkInBool);
	}

	public void setNullableInBool(boolean nullableInBool) {
		this.nullable = Boolean.toString(nullableInBool);
	}

	//

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

}
