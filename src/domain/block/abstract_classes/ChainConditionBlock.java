package domain.block.abstract_classes;

import domain.block.block_types.ConditionBlock;

public abstract class ChainConditionBlock extends ConditionBlock{
	
	/**
	 * 
	 * @param block The block which gets connected to the current one.
	 */
	public void addCondition(ConditionBlock block) {
		block.setPrevious(this);
		
		ConditionBlock last = block;
		while(last.getNextCondition() != null) {
			last = last.getNextCondition();
		}
		last.setNextCondition(getNextCondition());
		if (getNextCondition() != null) getNextCondition().setPrevious(last);
		setNextCondition(block);
	}

	public boolean isValidCondition() {
		if (getNextCondition() != null)
			return getNextCondition().isValidCondition();
		return false;
	}

}
