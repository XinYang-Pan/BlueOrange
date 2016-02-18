package org.blueo.commons.jdbc.core.impl.dao;

import java.util.List;

import org.blueo.commons.BlueoUtils;
import org.blueo.commons.jdbc.core.Crud;
import org.blueo.commons.jdbc.core.CrudBatch;
import org.blueo.commons.jdbc.core.Search;
import org.blueo.commons.jdbc.core.impl.ParameterizedClass;
import org.springframework.beans.BeanUtils;

public class AbstractDao<T, K> implements Crud<T, K>, CrudBatch<T, K>, Search<T> {
	protected ParameterizedClass<T> parameterizedClass = new ParameterizedClass<T>(){};
	
	private Crud<T, K> crud;
	private CrudBatch<T, K> crudBatch;
	private Search<T> search;

	// -----------------------------
	// ----- delegate to Crud
	// -----------------------------

	@Override
	public T getById(K id) {
		return crud.getById(id);
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
	public List<T> findByExample(T t) {
		return search.findByExample(t);
	}
	
	public final T findByExample(T t, boolean nullableResult) {
		List<T> findByExample = this.findByExample(t);
		if (nullableResult) {
			return BlueoUtils.oneOrNull(findByExample);
		} else {
			return BlueoUtils.oneNoNull(findByExample);
		}
	}

	public final List<T> findAll() {
		Class<T> clazz = parameterizedClass.getParameterizedClass();
		T t = BeanUtils.instantiate(clazz);
		return this.findByExample(t);
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
