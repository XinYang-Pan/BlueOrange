package org.blueo.example;

import org.blueo.codegen.setget.SetGetGenerator;
import org.blueo.db.config.DbTableConfig;
import org.blueo.db.config.raw.DbTableConfigRawData;

public class CodeGens {

	public static void main(String[] args) {
		SetGetGenerator.declaredFieldBased(DbTableConfig.class, DbTableConfigRawData.class);
	}

}
