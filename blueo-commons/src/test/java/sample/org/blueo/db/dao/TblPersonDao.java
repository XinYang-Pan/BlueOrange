package sample.org.blueo.db.dao;

import org.springframework.stereotype.Repository;

import sample.org.blueo.db.po.TblPerson;

import org.blueo.commons.jdbc.core.impl.dao.AbstractDao;

@Repository
public class TblPersonDao extends AbstractDao<TblPerson, Long> {


}