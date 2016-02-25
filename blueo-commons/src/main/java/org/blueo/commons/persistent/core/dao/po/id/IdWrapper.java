package org.blueo.commons.persistent.core.dao.po.id;

public interface IdWrapper<T, K> {
	
	K getId(T t);
	
	void setId(T t, K k);
	
}
