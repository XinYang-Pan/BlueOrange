package org.blueo.commons.persistent.dao.impl;

import java.util.List;

import org.blueo.commons.persistent.dao.EntityDao;
import org.blueo.commons.persistent.dao.po.traceable.DelFlagType;
import org.blueo.commons.persistent.dao.po.traceable.TraceablePo;
import org.blueo.commons.persistent.dao.po.traceable.TraceablePoOverwriter;

public class TraceableDao<T extends TraceablePo<U>, K, U> extends AbstractEntityDao<T, K> {
	private TraceablePoOverwriter<T, U> traceablePoOverwriter;
	private EntityDao<T, K> entityDao;

	// -----------------------------
	// ----- CRUD
	// -----------------------------

	@Override
	public T getById(K id) {
		return this.getById(id, DelFlagType.Active);
	}

	@Override
	public K save(T t) {
		traceablePoOverwriter.saveOverwrite(t);
		return entityDao.save(t);
	}

	@Override
	public void update(T t) {
		traceablePoOverwriter.updateOverwrite(t);
		entityDao.update(t);
	}

	@Override
	public void delete(T t) {
		traceablePoOverwriter.deleteOverwrite(t);
		this.update(t);
	}

	@Override
	public void deleteById(K id) {
		T t = this.getById(id);
		if (t != null) {
			this.delete(t);
		}
	}

	public T getById(K id, DelFlagType type) {
		T t = entityDao.getById(id);
		return traceablePoOverwriter.getOverwrite(t, type);
	}

	// -----------------------------
	// ----- CRUD BATCH
	// -----------------------------

	@Override
	public void saveAll(List<T> list) {
		traceablePoOverwriter.saveAllOverwrite(list);
		entityDao.saveAll(list);
	}

	@Override
	public void updateAll(List<T> list) {
		traceablePoOverwriter.updateAllOverwrite(list);
		entityDao.updateAll(list);
	}

	@Override
	public void deleteAll(List<T> list) {
		traceablePoOverwriter.deleteAllOverwrite(list);
		this.updateAll(list);
	}

	// -----------------------------
	// ----- Search
	// -----------------------------

	@Override
	public List<T> findByExample(T t) {
		traceablePoOverwriter.findByExampleOverwrite(t);
		return entityDao.findByExample(t);
	}

	// -----------------------------
	// ----- DI
	// -----------------------------
	
	public TraceablePoOverwriter<T, U> getTraceablePoOverwriter() {
		return traceablePoOverwriter;
	}

	public void setTraceablePoOverwriter(TraceablePoOverwriter<T, U> traceablePoOverwriter) {
		this.traceablePoOverwriter = traceablePoOverwriter;
	}

	public EntityDao<T, K> getDao() {
		return entityDao;
	}

	public void setDao(EntityDao<T, K> dao) {
		this.entityDao = dao;
	}

}
