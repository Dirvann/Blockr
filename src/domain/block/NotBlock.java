package domain.block;

import domain.block.abstract_classes.ChainConditionBlock;

public class NotBlock extends ChainConditionBlock {
	
	public NotBlock() {
		
	}
	public boolean evaluate() {
		if (this.getNextCondition() == null) {
			return false;
		}
		return !this.getNextCondition().evaluate();
	}
}
