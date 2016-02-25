package org.blueo.commons.persistent.core.dao;

import java.util.List;

import org.blueo.commons.BlueoUtils;
import org.blueo.commons.persistent.core.EntityDao;
import org.blueo.commons.persistent.core.dao.po.id.IdWrapper;
import org.blueo.commons.persistent.entity.EntityUtils;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;

public abstract class AbstractDao<T, K> implements EntityDao<T, K> {
	protected Class<T> parameterizedClass;
	protected IdWrapper<T, K> idWrapper;

	@SuppressWarnings("serial")
	public AbstractDao() {
		parameterizedClass = BlueoUtils.getParameterizedClass(new TypeToken<T>(this.getClass()) {});
		idWrapper = EntityUtils.idGetter(parameterizedClass);
	}
	
	public AbstractDao(Class<T> parameterizedClass) {
		this.parameterizedClass = parameterizedClass;
		idWrapper = EntityUtils.idGetter(parameterizedClass);
	}

	public final void saveOrUpdate(T t) {
		if (t == null) {
			return;
		}
		if (idWrapper.getId(t) == null) {
			this.save(t);
		} else {
			this.update(t);
		}
	}

	public final void saveOrUpdateAll(List<T> list) {
		if (list == null) {
			return;
		}
		List<T> saves = Lists.newArrayList();
		List<T> updates = Lists.newArrayList();
		for (T t : updates) {
			if (idWrapper.getId(t) == null) {
				saves.add(t);
			} else {
				updates.add(t);
			}
		}
		this.saveAll(saves);
		this.updateAll(updates);
	}
	
	public void setParameterizedClass(Class<T> parameterizedClass) {
		this.parameterizedClass = parameterizedClass;
		idWrapper = EntityUtils.idGetter(parameterizedClass);
	}

	public Class<T> getParameterizedClass() {
		return parameterizedClass;
	}
	
}
