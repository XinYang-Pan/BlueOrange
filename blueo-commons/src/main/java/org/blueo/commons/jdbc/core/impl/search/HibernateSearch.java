package org.blueo.commons.jdbc.core.impl.search;

import java.util.List;

import org.blueo.commons.BlueoUtils;
import org.blueo.commons.jdbc.core.Search;
import org.blueo.commons.jdbc.core.impl.ParameterizedClass;
import org.blueo.commons.jdbc.core.po.TraceablePo;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.google.common.base.Throwables;

public class HibernateSearch<T> implements Search<T> {
	protected ParameterizedClass<T> parameterizedClass = new ParameterizedClass<T>(){};
	//
	protected HibernateTemplate hibernateTemplate;

	@Override
	public T findByExample(T t, boolean nullableResult) {
		List<T> findByExample = this.findByExample(t);
		if (nullableResult) {
			return BlueoUtils.oneOrNull(findByExample);
		} else {
			return BlueoUtils.oneNoNull(findByExample);
		}
	}

	@Override
	public List<T> findByExample(T t) {
		if (t instanceof TraceablePo<?>) {
			TraceablePo<?> traceablePo = (TraceablePo<?>) t;
			traceablePo.setDelFlag(true);
		}
		return hibernateTemplate.findByExample(t);
	}

	@Override
	public List<T> findAll() {
		Class<T> clazz = parameterizedClass.getParameterizedClass();
		try {
			T t = clazz.newInstance();
			return this.findByExample(t);
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
	}

}
