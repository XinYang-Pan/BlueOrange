package org.blueo.commons.jdbc.core.impl.dao;

import java.util.List;

import org.blueo.commons.jdbc.core.DelFlagType;
import org.blueo.commons.jdbc.core.po.HasId;
import org.blueo.commons.jdbc.core.traceable.TraceablePo;
import org.blueo.commons.jdbc.core.traceable.TraceablePoOverwriter;

public class AbstractTraceableDao<T extends HasId<K> & TraceablePo<U>, K, U> extends AbstractDao<T, K> {
	private TraceablePoOverwriter<T, U> TraceablePoOverwriter;

	// -----------------------------
	// ----- CRUD
	// -----------------------------

	@Override
	public T getById(K id) {
		return this.getById(id, DelFlagType.Active);
	}

	@Override
	public K save(T t) {
		TraceablePoOverwriter.saveOverwrite(t);
		return super.save(t);
	}

	@Override
	public void update(T t) {
		TraceablePoOverwriter.updateOverwrite(t);
		super.update(t);
	}

	@Override
	public void delete(T t) {
		TraceablePoOverwriter.deleteOverwrite(t);
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
		T t = super.getById(id);
		return TraceablePoOverwriter.getOverwrite(t, type);
	}

	// -----------------------------
	// ----- CRUD BATCH
	// -----------------------------

	@Override
	public void saveAll(List<T> list) {
		TraceablePoOverwriter.saveAllOverwrite(list);
		super.saveAll(list);
	}

	@Override
	public void updateAll(List<T> list) {
		TraceablePoOverwriter.updateAllOverwrite(list);
		super.updateAll(list);
	}

	@Override
	public void deleteAll(List<T> list) {
		TraceablePoOverwriter.deleteAllOverwrite(list);
		this.updateAll(list);
	}

	// -----------------------------
	// ----- Search
	// -----------------------------

	@Override
	public List<T> findByExample(T t) {
		TraceablePoOverwriter.findByExampleOverwrite(t);
		return super.findByExample(t);
	}

}
