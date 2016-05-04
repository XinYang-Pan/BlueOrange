package org.blueo.commons;

import java.util.Map;

import org.apache.commons.lang3.EnumUtils;
import org.javatuples.Pair;
import org.springframework.util.Assert;

import com.google.common.collect.Maps;

public class EnumMapping {

	private Map<Pair<Class<Enum<?>>, Class<Enum<?>>>, Map<Enum<?>, Enum<?>>> map = Maps.newHashMap();
	
	@SuppressWarnings("unchecked")
	public <F extends Enum<F>, T extends Enum<T>> T convert(F from, Class<T> toEnumClass) {
		if (from == null) {
			return null;
		}
		Assert.notNull(toEnumClass);
		Class<F> fromEnumClass = from.getDeclaringClass();
		Pair<Class<F>, Class<T>> pair = Pair.with(fromEnumClass, toEnumClass);
		Map<Enum<?>, Enum<?>> innerMap = map.get(pair);
		Assert.notNull(innerMap, String.format("%s mapping is not initialized.", pair));
		return (T) innerMap.get(from);
	}

	public <E1 extends Enum<E1>, E2 extends Enum<E2>> void dulinkSameName(Class<E1> c1, Class<E2> c2) {
		this.dulinkSameName(c1, c2, true);
	}

	public <E1 extends Enum<E1>, E2 extends Enum<E2>> void dulinkSameName(Class<E1> c1, Class<E2> c2, boolean strictMode) {
		Map<String, E1> c1Map = EnumUtils.getEnumMap(c1);
		Map<String, E2> c2Map = EnumUtils.getEnumMap(c2);
		if (strictMode) {
			Assert.isTrue(c1Map.size() == c2Map.size(), "2 enum list sizes have to be same in strictMode");
		}
		for (E1 e1 : c1Map.values()) {
			E2 e2 = c2Map.get(e1.name());
			if (strictMode) {
				Assert.notNull(e2, "2 enum list have to have exactly the same element in strictMode");
			}
			this.dulink(e1, e2);
		}
	}

	public <E1 extends Enum<E1>, E2 extends Enum<E2>> void dulink(E1 e1, E2 e2) {
		this.singlelink(e1, e2);
		this.singlelink(e2, e1);
	}

	@SuppressWarnings("unchecked")
	public <E1 extends Enum<E1>, E2 extends Enum<E2>> void singlelink(E1 from, E2 to) {
		if (from == null) {
			return;
		}
		if (to == null) {
			return;
		} 
		// both not null
		Class<Enum<?>> fromEnumClass = (Class<Enum<?>>) from.getDeclaringClass();
		Class<Enum<?>> toEnumClass = (Class<Enum<?>>) to.getDeclaringClass();
		Map<Enum<?>, Enum<?>> innerMap = this.initAndGetInnerMap(fromEnumClass, toEnumClass);
		innerMap.put(from, to);
}

	private Map<Enum<?>, Enum<?>> initAndGetInnerMap(Class<Enum<?>> fromEnumClass, Class<Enum<?>> toEnumClass) {
		Assert.notNull(fromEnumClass);
		Assert.notNull(toEnumClass);
		Pair<Class<Enum<?>>, Class<Enum<?>>> pair = Pair.with(fromEnumClass, toEnumClass);
		if (!map.containsKey(pair)) {
			Map<Enum<?>, Enum<?>> innerMap = Maps.newHashMap();
			map.put(pair, innerMap);
		}
		return map.get(pair);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EnumMapping [map=");
		builder.append(map);
		builder.append("]");
		return builder.toString();
	}

}
