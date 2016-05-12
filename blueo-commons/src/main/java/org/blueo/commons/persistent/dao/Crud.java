package org.blueo.commons.persistent.dao;

import java.util.List;


// T is entity, K is key
public interface Crud<T, K> {

	// -----------------------------
	// ----- Read
	// -----------------------------

	public T getById(K id);
	
	public List<T> getAll();

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
