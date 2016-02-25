package org.blueo.commons.persistent.hibernate.impl;

import java.io.Serializable;

import org.blueo.commons.persistent.core.dao.impl.AbstractCrud;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class HibernateCrud<T, K extends Serializable> extends AbstractCrud<T, K> {
	//
	protected HibernateTemplate hibernateTemplate;

	@Override
	public T getById(K id) {
		return hibernateTemplate.get(parameterizedClass, id);
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

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

}
