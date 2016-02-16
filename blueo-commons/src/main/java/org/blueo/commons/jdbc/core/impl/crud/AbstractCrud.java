package org.blueo.commons.jdbc.core.impl.crud;

import java.io.Serializable;
import java.util.Date;

import org.blueo.commons.jdbc.core.Crud;
import org.blueo.commons.jdbc.core.ReadType;
import org.blueo.commons.jdbc.core.impl.ParameterizedClass;
import org.blueo.commons.jdbc.core.po.HasId;
import org.blueo.commons.jdbc.core.po.ThreadLocalGetter;

public abstract class AbstractCrud<T extends HasId<K>, K extends Serializable, U> extends ThreadLocalGetter<U> implements Crud<T, K> {
	protected final ParameterizedClass<T> parameterizedClass = new ParameterizedClass<T>(){};

	@Override
	public T getById(K id) {
		return this.getById(id, ReadType.Active);
	}

	@Override
	public void deleteById(K id) {
		T entity = this.getById(id);
		if (entity != null) {
			this.delete(entity);
		}
	}

	protected Date now() {
		return new Date();
	}

}