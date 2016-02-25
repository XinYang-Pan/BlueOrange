package org.blueo.commons.persistent.dao.po.traceable;


public class ThreadLocalGetter<T extends TraceablePo<U>, U> extends TraceablePoOverwriterAdaptor<T, U> {

	private ThreadLocal<U> createUpdateIdGetter = new ThreadLocal<>();

	@Override
	public U getUserId() {
		return createUpdateIdGetter.get();
	}

	public void setUserId(U userId) {
		createUpdateIdGetter.set(userId);
	}

}
