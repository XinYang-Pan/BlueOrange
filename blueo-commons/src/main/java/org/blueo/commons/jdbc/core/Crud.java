package org.blueo.commons.jdbc.core;

import java.io.Serializable;

// T is entity, K is key
public interface Crud<T, K> {

	// -----------------------------
	// ----- Read
	// -----------------------------
	
	public T getById(K id);

	public T getById(Serializable id, ReadType type);

	// -----------------------------
	// ----- Create
	// -----------------------------

	public K save(T t);

	// -----------------------------
	// ----- Update
	// -----------------------------
	
	public void update(T t);

	// -----------------------------
	// ----- Create/Update
	// -----------------------------
	
	public void saveOrUpdate(T t);

	// -----------------------------
	// ----- Delete
	// -----------------------------

	public void delete(T t);

	public void deleteById(K id);

}
