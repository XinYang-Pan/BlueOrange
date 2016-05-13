package org.blueo.commons.persistent.dao.impl;

import java.util.List;

import org.blueo.commons.BlueoUtils;
import org.blueo.commons.persistent.dao.EntityDao;
import org.blueo.commons.persistent.dao.po.id.IdHandler;
import org.blueo.commons.persistent.entity.EntityUtils;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;

public abstract class AbstractEntityDao<T, K> implements EntityDao<T, K> {
	protected Class<T> parameterizedClass;
	protected IdHandler<T, K> idHandler;

	@SuppressWarnings("serial")
	public AbstractEntityDao() {
		parameterizedClass = BlueoUtils.getParameterizedClass(new TypeToken<T>(this.getClass()) {});
		idHandler = EntityUtils.idGetter(parameterizedClass);
	}
	
	public AbstractEntityDao(Class<T> parameterizedClass) {
		this.parameterizedClass = parameterizedClass;
		idHandler = EntityUtils.idGetter(parameterizedClass);
	}

	public void saveOrUpdate(T t) {
		if (t == null) {
			return;
		}
		if (idHandler.getId(t) == null) {
			this.save(t);
		} else {
			this.update(t);
		}
	}

	public void saveOrUpdateAll(List<T> list) {
		if (list == null) {
			return;
		}
		List<T> saves = Lists.newArrayList();
		List<T> updates = Lists.newArrayList();
		for (T t : updates) {
			if (idHandler.getId(t) == null) {
				saves.add(t);
			} else {
				updates.add(t);
			}
		}
		this.saveAll(saves);
		this.updateAll(updates);
	}

	public Class<T> getEntityClass() {
		return parameterizedClass;
	}

}
