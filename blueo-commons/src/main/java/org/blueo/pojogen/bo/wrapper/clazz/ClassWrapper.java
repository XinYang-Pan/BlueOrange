package org.blueo.pojogen.bo.wrapper.clazz;

import java.util.List;

import org.apache.commons.lang3.ClassUtils;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

public class ClassWrapper {
	private final String packageName;
	private final String name;
	private final List<ClassWrapper> parameterizedTypes;

	ClassWrapper(String clazzFullName, List<ClassWrapper> parameterizedTypes) {
		this.packageName = Strings.emptyToNull(ClassUtils.getPackageName(clazzFullName));
		this.name = ClassUtils.getShortClassName(clazzFullName);
		this.parameterizedTypes = parameterizedTypes;
	}

	// -----------------------------
	// ----- Static Methods
	// -----------------------------

	public static ClassWrapper of(Class<?> clazz) {
		return of(clazz, (Class<?>[]) null);
	}

	public static ClassWrapper of(Class<?> clazz, Class<?>... parameterizedTypes) {
		if (parameterizedTypes == null) {
			return of(clazz.getName());
		} else {
			List<ClassWrapper> classWrappers = Lists.newArrayList();
			for (Class<?> parameterizedType : parameterizedTypes) {
				classWrappers.add(ClassWrapper.of(parameterizedType.getName()));
			}
			return new ClassWrapper(clazz.getName(), classWrappers);
		}
	}

	public static ClassWrapper of(Class<?> clazz, String... parameterizedTypes) {
		if (parameterizedTypes == null) {
			return new ClassWrapper(clazz.getName(), null);
		} else {
			List<ClassWrapper> ClassWrappers = Lists.newArrayList();
			for (String parameterizedType : parameterizedTypes) {
				ClassWrappers.add(ClassWrapper.of(parameterizedType));
			}
			return new ClassWrapper(clazz.getName(), ClassWrappers);
		}
	}

	public static ClassWrapper of(String packageName, String className) {
		return of(Joiner.on('.').skipNulls().join(packageName, className));
	}

	public static ClassWrapper of(String classNameWithParameterizedTypes) {
		return ClassWrapperParser.parse(classNameWithParameterizedTypes);
	}

	// -----------------------------
	// ----- Non-Static Methods
	// -----------------------------

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

	public String getTypedName() {
		if (!CollectionUtils.isEmpty(parameterizedTypes)) {
			List<String> names = Lists.newArrayList();
			for (ClassWrapper wrapper : parameterizedTypes) {
				names.add(wrapper.getName());
			}
			String parameterizedTypesuffix = Joiner.on(", ").join(names);
			return String.format("%s<%s>", this.getName(), parameterizedTypesuffix);
		} else {
			return this.getName();
		}
	}

	public List<ClassWrapper> getImports() {
		List<ClassWrapper> classWrappers = Lists.newArrayList();
		classWrappers.add(this);
		if (!CollectionUtils.isEmpty(parameterizedTypes)) {
			for (ClassWrapper wrapper : parameterizedTypes) {
				classWrappers.addAll(wrapper.getImports());
			}
		}
		return classWrappers;
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
		builder.append(", parameterizedTypes=");
		builder.append(parameterizedTypes);
		builder.append("]");
		return builder.toString();
	}

}
