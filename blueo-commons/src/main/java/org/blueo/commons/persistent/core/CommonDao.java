package org.blueo.commons.persistent.core;

import java.io.Serializable;
import java.util.List;

import org.blueo.commons.persistent.core.dao.po.HasId;

public interface CommonDao {

	// -----------------------------
	// ----- Single
	// -----------------------------
	
	<T> T getById(Class<T> entityClass, Serializable id);

	<T> Serializable save(T t);

	<T> void update(T t);

	<T> void delete(T t);

	<T> void deleteById(Class<T> entityClass, Serializable id);

	// -----------------------------
	// ----- Batch
	// -----------------------------
	
	<T extends HasId<K>, K> void saveAll(List<T> list);

	<T extends HasId<K>, K> void updateAll(List<T> list);

	<T extends HasId<K>, K> void deleteAll(List<T> list);

	// -----------------------------
	// ----- Search
	// -----------------------------
	
	<T> List<T> findByExample(T t);

}