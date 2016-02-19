package org.blueo.commons.persistent.hibernate.impl;

import java.util.List;

import org.blueo.commons.persistent.core.dao.Search;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class HibernateSearch<T> implements Search<T> {
	//
	protected HibernateTemplate hibernateTemplate;

	@Override
	public List<T> findByExample(T t) {
		return hibernateTemplate.findByExample(t);
	}

}
