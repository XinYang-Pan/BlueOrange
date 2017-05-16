package tools;

import java.beans.IntrospectionException;

import org.blueo.codegen.set.SetGenerator;

import cn.nextop.thorin.core.dealing.orm.po.DealingPositionPo;
import cn.nextop.thorin.core.holding.orm.po.AllocationBookConfigPo;

public class Gens {

	public static void main(String[] args) throws IntrospectionException {
//		SetGetGenerator.declaredFieldBased(DealingPositionDelta.class, DealingPositionPo.class);
//		SetGenerator.declaredFieldBased(BkBandImpl.class);
//		SetGenerator.methodBased(BkBandImpl.class);
		SetGenerator.declaredFieldBased(DealingPositionPo.class);
		SetGenerator.declaredFieldBased(AllocationBookConfigPo.class);

//		CukeMethodBased.generate(AllocationPo.class, "class", "id", "insertDatetime", "insertOperator", "updateDatetime", "updateOperator", "version");
//		CukeMethodBased.generateMap(AllocationPo.class, "class", "id", "insertDatetime", "insertOperator", "updateDatetime", "updateOperator", "version");

//		SetGetGenerator.methodBased(AccountsConfigPo.class, AccountsConfigArg.class);
		
	}

}
