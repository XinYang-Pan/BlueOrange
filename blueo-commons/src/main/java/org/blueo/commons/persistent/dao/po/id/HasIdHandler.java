package org.blueo.commons.persistent.dao.po.id;

public class HasIdHandler<T extends HasId<K>, K> implements IdHandler<T, K> {

	@Override
	public K getId(T t) {
		return t.getId();
	}

	@Override
	public void setId(T t, K k) {
		t.setId(k);
	}

}
