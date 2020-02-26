package domain.block.abstract_classes;

import domain.block.block_types.ConditionBlock;

public class ChainConditionBlock extends ConditionBlock{
	private ConditionBlock next = null;
	
	public boolean isValidCondition() {
		if (next == null) {
			return false;
		}
		return next.isValidCondition();
	}

}
