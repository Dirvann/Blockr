package domain.block.block_types;

import domain.block.abstract_classes.SurroundingBlock;

public abstract class ConditionBlock extends Block{


	private ConditionBlock next = null;
	private ConditionBlock previous = null;
	private SurroundingBlock surroundingBlock = null;
	
	
	/**
	 * 
	 * @return The next block of the condition as ConditionBlock.
	 */
	public ConditionBlock getNextCondition() {
		ConditionBlock copy = this.next; //TODO: make actual copy
		return copy;
	}
	
	public void setNextCondition(ConditionBlock condition) {
		this.next = condition;
	}

	public Block getPrevious() {
		return previous;
	}

	public void setPrevious(ConditionBlock previous) {
		this.previous = previous;
	}

	public SurroundingBlock getSurroundingBlock() {
		return surroundingBlock;
	}

	public void setSurroundingBlock(SurroundingBlock surroundingBlock) {
		this.surroundingBlock = surroundingBlock;
		ConditionBlock i = this;
		while (i != null) {
			i.setSurroundingBlock(surroundingBlock);
			i = i.getNextCondition();
		}
	}
	
	abstract public boolean isValidCondition();

	abstract public boolean evaluate();
}
