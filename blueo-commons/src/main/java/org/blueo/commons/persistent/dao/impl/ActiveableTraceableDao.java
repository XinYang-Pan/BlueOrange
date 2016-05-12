package org.blueo.commons.persistent.dao.impl;

import org.blueo.commons.persistent.dao.po.activeable.ActiveablePo;
import org.blueo.commons.persistent.dao.po.traceable.TraceablePo;

public class ActiveableTraceableDao<T extends ActiveablePo & TraceablePo<U>, K, U> extends EntityDaoAdaptor<T, K> {

	public ActiveableTraceableDao() {
		super();
	}

	public ActiveableTraceableDao(Class<T> parameterizedClass) {
		super(parameterizedClass);
	}

}
