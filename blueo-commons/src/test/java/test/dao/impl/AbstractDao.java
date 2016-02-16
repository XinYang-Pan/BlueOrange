package test.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;

import test.dao.Crud;
import test.dao.ReadType;
import test.dao.Search;

import com.google.common.reflect.TypeToken;

public class AbstractDao<T, K> implements Crud<T, K>, Search<T> {
	protected HibernateTemplate hibernateTemplate;
	protected JdbcTemplate jdbcTemplate;
	protected Crud<T, K> crud;
	protected Search<T> search;
	//
	protected final Class<T> clazz;

	public AbstractDao() {
		clazz = this.getParameterizedClass();
	}

	// -----------------------------
	// ----- delegate to Crud
	// -----------------------------
	public T getById(K id) {
		return crud.getById(id);
	}

	public T getById(Serializable id, ReadType type) {
		return crud.getById(id, type);
	}

	public K save(T t) {
		return crud.save(t);
	}

	public List<K> saveAll(List<T> list) {
		return crud.saveAll(list);
	}

	public void update(T t) {
		crud.update(t);
	}

	public void updateAll(List<T> list) {
		crud.updateAll(list);
	}

	public void saveOrUpdate(T t) {
		crud.saveOrUpdate(t);
	}

	public void saveOrUpdateAll(List<T> list) {
		crud.saveOrUpdateAll(list);
	}

	public void delete(T t) {
		crud.delete(t);
	}

	public void deleteAll(List<T> ts) {
		crud.deleteAll(ts);
	}

	public void deleteById(Serializable id) {
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
	// ----- Others
	// -----------------------------

	@SuppressWarnings({ "unchecked", "serial" })
	private Class<T> getParameterizedClass() {
		TypeToken<T> typeToken = new TypeToken<T>(this.getClass()) {
		};
		Type type = typeToken.getType();
		//
		if (type instanceof Class<?>) {
			Class<T> clazz = (Class<T>) type;
			return clazz;
		} else {
			return null;
		}
	}

	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Crud<T, K> getCrud() {
		return crud;
	}

	public void setCrud(Crud<T, K> crud) {
		this.crud = crud;
	}

	public Search<T> getSearch() {
		return search;
	}

	public void setSearch(Search<T> search) {
		this.search = search;
	}

}
