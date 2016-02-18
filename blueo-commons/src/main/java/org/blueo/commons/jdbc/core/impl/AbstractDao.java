package org.blueo.commons.jdbc.core.impl;

import java.io.Serializable;
import java.util.List;

import org.blueo.commons.jdbc.core.Crud;
import org.blueo.commons.jdbc.core.CrudBatch;
import org.blueo.commons.jdbc.core.DelFlagType;
import org.blueo.commons.jdbc.core.Search;

public class AbstractDao<T, K> implements Crud<T, K>, CrudBatch<T, K>, Search<T> {
	protected Crud<T, K> crud;
	protected CrudBatch<T, K> crudBatch;
	protected Search<T> search;

	// -----------------------------
	// ----- delegate to Crud
	// -----------------------------

	@Override
	public T getById(K id) {
		return crud.getById(id);
	}

	@Override
	public T getById(Serializable id, DelFlagType type) {
		return crud.getById(id, type);
	}

	@Override
	public K save(T t) {
		return crud.save(t);
	}

	@Override
	public List<K> saveAll(List<T> list) {
		return crudBatch.saveAll(list);
	}

	@Override
	public void update(T t) {
		crud.update(t);
	}

	@Override
	public void updateAll(List<T> list) {
		crudBatch.updateAll(list);
	}

	@Override
	public void saveOrUpdate(T t) {
		crud.saveOrUpdate(t);
	}

	@Override
	public void saveOrUpdateAll(List<T> list) {
		crudBatch.saveOrUpdateAll(list);
	}

	@Override
	public void delete(T t) {
		crud.delete(t);
	}

	@Override
	public void deleteAll(List<T> ts) {
		crudBatch.deleteAll(ts);
	}

	@Override
	public void deleteById(K id) {
		crud.deleteById(id);
	}

	// -----------------------------
	// ----- Search
	// -----------------------------

	@Override
	public T findByExample(T t, boolean nullableResult) {
		return search.findByExample(t, nullableResult);
	}

	@Override
	public List<T> findByExample(T t) {
		return search.findByExample(t);
	}

	@Override
	public List<T> findAll() {
		return search.findAll();
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

	public CrudBatch<T, K> getCrudBatch() {
		return crudBatch;
	}

	public void setCrudBatch(CrudBatch<T, K> crudBatch) {
		this.crudBatch = crudBatch;
	}

	public Search<T> getSearch() {
		return search;
	}

	public void setSearch(Search<T> search) {
		this.search = search;
	}

}
