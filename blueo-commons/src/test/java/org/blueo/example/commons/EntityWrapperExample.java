package org.blueo.example.commons;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.blueo.commons.persistent.jdbc.impl.JdbcCrudBatch;
import org.blueo.example.object.YzpCodeLog;

public class EntityWrapperExample {

	public static void main(String[] args) {
//		EntityWrapper<YzpCodeLog> entityWrapper = EntityWrapper.of(YzpCodeLog.class);
//		System.out.println(entityWrapper);
		JdbcCrudBatch<YzpCodeLog, Long> crudBatch = new JdbcCrudBatch<YzpCodeLog, Long>(){};
		System.out.println(ToStringBuilder.reflectionToString(crudBatch, ToStringStyle.MULTI_LINE_STYLE));
	}
	
}
