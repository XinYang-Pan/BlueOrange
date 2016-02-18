package org.blueo.commons.jdbc.core.traceable;


public class ThreadLocalGetter<U> extends TraceablePoOverwriterAdaptor<U> {

	private ThreadLocal<U> createUpdateIdGetter = new ThreadLocal<>();

	@Override
	public U getUserId() {
		return createUpdateIdGetter.get();
	}

	public void setUserId(U userId) {
		createUpdateIdGetter.set(userId);
	}

}
