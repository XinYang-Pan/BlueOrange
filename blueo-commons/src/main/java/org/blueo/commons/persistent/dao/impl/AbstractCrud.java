package org.blueo.commons.persistent.dao.impl;

import java.io.Serializable;

import org.blueo.commons.BlueoUtils;
import org.blueo.commons.persistent.dao.Crud;

import com.google.common.reflect.TypeToken;

public abstract class AbstractCrud<T, K extends Serializable> implements Crud<T, K> {
	@SuppressWarnings("serial")
	protected final Class<T> parameterizedClass = BlueoUtils.getParameterizedClass(new TypeToken<T>(this.getClass()) {});

	@Override
	public void deleteById(K id) {
		T entity = this.getById(id);
		if (entity != null) {
			this.delete(entity);
		}
	}

}