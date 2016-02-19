package org.blueo.commons.persistent.core.dao;

import java.util.List;

public interface Search<T> {

	public List<T> findByExample(T t);

}
