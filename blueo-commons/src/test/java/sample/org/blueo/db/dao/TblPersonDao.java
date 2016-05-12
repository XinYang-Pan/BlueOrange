package sample.org.blueo.db.dao;

import org.blueo.commons.persistent.dao.impl.ActiveableTraceableDao;
import org.springframework.stereotype.Repository;
import sample.org.blueo.db.po.TblPerson;

@Repository
public class TblPersonDao extends ActiveableTraceableDao<TblPerson, Long, Long> {


}
