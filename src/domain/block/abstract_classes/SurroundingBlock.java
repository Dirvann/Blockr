package domain.block.abstract_classes;

import domain.block.block_types.Block;
import domain.block.block_types.ConditionBlock;
import domain.block.block_types.SequenceBlock;

public abstract class SurroundingBlock extends SequenceBlock{	
	
	private ConditionBlock condition = null;
	private SequenceBlock bodyBlock = null;
	
	public SequenceBlock getNextAfterLoop() {
		if (this.next == null) {
			return this.getSurroundingBlock().getNextAfterLoop();
		}
		return this.next;
	}


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
	public Block getConditionBlock() {
		ConditionBlock copy = this.condition; // TODO proper copy
		return copy;
	}

	/**
	 * 
	 * @param block The block that needs to be added. @ sets this block as the first
	 *              block in the sequence which is surrounded by the if block
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
		this.condition = null;
	}

	/**
	 * 
	 * @return True if the condition can be evaluated, false if evaluation isn't
	 *         possible.
	 */

	public boolean hasValidCondition() {
		if (this.condition == null)
			return false;
		return this.condition.isValidCondition();
	}

	/**
	 * 
	 * @return True if the complete condition is true, false otherwise.
	 * @throws Exception Condition is not valid. For example condition is 'null' or
	 *                   'not'
	 */
	public boolean evaluateCondition() {
		if (condition == null) return false;
		return condition.evaluate();
	}
	
	public boolean isempty() {
		if (this.bodyBlock == null) return true;
		return false;
	}
	/**
	 * 
	 * @param block The first block (of a group of blocks) which will be added
	 *              between this and this.getNextBlock(). The surrounding block is
	 *              adjusted in the added blocks. If the block to be added is an
	 *              empty surrounding block (like if and while), all sequence blocks
	 *              after 'block' will be added to the body of this surrounding
	 *              block. If the 'block' is a non-empty surrounding block, its will
	 *              be added between 'block' and 'block.next'.
	 * 
	 */
	public void setNextBlock(SequenceBlock block) {

		block.setSurroundingBlock(this.getSurroundingBlock());

		if (this.next == null) {
			this.next = block;// TODO: verify that there are no loops
		} else {
			block.setNextBlock(this.next);
			this.next = block;
		}
	}

}
