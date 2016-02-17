package org.blueo.commons.jdbc.core.impl.crud;

import java.io.Serializable;
import java.util.Date;

import org.blueo.commons.jdbc.core.Crud;
import org.blueo.commons.jdbc.core.ReadType;
import org.blueo.commons.jdbc.core.impl.ParameterizedClass;
import org.blueo.commons.jdbc.core.po.CreateUpdateUserIdGetter;
import org.blueo.commons.jdbc.core.po.HasId;

public abstract class AbstractCrud<T extends HasId<K>, K extends Serializable, U> implements Crud<T, K> {
	protected ParameterizedClass<T> parameterizedClass = new ParameterizedClass<T>(){};
	protected CreateUpdateUserIdGetter<U> createUpdateUserIdGetter;
	
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
	
	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------
	
	public CreateUpdateUserIdGetter<U> getCreateUpdateUserIdGetter() {
		return createUpdateUserIdGetter;
	}

	public void setCreateUpdateUserIdGetter(CreateUpdateUserIdGetter<U> createUpdateUserIdGetter) {
		this.createUpdateUserIdGetter = createUpdateUserIdGetter;
	}

}