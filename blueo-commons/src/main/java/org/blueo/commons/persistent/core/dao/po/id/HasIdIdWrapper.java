package org.blueo.commons.persistent.core.dao.po.id;



public class HasIdIdWrapper<T extends HasId<K>, K> implements IdWrapper<T, K>{

	@Override
	public K getId(T t) {
		return t.getId();
	}

	@Override
	public void setId(T t, K k) {
		t.setId(k);
	}

}
