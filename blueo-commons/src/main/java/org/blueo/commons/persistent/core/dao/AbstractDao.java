package org.blueo.commons.persistent.core.dao;

import java.util.List;

import org.blueo.commons.BlueoUtils;
import org.blueo.commons.persistent.core.dao.po.HasId;
import org.springframework.beans.BeanUtils;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;

public class AbstractDao<T extends HasId<K>, K> implements Crud<T, K>, CrudBatch<T, K>, Search<T> {
	@SuppressWarnings("serial")
	private final Class<T> parameterizedClass = BlueoUtils.getParameterizedClass(new TypeToken<T>(this.getClass()) {});

	private Crud<T, K> crud;
	private CrudBatch<T, K> crudBatch;
	private Search<T> search;

	// -----------------------------
	// ----- CRUD
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
	public void update(T t) {
		crud.update(t);
	}

	@Override
	public void delete(T t) {
		crud.delete(t);
	}

	@Override
	public void deleteById(K id) {
		crud.deleteById(id);
	}

	public final void saveOrUpdate(T t) {
		if (t == null) {
			return;
		}
		if (t.getId() == null) {
			this.save(t);
		} else {
			this.update(t);
		}
	}

	// -----------------------------
	// ----- CRUD Batch
	// -----------------------------

	@Override
	public void saveAll(List<T> list) {
		crudBatch.saveAll(list);
	}

	@Override
	public void updateAll(List<T> list) {
		crudBatch.updateAll(list);
	}

	@Override
	public void deleteAll(List<T> list) {
		crudBatch.deleteAll(list);
	}

	public final void saveOrUpdateAll(List<T> list) {
		if (list == null) {
			return;
		}
		List<T> saves = Lists.newArrayList();
		List<T> updates = Lists.newArrayList();
		for (T t : updates) {
			if (t.getId() == null) {
				saves.add(t);
			} else {
				updates.add(t);
			}
		}
		this.saveAll(saves);
		this.updateAll(updates);
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
		T t = BeanUtils.instantiate(parameterizedClass);
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
