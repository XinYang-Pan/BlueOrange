package org.blueo.commons.jdbc.core.traceable;

import java.util.Date;

import org.blueo.commons.jdbc.core.DelFlagType;

public interface TraceablePoOverwriter<U> {

	public <T> T getOverwrite(T t, DelFlagType type);

	public void saveOverwrite(Object t);

	public void updateOverwrite(Object t);

	public boolean deleteOverwrite(Object t);
	
	public void findByExampleOverwrite(Object t);
	
    public U getUserId();
    
    public Date currentTime();
    
}
