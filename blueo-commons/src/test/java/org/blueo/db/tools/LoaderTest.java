package org.blueo.db.tools;

import java.net.URL;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.blueo.db.DbConfig;
import org.blueo.db.java.DataLoader;
import org.junit.Test;

public class LoaderTest {

	@Test
	public void test() {
		URL url = LoaderTest.class.getResource("loader_test.xls");
		DataLoader loader = DataLoader.build(url.getPath());
		DbConfig dbConfig = loader.getDbConfig();
		System.out.println(ToStringBuilder.reflectionToString(dbConfig, ToStringStyle.MULTI_LINE_STYLE));
		System.out.println(dbConfig.getPoInterfacesInList());
		System.out.println(dbConfig.getDaoInterfacesInList());
	}

}
