package sample.org.blueo.db.dao;

import org.springframework.stereotype.Repository;
import sample.org.blueo.db.po.TblPerson;
import org.blueo.commons.jdbc.core.impl.dao.AbstractTraceableDao;

@Repository
public class TblPersonDao extends AbstractTraceableDao<TblPerson, Long, Long> {


}
