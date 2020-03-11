package domain.block.abstract_classes;

import domain.block.block_types.ConditionBlock;

public class ChainConditionBlock extends ConditionBlock{
	private ConditionBlock next = null;
	
	/**
	 * 
	 * @return True is the condition can be evaluated, false otherwise. (For example 'Not-Not-Null' can't be evaluated)
	 */
	public boolean isValidCondition() {
		if (next == null) {
			return false;
		}
		return next.isValidCondition();
	}
	
	/**
	 * 
	 * @param block The block which gets connected to the current one.
	 */
	public void setNextConditon(ConditionBlock block) {
		this.next = block;
	}
	
	/**
	 * 
	 * @return The next block of the condition as ConditionBlock.
	 */
	public ConditionBlock getNextCondition() {
		ConditionBlock copy = this.next; //TODO: make actual copy
		return copy;
	}

}
