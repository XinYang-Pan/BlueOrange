package org.blueo.commons.persistent.dao;

import java.io.Serializable;
import java.util.List;

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
	
	<T> void saveAll(List<T> list);

	<T> void updateAll(List<T> list);

	<T> void deleteAll(List<T> list);

	// -----------------------------
	// ----- Search
	// -----------------------------
	
	<T> List<T> findByExample(T t);

}