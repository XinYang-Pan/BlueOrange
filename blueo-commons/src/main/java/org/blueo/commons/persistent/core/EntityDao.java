package org.blueo.commons.persistent.core;

import java.util.List;

import org.blueo.commons.persistent.core.dao.po.id.HasId;

public interface EntityDao<T extends HasId<K>, K> extends Crud<T, K>, CrudBatch<T, K>, Search<T> {

	void saveOrUpdate(T t);

	void saveOrUpdateAll(List<T> list);

}
