package org.blueo.commons.jdbc.core.impl.crud;

import java.io.Serializable;

import org.blueo.commons.jdbc.core.po.HasId;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class HibernateCrud<T extends HasId<K>, K extends Serializable, U> extends AbstractCrud<T, K, U> {
	// 
	protected HibernateTemplate hibernateTemplate;
	
	@Override
	public T getById(K id) {
		return hibernateTemplate.get(parameterizedClass.getParameterizedClass(), id);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public K save(T t) {
		if (t == null) {
			return null;
		}
		return (K) hibernateTemplate.save(t);
	}

	@Override
	public void update(T t) {
		if (t == null) {
			return;
		}
		hibernateTemplate.update(t);
	}

	@Override
	public void delete(T t) {
		if (t == null) {
			return;
		}
        hibernateTemplate.delete(t);
	}
	
}
