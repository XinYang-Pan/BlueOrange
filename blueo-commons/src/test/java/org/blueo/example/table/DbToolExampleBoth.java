package org.blueo.example.table;

import java.io.IOException;

import jxl.read.biff.BiffException;

public class DbToolExampleBoth {

	public static void main(String[] args) throws BiffException, IOException {
		DbToolExample.main(args);
		DbToolExampleIncreamental.main(args);
	}

}
