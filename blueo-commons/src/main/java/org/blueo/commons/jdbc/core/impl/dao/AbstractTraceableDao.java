package org.blueo.commons.jdbc.core.impl.dao;

import java.util.Collections;
import java.util.List;

import org.blueo.commons.jdbc.core.DelFlagType;
import org.blueo.commons.jdbc.core.traceable.TraceablePoOverwriter;

public class AbstractTraceableDao<T, K, U> extends AbstractDao<T, K> {
	private TraceablePoOverwriter<U> TraceablePoOverwriter;

	@Override
	public T getById(K id) {
		return this.getById(id, DelFlagType.Active);
	}
	
	public T getById(K id, DelFlagType type) {
		T t = super.getById(id);
		return TraceablePoOverwriter.getOverwrite(t, type);
	}

	@Override
	public K save(T t) {
		TraceablePoOverwriter.saveOverwrite(t);
		return super.save(t);
	}

	@Override
	public List<K> saveAll(List<T> list) {
		if (list == null) {
			return Collections.emptyList();
		}
		for (T t : list) {
			TraceablePoOverwriter.saveOverwrite(t);
		}
		return super.saveAll(list);
	}

	@Override
	public void update(T t) {
		TraceablePoOverwriter.updateOverwrite(t);
		super.update(t);
	}

	@Override
	public void updateAll(List<T> list) {
		if (list == null) {
			return;
		}
		for (T t : list) {
			TraceablePoOverwriter.updateOverwrite(t);
		}
		super.updateAll(list);
	}

	@Override
	public void saveOrUpdate(T t) {
		super.saveOrUpdate(t);
	}

	@Override
	public void saveOrUpdateAll(List<T> list) {
		super.saveOrUpdateAll(list);
	}

	@Override
	public void delete(T t) {
		if (TraceablePoOverwriter.deleteOverwrite(t)) {
			this.update(t);
		} else {
			super.delete(t);
		}
	}

	@Override
	public void deleteAll(List<T> ts) {
		super.deleteAll(ts);
	}

	@Override
	public void deleteById(K id) {
		super.deleteById(id);
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
