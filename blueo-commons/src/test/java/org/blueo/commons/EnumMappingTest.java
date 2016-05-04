package org.blueo.commons;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EnumMappingTest {
	// @formatter:off
	enum A {a,b,c}
	enum A1 {a,b,c}
	enum B {a,b,c,d,e}
	enum C {a,b,c,d}
	enum D {a,b,c,d}
	enum F {a,b,d}
	// @formatter:on

	//
	private EnumMapping enumMapping = new EnumMapping();

	@Before
	public void init() {
		enumMapping.dulink(A.a, B.a);
		enumMapping.dulink(A.b, B.b);
		enumMapping.dulink(A.c, B.c);
		enumMapping.dulink(null, B.d);
		//
		enumMapping.dulinkSameName(A.class, A1.class);
		//
		System.out.println(enumMapping.toString());
	}

	@Test
	public void normal_case() {
		// null always return null
		Assert.assertSame(null, enumMapping.convert(null, A.class));
		// not mapped return null
		Assert.assertSame(null, enumMapping.convert(B.e, A.class));
		// mapped null return null
		Assert.assertSame(null, enumMapping.convert(B.d, A.class));
		// dulink
		Assert.assertSame(B.a, enumMapping.convert(A.a, B.class));
		Assert.assertSame(A.a, enumMapping.convert(B.a, A.class));
		// dulinkSameName
		Assert.assertSame(A1.a, enumMapping.convert(A.a, A1.class));
		Assert.assertSame(A.b, enumMapping.convert(A1.b, A.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void not_init() {
		Assert.assertSame(B.a, enumMapping.convert(A.a, C.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void strictMode_init_different_size() {
		enumMapping.dulinkSameName(A.class, D.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void strictMode_init_different_elements() {
		enumMapping.dulinkSameName(A.class, F.class);
	}

}
