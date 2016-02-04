package org.blueo.pojogen.bo;

import java.util.Map;

import com.google.common.collect.Maps;

public class ValueMapObject {

	protected final Map<String, Object> valueMap = Maps.newHashMap();

	public Map<String, Object> getValueMap() {
		return valueMap;
	}

}