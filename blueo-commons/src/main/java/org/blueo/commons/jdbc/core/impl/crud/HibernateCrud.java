package org.blueo.commons.jdbc.core.impl.crud;

import java.io.Serializable;

import org.apache.commons.lang3.BooleanUtils;
import org.blueo.commons.jdbc.core.ReadType;
import org.blueo.commons.jdbc.core.po.HasId;
import org.blueo.commons.jdbc.core.po.TraceablePo;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.google.common.base.Preconditions;

public class HibernateCrud<T extends HasId<K>, K extends Serializable, U> extends AbstractCrud<T, K, U> {
	// 
	protected HibernateTemplate hibernateTemplate;
	
	@Override
	public T getById(Serializable id, ReadType type) {
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
	    if (t instanceof TraceablePo<?>) {
	    	TraceablePo<U> traceablePo = (TraceablePo<U>) t;
	    	if (traceablePo.getCreateTime() == null) {
				traceablePo.setCreateTime(this.now());
	    	}
	    	if (traceablePo.getUpdateTime() == null) {
	    		traceablePo.setUpdateTime(this.now());
	    	}
	    	if (traceablePo.getDelFlag() == null) {
	    		traceablePo.setDelFlag(false);
	    	}
	    	if (traceablePo.getCreateId() == null) {
	    		traceablePo.setCreateId(this.getUserId());
	    	}
	    	if (traceablePo.getUpdateId() == null) {
	    		traceablePo.setUpdateId(this.getUserId());
	    	}
	    }
		return (K) hibernateTemplate.save(t);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void update(T t) {
		if (t == null) {
			return;
		}
	    if (t instanceof TraceablePo<?>) {
	    	TraceablePo<U> traceablePo = (TraceablePo<U>) t;
    		traceablePo.setUpdateTime(this.now());
    		traceablePo.setUpdateId(this.getUserId());
        }
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
	@SuppressWarnings("unchecked")
	public void delete(T t) {
		if (t == null) {
			return;
		}
	    if (t instanceof TraceablePo<?>) {
	    	TraceablePo<U> traceablePo = (TraceablePo<U>) t;
	    	traceablePo.setDelFlag(true);
            this.update(t);
        } else {
            hibernateTemplate.delete(t);
        } 
	}
	
}
