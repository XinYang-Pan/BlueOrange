package org.blueo.db.vo;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;

public class DbColumn {
	// From Excel
	private String name;
	private String type;
	private String length;
	private String pk;
	private String nullable;
	private String comment;
	private String enumType;
	
	public String getFullTypeStr() {
		return this.getSqlType().getFullTypeStr();
	}
	
	public DbType getDbType() {
		return DbType.of(this);
	}

	public void setDbType(DbType dbType) {
		this.type = dbType.getType();
		this.length = dbType.getLengthStr();
	}

	public Class<?> getJavaType() {
		return DbType.of(this).getJavaType();
	}

	public SqlType getSqlType() {
		return DbType.of(this).getSqlType();
	}

	public boolean isPkInBool() {
		return BooleanUtils.toBoolean(ObjectUtils.firstNonNull(pk, "false"));
	}
	
	public boolean isEnumTypeInBool() {
		return BooleanUtils.toBoolean(ObjectUtils.firstNonNull(enumType, "false"));
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
