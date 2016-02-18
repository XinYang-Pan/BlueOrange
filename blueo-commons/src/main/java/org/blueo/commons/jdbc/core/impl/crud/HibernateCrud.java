package org.blueo.commons.jdbc.core.impl.crud;

import java.io.Serializable;

import org.apache.commons.lang3.BooleanUtils;
import org.blueo.commons.jdbc.core.DelFlagType;
import org.blueo.commons.jdbc.core.po.HasId;
import org.blueo.commons.jdbc.core.traceable.TraceablePo;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.google.common.base.Preconditions;

public class HibernateCrud<T extends HasId<K>, K extends Serializable, U> extends AbstractCrud<T, K, U> {
	// 
	protected HibernateTemplate hibernateTemplate;
	
	@Override
	public T getById(Serializable id, DelFlagType type) {
		Preconditions.checkNotNull(id);
		Preconditions.checkNotNull(type);
		// 
		T t = hibernateTemplate.get(parameterizedClass.getParameterizedClass(), id);
		if (t == null) {
			return t;
		}
		if (!(t instanceof TraceablePo<?>)) {
			return t;
		}
		// None null traceable PO
		TraceablePo<?> traceablePo = (TraceablePo<?>) t;
		switch (type) {
		case All:
			return t;
		case Active:
			if (BooleanUtils.isNotTrue(traceablePo.getDelFlag())) {
				return t;
			}
			break;
		case Del:
			if (BooleanUtils.isTrue(traceablePo.getDelFlag())) {
				return t;
			}
			break;
		default:
			break;
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public K save(T t) {
		if (t == null) {
			return null;
		}
	    traceablePoOverwriter.saveOverwrite(t);
		return (K) hibernateTemplate.save(t);
	}

	@Override
	public void update(T t) {
		if (t == null) {
			return;
		}
		traceablePoOverwriter.updateOverwrite(t);
		hibernateTemplate.update(t);
	}

	@Override
	public void saveOrUpdate(T t) {
		if (t == null) {
			return;
		}
		if (t.getId() == null) {
			this.save(t);
		} else {
			this.update(t);
		}
	}

	@Override
	public void delete(T t) {
		if (t == null) {
			return;
		}
	    if (traceablePoOverwriter.deleteOverwrite(t)) {
            this.update(t);
        } else {
            hibernateTemplate.delete(t);
        } 
	}
	
}
