package org.blueo.commons.persistent.dao.po.traceable;

import java.util.Date;


public class ThreadLocalGetter<U> implements UserIdAndTimeGetter<U> {

	private ThreadLocal<U> createUpdateIdGetter = new ThreadLocal<>();

	@Override
	public U getUserId() {
		return createUpdateIdGetter.get();
	}

	public void setUserId(U userId) {
		createUpdateIdGetter.set(userId);
	}

	@Override
	public Date currentTime() {
		return new Date();
	}

}
