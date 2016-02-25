package org.blueo.commons.persistent.dao;

import java.util.List;

// T is entity, K is key
public interface CrudBatch<T, K> {

	// -----------------------------
	// ----- Create
	// -----------------------------

	public void saveAll(List<T> list);

	// -----------------------------
	// ----- Update
	// -----------------------------

	public void updateAll(List<T> list);

	// -----------------------------
	// ----- Delete
	// -----------------------------

	public void deleteAll(List<T> list);

	// -----------------------------
	// ----- Read
	// -----------------------------

}
