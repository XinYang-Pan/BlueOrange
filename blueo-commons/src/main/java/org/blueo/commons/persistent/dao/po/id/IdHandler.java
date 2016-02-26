package org.blueo.commons.persistent.dao.po.id;

public interface IdHandler<T, K> {

	K getId(T t);

	void setId(T t, K k);

}
