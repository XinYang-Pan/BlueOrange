package test.dao;

import java.io.Serializable;
import java.util.List;

// T is entity, K is key
public interface Crud<T, K> {

	// -----------------------------
	// ----- Read
	// -----------------------------
	
	public T getById(K id);

	public T getById(Serializable id, ReadType type);

	// -----------------------------
	// ----- Create
	// -----------------------------

	public K save(T t);

	public List<K> saveAll(List<T> list);

	// -----------------------------
	// ----- Update
	// -----------------------------
	
	public void update(T t);

	public void updateAll(List<T> list);

	// -----------------------------
	// ----- Create/Update
	// -----------------------------
	
	public void saveOrUpdate(T t);

	public void saveOrUpdateAll(List<T> list);

	// -----------------------------
	// ----- Delete
	// -----------------------------

	public void delete(T t);

	public void deleteAll(List<T> ts);

	public void deleteById(Serializable id);

}
