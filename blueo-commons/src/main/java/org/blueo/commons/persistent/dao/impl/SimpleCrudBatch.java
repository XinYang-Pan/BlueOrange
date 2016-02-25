package org.blueo.commons.persistent.dao.impl;

import java.util.List;

import org.blueo.commons.persistent.dao.Crud;
import org.blueo.commons.persistent.dao.CrudBatch;
import org.springframework.util.CollectionUtils;

public class SimpleCrudBatch<T, K> implements CrudBatch<T, K> {
	private Crud<T, K> crud;

	public SimpleCrudBatch(Crud<T, K> crud) {
		this.crud = crud;
	}

	@Override
	public void saveAll(List<T> list) {
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		for (T t : list) {
			crud.save(t);
		}
	}

	@Override
	public void updateAll(List<T> list) {
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		for (T t : list) {
			crud.update(t);
		}
	}

	@Override
	public void deleteAll(List<T> list) {
		for (T t : list) {
			crud.delete(t);
		}
	}

	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------

	public Crud<T, K> getCrud() {
		return crud;
	}

	public void setCrud(Crud<T, K> crud) {
		this.crud = crud;
	}

}
