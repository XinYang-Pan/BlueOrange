package org.blueo.commons.persistent.dao.impl;

import java.util.List;

import org.blueo.commons.persistent.dao.EntityDao;

public class EntityDaoAdaptor<T, K> extends AbstractEntityDao<T, K> {
	protected EntityDao<T, K> entityDao;
	
	public EntityDaoAdaptor() {
		super();
	}

	public EntityDaoAdaptor(Class<T> parameterizedClass) {
		super(parameterizedClass);
	}

	public List<T> findByExample(T t) {
		return entityDao.findByExample(t);
	}

	public T getById(K id) {
		return entityDao.getById(id);
	}

	@Override
	public List<T> getAll() {
		return entityDao.getAll();
	}

	public void saveAll(List<T> list) {
		entityDao.saveAll(list);
	}

	public K save(T t) {
		return entityDao.save(t);
	}

	public void updateAll(List<T> list) {
		entityDao.updateAll(list);
	}

	public void update(T t) {
		entityDao.update(t);
	}

	public void deleteAll(List<T> list) {
		entityDao.deleteAll(list);
	}

	public void delete(T t) {
		entityDao.delete(t);
	}

	public void deleteById(K id) {
		entityDao.deleteById(id);
	}
	
	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------
	
	public EntityDao<T, K> getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao<T, K> entityDao) {
		this.entityDao = entityDao;
	}

}
