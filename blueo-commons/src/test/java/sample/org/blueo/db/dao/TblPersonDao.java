package sample.org.blueo.db.dao;

import org.springframework.stereotype.Repository;
import sample.org.blueo.db.po.TblPerson;
import org.blueo.commons.persistent.core.dao.TraceableDao;

@Repository
public class TblPersonDao extends TraceableDao<TblPerson, Long, Long> {


}
