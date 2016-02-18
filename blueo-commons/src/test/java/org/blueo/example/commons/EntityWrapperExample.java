package org.blueo.example.commons;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.blueo.commons.jdbc.EntityWrapper;
import org.blueo.example.object.YzpCodeLog;

public class EntityWrapperExample {

	public static void main(String[] args) {
//		EntityWrapper<YzpCodeLog> entityWrapper = EntityWrapper.of(YzpCodeLog.class);
//		System.out.println(entityWrapper);
		Object wrapper = EntityWrapper.of(YzpCodeLog.class);
		System.out.println(ToStringBuilder.reflectionToString(wrapper, ToStringStyle.MULTI_LINE_STYLE));
	}
	
}
