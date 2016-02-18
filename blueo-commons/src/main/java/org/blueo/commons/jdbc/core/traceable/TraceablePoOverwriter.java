package org.blueo.commons.jdbc.core.traceable;

import java.util.Date;
import java.util.List;

import org.blueo.commons.jdbc.core.DelFlagType;

public interface TraceablePoOverwriter<T extends TraceablePo<U>, U> {

	<K extends T> K getOverwrite(K t, DelFlagType type);

	void saveOverwrite(T t);

	void updateOverwrite(T t);

	void deleteOverwrite(T t);

	void saveAllOverwrite(List<T> list);

	void updateAllOverwrite(List<T> list);

	void deleteAllOverwrite(List<T> list);
	
	void findByExampleOverwrite(T t);
	
    U getUserId();
    
    Date currentTime();

}
