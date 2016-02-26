package org.blueo.db.vo;

import java.util.Iterator;

import org.blueo.commons.text.BlueoStrs;
import org.javatuples.Triplet;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.primitives.Ints;

public class DbType {
	private String type;
	private Integer length;
	private Integer length2;

	public static DbType of(DbColumn dbColumn) {
		return of(dbColumn.getType(), dbColumn.getLength());
	}

	public static DbType of(String fullTypeName) {
		Triplet<String, String, String> texts = BlueoStrs.parse(fullTypeName, '(', ')');
		String parameterizedTypeStr = texts.getValue0();
		String typeName = texts.getValue1();
		if (parameterizedTypeStr == null) {
			return new DbType(typeName);
		}
		return of(typeName, parameterizedTypeStr);

	}

	public static DbType of(String typeName, String length) {
		if (length == null) {
			return new DbType(typeName);
		}
		Iterable<String> split = Splitter.on(',').omitEmptyStrings().trimResults().split(length);
		//
		DbType dbType = new DbType(typeName);
		Iterator<String> iterator = split.iterator();
		if (iterator.hasNext()) {
			dbType.length = Ints.tryParse(iterator.next());
		}
		if (iterator.hasNext()) {
			dbType.length2 = Ints.tryParse(iterator.next());
		}
		return dbType;
	}
	
	public Class<?> getJavaType() {
		return TypeToJavaTypeMapping.getJavaType(this);
	}
	
	public SqlType getSqlType() {
		return TypeToDbTypeMapping.getJavaType(this);
	}
	
	public String getLengthStr() {
		if (length != null) {
			if (length2 != null) {
				return Joiner.on(',').join(length, length2);
			} else {
				return length.toString();
			}
		} else {
			if (length2 != null) {
				return Joiner.on(',').join(null, length2);
			} else {
				return null;
			}
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((length == null) ? 0 : length.hashCode());
		result = prime * result + ((length2 == null) ? 0 : length2.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DbType other = (DbType) obj;
		if (length == null) {
			if (other.length != null)
				return false;
		} else if (!length.equals(other.length))
			return false;
		if (length2 == null) {
			if (other.length2 != null)
				return false;
		} else if (!length2.equals(other.length2))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public DbType(String type) {
		super();
		this.type = type;
	}

	public DbType(String type, Integer length) {
		super();
		this.type = type;
		this.length = length;
	}

	public DbType(String type, Integer length, Integer length2) {
		super();
		this.type = type;
		this.length = length;
		this.length2 = length2;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getLength2() {
		return length2;
	}

	public void setLength2(Integer length2) {
		this.length2 = length2;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DbType [type=");
		builder.append(type);
		builder.append(", length=");
		builder.append(length);
		builder.append(", length2=");
		builder.append(length2);
		builder.append("]");
		return builder.toString();
	}

}
