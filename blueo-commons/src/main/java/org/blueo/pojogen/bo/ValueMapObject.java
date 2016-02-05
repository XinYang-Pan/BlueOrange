package org.blueo.pojogen.bo;

import java.util.Map;

import com.google.common.collect.Maps;

public class ValueMapObject {

	protected final Map<String, String> valueMap = Maps.newHashMap();

	public Map<String, String> getValueMap() {
		return valueMap;
	}

}