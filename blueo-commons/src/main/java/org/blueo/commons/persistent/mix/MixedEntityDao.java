package org.blueo.commons.persistent.mix;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.blueo.commons.persistent.dao.impl.AssemblableDao;
import org.blueo.commons.persistent.hibernate.impl.HibernateCrud;
import org.blueo.commons.persistent.hibernate.impl.HibernateSearch;
import org.blueo.commons.persistent.jdbc.impl.JdbcDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class MixedEntityDao<T, K extends Serializable> extends AssemblableDao<T, K> {
	// DI
	private HibernateTemplate hibernateTemplate;
	private JdbcTemplate jdbcTemplate;

	public MixedEntityDao() {
		super();
	}

	public MixedEntityDao(Class<T> parameterizedClass) {
		super(parameterizedClass);
	}

	@PostConstruct
	public void init() {
		//
		HibernateCrud<T, K> hibernateCrud = new HibernateCrud<>(this.getEntityClass());
		hibernateCrud.setHibernateTemplate(hibernateTemplate);
		this.setCrud(hibernateCrud);
		//
		HibernateSearch<T> hibernateSearch = new HibernateSearch<>();
		hibernateSearch.setHibernateTemplate(hibernateTemplate);
		this.setSearch(hibernateSearch);
		//
		JdbcDao<T, K> jdbcDao = new JdbcDao<T, K>(this.getEntityClass());
		jdbcDao.setJdbcTemplate(jdbcTemplate);
		jdbcDao.init();
		this.setCrudBatch(jdbcDao);
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
