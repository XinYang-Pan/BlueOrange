package org.blueo.commons.persistent.mix;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.blueo.commons.persistent.core.CommonDao;
import org.blueo.commons.persistent.core.dao.po.id.HasId;
import org.blueo.commons.persistent.jdbc.impl.JdbcDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;

public class MixedCommonDao implements CommonDao {

	private HibernateTemplate hibernateTemplate;
	private JdbcTemplate jdbcTemplate;
	private ConcurrentMap<Class<?>, JdbcDao<?, ?>> map = Maps.newConcurrentMap();
	
	// -----------------------------
	// ----- Single
	// -----------------------------
	
	@Override
	public <T> T getById(Class<T> entityClass, Serializable id) {
		return getHibernateTemplate().get(entityClass, id);
	}

	@Override
	public <T> Serializable save(T t) {
		if (t == null) {
			return null;
		}
		return getHibernateTemplate().save(t);
	}

	@Override
	public <T> void update(T t) {
		if (t == null) {
			return;
		}
		getHibernateTemplate().update(t);
	}

	@Override
	public <T> void delete(T t) {
		if (t == null) {
			return;
		}
		getHibernateTemplate().update(t);
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
	public <T extends HasId<K>, K> void saveAll(List<T> list) {
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		JdbcDao<T, K> jdbcDao = getJdbcDao(list);
		jdbcDao.saveAll(list);
	}

	@Override
	public <T extends HasId<K>, K> void updateAll(List<T> list) {
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		JdbcDao<T, K> jdbcDao = getJdbcDao(list);
		jdbcDao.updateAll(list);
	}

	@Override
	public <T extends HasId<K>, K> void deleteAll(List<T> list) {
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		JdbcDao<T, K> jdbcDao = getJdbcDao(list);
		jdbcDao.deleteAll(list);
	}

	@SuppressWarnings("unchecked")
	private <K, T extends HasId<K>> JdbcDao<T, K> getJdbcDao(List<T> list) {
		Class<T> entityClass = (Class<T>) list.get(0).getClass();
		JdbcDao<T, K> jdbcDao = (JdbcDao<T, K>) map.get(entityClass);
		if (jdbcDao != null) {
			return jdbcDao;
		}
		jdbcDao = new JdbcDao<T, K>();
		jdbcDao.setParameterizedClass(entityClass);
		jdbcDao.setJdbcTemplate(jdbcTemplate);
		jdbcDao.init();
		map.putIfAbsent(entityClass, jdbcDao);
		return (JdbcDao<T, K>) map.get(entityClass);
	}
	
	// -----------------------------
	// ----- Search
	// -----------------------------
	
	@Override
	public <T> List<T> findByExample(T t) {
		return getHibernateTemplate().findByExample(t);
	}

	// -----------------------------
	// ----- Getter Setter
	// -----------------------------
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	
	
}
