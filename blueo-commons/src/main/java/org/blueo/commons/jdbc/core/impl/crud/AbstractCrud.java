package org.blueo.commons.jdbc.core.impl.crud;

import java.io.Serializable;
import java.util.Date;

import org.blueo.commons.jdbc.core.Crud;
import org.blueo.commons.jdbc.core.DelFlagType;
import org.blueo.commons.jdbc.core.impl.ParameterizedClass;
import org.blueo.commons.jdbc.core.po.HasId;
import org.blueo.commons.jdbc.core.traceable.TraceablePoOverwriter;

public abstract class AbstractCrud<T extends HasId<K>, K extends Serializable, U> implements Crud<T, K> {
	protected ParameterizedClass<T> parameterizedClass = new ParameterizedClass<T>(){};
	protected TraceablePoOverwriter<U> traceablePoOverwriter;
	
	@Override
	public T getById(K id) {
		return this.getById(id, DelFlagType.Active);
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

	public TraceablePoOverwriter<U> getTraceablePoOverwriter() {
		return traceablePoOverwriter;
	}

	public void setTraceablePoOverwriter(TraceablePoOverwriter<U> traceablePoOverwriter) {
		this.traceablePoOverwriter = traceablePoOverwriter;
	}
	

}