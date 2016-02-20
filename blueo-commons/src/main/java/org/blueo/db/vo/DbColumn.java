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

	public String alterAdd(String tableName) {
		return String.format("ALTER TABLE %s ADD %s;", tableName, getDefinitionSql());
	}

	public String alterDrop(String tableName) {
		return String.format("ALTER TABLE %s DROP COLUMN %s;", tableName, name);
	}

	public String alterModify(String tableName) {
		return String.format("ALTER TABLE %s MODIFY %s;", tableName, getDefinitionSql());
	}
	
	public String getDefinitionSql() {
		String nullable;
		if (this.isPkInBool()) {
			nullable = "NOT NULL";
		} else {
			nullable = this.isNullableInBool()? "NULL":"NOT NULL";
		}
		return String.format("%s %s %s", name, this.getTypeWithLengthStr(), nullable);
	}
	
	public String oneLineOfCreateSql(boolean lastLine, boolean withComments) {
		String lastLineStr = "";
		if (!lastLine) {
			lastLineStr = ",";
		}
		String comment = "";
		if (withComments && this.getComment() != null) {
			comment = String.format(" -- %s", this.getComment());
		} 
		return String.format("%s%s%s", getDefinitionSql(), lastLineStr, comment);
	}
	
	private String getTypeWithLengthStr() {
		String size = this.getLength();
		if (StringUtils.isBlank(size)) {
			return type;
		} else {
			return String.format("%s(%s)", type, size);
		}
	}
	
	// 
	
	public boolean isPkInBool() {
		return BooleanUtils.toBoolean(ObjectUtils.firstNonNull(pk, "false"));
	}

	public boolean isNullableInBool() {
		return BooleanUtils.toBoolean(ObjectUtils.firstNonNull(pk, "true"));
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
