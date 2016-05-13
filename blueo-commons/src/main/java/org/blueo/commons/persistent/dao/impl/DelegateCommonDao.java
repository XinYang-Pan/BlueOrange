package org.blueo.commons.persistent.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.PostConstruct;

import org.blueo.commons.persistent.dao.CommonDao;
import org.blueo.commons.persistent.dao.EntityDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;

public class DelegateCommonDao implements CommonDao {
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected ApplicationContext applicationContext;
	// 
	protected ConcurrentMap<Class<?>, EntityDao<?, ?>> map = Maps.newConcurrentMap();
	
	@SuppressWarnings("rawtypes")
	@PostConstruct
	public void init() {
		Map<String, EntityDao> entityDaoMap = applicationContext.getBeansOfType(EntityDao.class);
		if (entityDaoMap != null) {
			for (EntityDao entityDao : entityDaoMap.values()) {
				Class<?> enityClass = entityDao.getEntityClass();
				map.put(enityClass, entityDao);
				log.debug("Register entity dao - enityClass={}, entityDao={}.", enityClass.getClass().getSimpleName(), entityDao.getClass().getSimpleName());
			}
		}
	}
	
	// -----------------------------
	// ----- Single
	// -----------------------------

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getById(Class<T> entityClass, Serializable id) {
		EntityDao<T, Serializable> entityDao = (EntityDao<T, Serializable>) this.getEntityDao(entityClass);
		return entityDao.getById(id);
	}

	@Override
	public <T> Serializable save(T t) {
		if (t == null) {
			log.warn("Skip to save null object.");
			return null;
		}
		return (Serializable) this.getEntityDao(t).save(t);
	}

	@Override
	public <T> void update(T t) {
		if (t == null) {
			log.warn("Skip to update null object.");
			return;
		}
		this.getEntityDao(t).update(t);
	}

	@Override
	public <T> void delete(T t) {
		if (t == null) {
			log.warn("Skip to delete null object.");
			return;
		}
		this.getEntityDao(t).update(t);
	}

	@Override
	public <T> void deleteById(Class<T> entityClass, Serializable id) {
		T t = this.getById(entityClass, id);
		this.delete(t);
	}

	// -----------------------------
	// ----- Batch
	// -----------------------------

	@Override
	public <T> void saveAll(List<T> list) {
		if (CollectionUtils.isEmpty(list)) {
			log.warn("Skip to saveAll empty list.");
			return;
		}
		getEntityDao(list).saveAll(list);
	}

	@Override
	public <T> void updateAll(List<T> list) {
		if (CollectionUtils.isEmpty(list)) {
			log.warn("Skip to updateAll empty list.");
			return;
		}
		getEntityDao(list).updateAll(list);
	}

	@Override
	public <T> void deleteAll(List<T> list) {
		if (CollectionUtils.isEmpty(list)) {
			log.warn("Skip to deleteAll empty list.");
			return;
		}
		getEntityDao(list).deleteAll(list);
	}


	@SuppressWarnings("unchecked")
	protected <T> EntityDao<T, ?> getEntityDao(List<T> list) {
		Assert.notEmpty(list);
		Class<T> entityClass = (Class<T>) list.get(0).getClass();
		return getEntityDao(entityClass);
	}

	@SuppressWarnings("unchecked")
	protected <T> EntityDao<T, ?> getEntityDao(T t) {
		Objects.requireNonNull(t);
		return (EntityDao<T, ?>) getEntityDao(t.getClass());
	}

	@SuppressWarnings("unchecked")
	protected <T> EntityDao<T, ?> getEntityDao(Class<T> entityClass) {
		return (EntityDao<T, ?>) Objects.requireNonNull(map.get(entityClass));
	}

	// -----------------------------
	// ----- Search
	// -----------------------------

	@Override
	public <T> List<T> findByExample(T t) {
		return this.getEntityDao(t).findByExample(t);
	}

}
