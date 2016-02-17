package org.blueo.commons.jdbc.core.impl.crud;

import java.util.Collections;
import java.util.List;

import org.blueo.commons.jdbc.core.Crud;
import org.blueo.commons.jdbc.core.CrudBatch;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;

public class SimpleCrudBatch<T, K> implements CrudBatch<T, K> {
	private Crud<T, K> crud;

	public SimpleCrudBatch(Crud<T, K> crud) {
		this.crud = crud;
	}

	@Override
	public List<K> saveAll(List<T> list) {
		if (CollectionUtils.isEmpty(list)) {
			return Collections.emptyList();
		}
		List<K> ids = Lists.newArrayList();
		for (T t : list) {
			ids.add(crud.save(t));
		}
		return ids;
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
	public void saveOrUpdateAll(List<T> list) {
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		for (T t : list) {
			crud.saveOrUpdate(t);
		}
	}

	@Override
	public void deleteAll(List<T> ts) {
		for (T t : ts) {
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
