package org.blueo.commons.jdbc.core.po;

public class ThreadLocalGetter<T> implements CreateUpdateUserIdGetter<T> {

	private ThreadLocal<T> createUpdateIdGetter = new ThreadLocal<>();

	@Override
	public T getUserId() {
		return createUpdateIdGetter.get();
	}

	public void setUserId(T userId) {
		createUpdateIdGetter.set(userId);
	}

}
