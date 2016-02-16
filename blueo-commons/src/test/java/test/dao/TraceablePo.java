package test.dao;

import java.util.Date;

// T is ID type
public interface TraceablePo<T> {
	
    public T getCreateId() ;
    public void setCreateId(T createId) ;

    public Date getCreateTime() ;
    public void setCreateTime(Date createTime) ;

    public T getUpdateId() ;
    public void setUpdateId(T updateId) ;

    public Date getUpdateTime() ;
    public void setUpdateTime(Date updateTime) ;

    public Boolean getDelFlag() ;
    public void setDelFlag(Boolean delFlag) ;
    
}
