# BlueOrange
Blue Orange Utilities

# Todo
1. AutoBorrowRequestSummary autoborrowrequestsummary
1. order
1. new util
package com.citi.prime.services.tradingtools.common.util;
import org.apache.commons.lang3.ObjectUtils;
public class IterableMultilineToString<T> {
	private final Iterable<T> iterable;
	private final String prefix;
	public IterableMultilineToString(Iterable<T> iterable) {
		this(iterable, null);
	}
	public IterableMultilineToString(Iterable<T> iterable, String prefix) {
		super();
		this.iterable = iterable;
		this.prefix = ObjectUtils.firstNonNull(prefix, "");
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (T t : iterable) {
			sb.append(prefix).append(t).append(System.lineSeparator());
		}
		return sb.toString();
	}
	public static <T> IterableMultilineToString<T> create(Iterable<T> iterable) {
		return new IterableMultilineToString<>(iterable);
	}
	public static <T> IterableMultilineToString<T> create(Iterable<T> iterable, String prefix) {
		return new IterableMultilineToString<>(iterable, prefix);
	}
}
