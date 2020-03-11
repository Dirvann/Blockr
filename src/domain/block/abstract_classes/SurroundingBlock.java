package domain.block.abstract_classes;

import domain.block.block_types.ConditionBlock;
import domain.block.block_types.SequenceBlock;

public abstract class SurroundingBlock extends SequenceBlock{	
	
	private ConditionBlock condition = null;
	private SequenceBlock bodyBlock = null;


	/**
	 * 
	 * @param block Sets this block as first (of a sequence) under the if statement.
	 */
	public void setBodyBlock(SequenceBlock block) {
		this.bodyBlock = block;
		block.setSurroundingBlock(this);

	}

	/**
	 * 
	 * @return The first block which is surrounded by this if block
	 */
	public SequenceBlock getBodyBlock() {
		SequenceBlock copy = this.bodyBlock; // TODO: decent copy
		return copy;
	}

	/**
	 * 
	 * @ removes all blocks which are surrounded by this if block. Sets the pointer
	 * to the first block to null.
	 */
	public void removeBodyBlock() {
		this.bodyBlock.setSurroundingBlock(null);
		this.bodyBlock = null;
	}

	/**
	 * 
	 * @return Returns the first condition block (might be of a sequence accessible
	 *         by block.next()).
	 */
	public ConditionBlock getConditionBlock() {
		ConditionBlock copy = this.condition; // TODO proper copy
		return copy;
	}

	/**
	 * 
	 * @param block The block that needs to be added. @ sets this block as the first
	 *              block in the sequence which is surrounded by the if block.
	 */
	public void setConditionBlock(ConditionBlock block) {
		block.setSurroundingBlock(this);
		if (this.condition != null) {
			ConditionBlock last = block;
			while (last.getNextCondition() != null) 
				last = last.getNextCondition();
			getConditionBlock().setPrevious(last);
			last.setNextCondition(getConditionBlock());
		}
		condition = block;
		
	}

	/**
	 * 
	 * @ removes the condition block and sets this to null.
	 */
	public void removeConditionBlock() {
		if (condition != null) 
			condition.setSurroundingBlock(null);
		this.condition = null;
		
	}

}
