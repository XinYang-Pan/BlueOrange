package org.blueo.commons.persistent.hibernate.impl;

import java.util.List;

import org.blueo.commons.persistent.dao.Search;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class HibernateSearch<T> implements Search<T> {
	//
	protected HibernateTemplate hibernateTemplate;

	@Override
	public List<T> findByExample(T t) {
		return hibernateTemplate.findByExample(t);
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

}
