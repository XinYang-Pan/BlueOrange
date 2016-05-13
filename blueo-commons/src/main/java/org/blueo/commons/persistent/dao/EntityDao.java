package org.blueo.commons.persistent.dao;

import java.util.List;

public interface EntityDao<T, K> extends Crud<T, K>, CrudBatch<T, K>, Search<T> {

	void saveOrUpdate(T t);

	void saveOrUpdateAll(List<T> list);
	
	Class<T> getEntityClass();

}
