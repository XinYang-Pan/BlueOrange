package org.blueo.commons.jdbc.core.impl.crud;

import java.io.Serializable;

import org.blueo.commons.jdbc.core.Crud;
import org.blueo.commons.jdbc.core.impl.ParameterizedClass;
import org.blueo.commons.jdbc.core.po.HasId;

public abstract class AbstractCrud<T extends HasId<K>, K extends Serializable, U> implements Crud<T, K> {
	protected ParameterizedClass<T> parameterizedClass = new ParameterizedClass<T>(){};

	@Override
	public void deleteById(K id) {
		T entity = this.getById(id);
		if (entity != null) {
			this.delete(entity);
		}
	}

}