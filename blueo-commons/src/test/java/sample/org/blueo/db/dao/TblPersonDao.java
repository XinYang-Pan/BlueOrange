package sample.org.blueo.db.dao;

import org.blueo.commons.persistent.dao.impl.TraceableDao;
import org.springframework.stereotype.Repository;
import sample.org.blueo.db.po.TblPerson;

@Repository
public class TblPersonDao extends TraceableDao<TblPerson, Long, Long> {


}
