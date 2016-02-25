package org.blueo.commons.persistent.entity;

import java.lang.reflect.Method;

import org.blueo.commons.persistent.core.dao.po.id.HasId;
import org.blueo.commons.persistent.core.dao.po.id.HasIdIdWrapper;
import org.blueo.commons.persistent.core.dao.po.id.IdWrapper;
import org.springframework.util.ReflectionUtils;

import com.google.common.base.Preconditions;

public class EntityUtils {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T, K> IdWrapper<T, K> idGetter(Class<T> clazz) {
		Preconditions.checkNotNull(clazz);
		if (HasId.class.isAssignableFrom(clazz)) {
			return new HasIdIdWrapper();
		}
		final BoColumn idCol = BoTable.annotationBased(clazz).getIdCol();
		return new IdWrapper<T, K>() {

			@Override
			public K getId(T t) {
				Method readMethod = idCol.getPropertyDescriptor().getReadMethod();
				return (K) ReflectionUtils.invokeMethod(readMethod, t);
			}

			@Override
			public void setId(T t, K k) {
				Method writeMethod = idCol.getPropertyDescriptor().getWriteMethod();
				ReflectionUtils.invokeMethod(writeMethod, t, k);
			}
		};
	}

}
