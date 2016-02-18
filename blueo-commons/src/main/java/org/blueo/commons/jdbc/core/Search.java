package org.blueo.commons.jdbc.core;

import java.util.List;

public interface Search<T> {

	public List<T> findByExample(T t);

}
