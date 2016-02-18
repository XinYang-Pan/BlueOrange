package org.blueo.commons.jdbc.core.impl.search;

import java.util.List;

import org.blueo.commons.jdbc.core.Search;
import org.blueo.commons.jdbc.core.impl.ParameterizedClass;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class HibernateSearch<T> implements Search<T> {
	protected ParameterizedClass<T> parameterizedClass = new ParameterizedClass<T>(){};
	//
	protected HibernateTemplate hibernateTemplate;

	@Override
	public List<T> findByExample(T t) {
		return hibernateTemplate.findByExample(t);
	}

}
