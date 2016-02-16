package org.blueo.commons.text;

import org.javatuples.Triplet;
import org.junit.Test;

public class BlueoStrsTest {

	@Test
	public void test_parse() {
		Triplet<String, String, String> result = BlueoStrs.parse("a{123}b", '{', '}');
		System.out.println(result);
	}

	@Test
	public void test_parse1() {
		Triplet<String, String, String> result = BlueoStrs.parse("a}{1{2}3}{b", '{', '}');
		System.out.println(result);
	}

}
