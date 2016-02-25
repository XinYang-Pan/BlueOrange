package sample.org.blueo.db.dao;

import org.springframework.stereotype.Repository;

import sample.org.blueo.db.po.TblPerson;

import org.blueo.commons.persistent.dao.impl.TraceableDao;

@Repository
public class TblPersonDao extends TraceableDao<TblPerson, Long, Long> {


}
