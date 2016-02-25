package org.blueo.commons.persistent.core;

import java.util.List;

import org.blueo.commons.persistent.core.dao.po.id.HasId;

// T is entity, K is key
public interface CrudBatch<T extends HasId<K>, K> {

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
