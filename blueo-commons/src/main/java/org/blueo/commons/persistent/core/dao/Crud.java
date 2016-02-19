package org.blueo.commons.persistent.core.dao;

// T is entity, K is key
public interface Crud<T, K> {

	// -----------------------------
	// ----- Read
	// -----------------------------

	public T getById(K id);

	// -----------------------------
	// ----- Create
	// -----------------------------

	public K save(T t);

	// -----------------------------
	// ----- Update
	// -----------------------------

	public void update(T t);

	// -----------------------------
	// ----- Delete
	// -----------------------------

	public void delete(T t);

	public void deleteById(K id);

}
