package org.blueo.pojogen.bo.wrapper.clazz;

import org.blueo.commons.tostring.ToStringUtils;
import org.junit.Test;

public class ClassWrapperParserTest {

	@Test
	public void test() {
		ClassWrapper wrapper = ClassWrapperParser.parse("java.util.Map<java.lang.String, java.lang.Long>");
		System.out.println(wrapper);
		System.out.println("*************************");
		System.out.println(ToStringUtils.wellFormat(wrapper.getImports()));
	}

}
