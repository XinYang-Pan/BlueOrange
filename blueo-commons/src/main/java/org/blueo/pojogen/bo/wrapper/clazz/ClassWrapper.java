package org.blueo.pojogen.bo.wrapper.clazz;

import org.apache.commons.lang3.ClassUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

public class ClassWrapper {
	private final String packageName;
	private final String name;
	
	private ClassWrapper(String clazzFullName) {
		packageName = Strings.emptyToNull(ClassUtils.getPackageName(clazzFullName));
		name = ClassUtils.getShortClassName(clazzFullName);
	}
	
	public static ClassWrapper of(Class<?> clazz) {
		return of(clazz.getName());
	}
	
	public static ClassWrapper of(String clazzFullName) {
		return new ClassWrapper(clazzFullName);
	}
	
	public Class<?> getClassIfPossible() {
		try {
			return ClassUtils.getClass(this.getFullName());
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	public String getFullName() {
		return Joiner.on('.').skipNulls().join(packageName, name);
	}
	
	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((packageName == null) ? 0 : packageName.hashCode());
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
		ClassWrapper other = (ClassWrapper) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (packageName == null) {
			if (other.packageName != null)
				return false;
		} else if (!packageName.equals(other.packageName))
			return false;
		return true;
	}
	
	public String getPackageName() {
		return packageName;
	}


	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ClassWrapper [packageName=");
		builder.append(packageName);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

}
