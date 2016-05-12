package org.blueo.commons.persistent.dao.po.traceable;

import java.util.Date;

// U is ID type
public interface TraceablePo<U> {
	
    public U getCreateId() ;
    public void setCreateId(U createId) ;

    public Date getCreateTime() ;
    public void setCreateTime(Date createTime) ;

    public U getUpdateId() ;
    public void setUpdateId(U updateId) ;

    public Date getUpdateTime() ;
    public void setUpdateTime(Date updateTime) ;
    
}
