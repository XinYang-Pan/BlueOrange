package tools;

import org.blueo.codegen.setget.SetGetGenerator;

import cn.nextop.thorin.core.holding.orm.po.AccountsConfigPo;

public class Gens {

	public static void main(String[] args) {
//		SetGetGenerator.declaredFieldBased(BooksConfigPo.class, BooksPo.class);
//		SetGenerator.declaredFieldBased(BkBandImpl.class);
//		SetGenerator.methodBased(BkBandImpl.class);
//		SetGenerator.methodBased(BkBandImpl.class);
		SetGetGenerator.methodBased(AccountsConfigPo.class, AccountsConfigArg.class);
		
	}

}
