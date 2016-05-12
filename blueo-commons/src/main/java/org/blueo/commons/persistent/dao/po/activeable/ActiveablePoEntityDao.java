package org.blueo.commons.persistent.dao.po.activeable;

import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.blueo.commons.persistent.dao.impl.EntityDaoAdaptor;

public class ActiveablePoEntityDao<T extends ActiveablePo, K> extends EntityDaoAdaptor<T, K> {

	@Override
	public T getById(K id) {
		return this.getById(id, ActiveFlagType.Active);
	}

	public T getById(K id, ActiveFlagType activeFlagType) {
		T t = this.getById(id);
		if (t == null) {
			return t;
		}
		switch (activeFlagType) {
		case All:
			return t;
		case Active:
			if (BooleanUtils.isTrue(t.getActiveFlag())) {
				return t;
			}
			break;
		case Delete:
			if (BooleanUtils.isFalse(t.getActiveFlag())) {
				return t;
			}
			break;
		default:
			break;
		}
		return null;
	}

	@Override
	public void delete(T t) {
		if (t == null) {
			return;
		}
		t.setActiveFlag(false);
		super.update(t);
	}

	@Override
	public void deleteAll(List<T> list) {
		if (list == null) {
			return;
		}
		for (T t : list) {
			t.setActiveFlag(false);
		}
		super.updateAll(list);
	}

	@Override
	public void deleteById(K id) {
		T t = this.getById(id);
		if (t != null) {
			this.delete(t);
		}
	}

}
