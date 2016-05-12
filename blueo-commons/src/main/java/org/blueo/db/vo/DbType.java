package org.blueo.db.vo;

import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.blueo.commons.text.BlueoStrs;
import org.blueo.db.vo.raw.DbColumnRawData;
import org.blueo.pojogen.bo.wrapper.clazz.ClassWrapper;
import org.javatuples.Triplet;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.primitives.Ints;

public class DbType {
	private String rawType;
	private String sqlType;
	private Integer length;
	private Integer length2;
	private ClassWrapper javaType;

	public DbType(String type) {
		super();
		this.rawType = type;
	}

	public DbType(String type, Integer length) {
		super();
		this.rawType = type;
		this.length = length;
	}

	public DbType(String type, Integer length, Integer length2) {
		super();
		this.rawType = type;
		this.length = length;
		this.length2 = length2;
	}
	
	public static DbType of(DbColumnRawData dbColumnRawData) {
		DbType dbType = DbType.of(dbColumnRawData.getType(), dbColumnRawData.getLength());
		dbType.setSqlType(dbColumnRawData.getSqlType());
		return dbType;
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

	/**
	 * @param rawType
	 *            String
	 * @param lengthStr
	 *            (9, 0)
	 * @return
	 */
	public static DbType of(String rawType, String lengthStr) {
		if (lengthStr == null) {
			return new DbType(rawType);
		}
		Iterable<String> split = Splitter.on(',').omitEmptyStrings().trimResults().split(lengthStr);
		//
		DbType dbType = new DbType(rawType);
		Iterator<String> iterator = split.iterator();
		if (iterator.hasNext()) {
			dbType.length = Ints.tryParse(iterator.next());
		}
		if (iterator.hasNext()) {
			dbType.length2 = Ints.tryParse(iterator.next());
		}
		return dbType;
	}

	// varchar(9, 9)
	public String getFullTypeStr() {
		String length = this.getLengthStr();
		if (StringUtils.isBlank(length)) {
			return sqlType;
		} else {
			return String.format("%s(%s)", sqlType, length);
		}
	}

	// (9, 8)
	private String getLengthStr() {
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
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DbType [rawType=");
		builder.append(rawType);
		builder.append(", sqlType=");
		builder.append(sqlType);
		builder.append(", length=");
		builder.append(length);
		builder.append(", length2=");
		builder.append(length2);
		builder.append(", javaType=");
		builder.append(javaType);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((javaType == null) ? 0 : javaType.hashCode());
		result = prime * result + ((length == null) ? 0 : length.hashCode());
		result = prime * result + ((length2 == null) ? 0 : length2.hashCode());
		result = prime * result + ((rawType == null) ? 0 : rawType.hashCode());
		result = prime * result + ((sqlType == null) ? 0 : sqlType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		DbType other = (DbType) obj;
		if (javaType == null) {
			if (other.javaType != null) {
				return false;
			}
		} else if (!javaType.equals(other.javaType)) {
			return false;
		}
		if (length == null) {
			if (other.length != null) {
				return false;
			}
		} else if (!length.equals(other.length)) {
			return false;
		}
		if (length2 == null) {
			if (other.length2 != null) {
				return false;
			}
		} else if (!length2.equals(other.length2)) {
			return false;
		}
		if (rawType == null) {
			if (other.rawType != null) {
				return false;
			}
		} else if (!rawType.equals(other.rawType)) {
			return false;
		}
		if (sqlType == null) {
			if (other.sqlType != null) {
				return false;
			}
		} else if (!sqlType.equals(other.sqlType)) {
			return false;
		}
		return true;
	}

	public String getRawType() {
		return rawType;
	}

	public void setRawType(String type) {
		this.rawType = type;
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

	public String getSqlType() {
		return this.sqlType;
	}

	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}

	public ClassWrapper getJavaType() {
		return javaType;
	}

	public void setJavaType(ClassWrapper javaType) {
		this.javaType = javaType;
	}

}
