package org.blueo.commons.persistent.core.dao;

import java.util.List;

import org.blueo.commons.persistent.core.Crud;
import org.blueo.commons.persistent.core.CrudBatch;
import org.blueo.commons.persistent.core.Search;
import org.blueo.commons.persistent.core.dao.po.HasId;

public interface Dao<T extends HasId<K>, K> extends Crud<T, K>, CrudBatch<T, K>, Search<T> {

	void saveOrUpdate(T t);

	void saveOrUpdateAll(List<T> list);

}
