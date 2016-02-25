package org.blueo.commons.persistent.hibernate.impl;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.blueo.commons.persistent.dao.impl.AssemblableDao;
import org.blueo.commons.persistent.dao.impl.SimpleCrudBatch;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class HibernateDao<T, K extends Serializable> extends AssemblableDao<T, K> {
	//
	protected HibernateTemplate hibernateTemplate;

	@PostConstruct
	public void init() {
		//
		HibernateCrud<T, K> hibernateCrud = new HibernateCrud<>();
		hibernateCrud.setHibernateTemplate(hibernateTemplate);
		HibernateSearch<T> hibernateSearch = new HibernateSearch<>();
		hibernateSearch.setHibernateTemplate(hibernateTemplate);
		//
		this.setCrud(hibernateCrud);
		this.setCrudBatch(new SimpleCrudBatch<>(hibernateCrud));
		this.setSearch(hibernateSearch);
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

}
