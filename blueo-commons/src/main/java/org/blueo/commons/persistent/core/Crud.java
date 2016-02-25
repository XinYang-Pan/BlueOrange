package org.blueo.commons.persistent.core;

import org.blueo.commons.persistent.core.dao.po.id.HasId;

// T is entity, K is key
public interface Crud<T extends HasId<K>, K> {

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
