package domain.block;

import java.util.ArrayList;
import java.util.List;

public abstract class SurroundingBlock extends SequenceBlock {

	protected ConditionBlock condition = null;
	protected SequenceBlock bodyBlock = null;

	/**
	 * 
	 * @param block Sets this block as first (of a sequence) under the if statement.
	 */
	protected void setBodyBlock(SequenceBlock block) {
		if (this.bodyBlock != null) {
			SequenceBlock last = block;
			while (last.getNextBlock() != null) {
				last = last.getNextBlock();
			}
			last.setNextBlock(this.bodyBlock);
		}
		this.bodyBlock = block;
		block.setSurroundingBlock(this);
		block.previous = null;

	}

	/**
	 * 
	 * @return The first block which is surrounded by this if block
	 */
	protected SequenceBlock getBodyBlock() {
		SequenceBlock copy = this.bodyBlock; // TODO: decent copy
		return copy;
	}

	/**
	 * 
	 * @ removes all blocks which are surrounded by this if block. Sets the pointer
	 * to the first block to null.
	 */
	protected void removeBodyBlock() {
		this.bodyBlock.setSurroundingBlock(null);
		this.bodyBlock = null;
	}

	/**
	 * 
	 * @return Returns the first condition block (might be of a sequence accessible
	 *         by block.next()).
	 */
	protected ConditionBlock getConditionBlock() {
		ConditionBlock copy = this.condition; // TODO proper copy
		return copy;
	}

	/**
	 * 
	 * @param block The block that needs to be added. @ sets this block as the first
	 *              block in the sequence which is surrounded by the if block.
	 */
	protected void setConditionBlock(ConditionBlock block) {
		block.setSurroundingBlock(this);
		block.getLastBlock().setNextBlock(condition);
		condition = block;

	}

	/**
	 * 
	 * @ removes the condition block and sets this to null. The surroundingBlock of
	 * the condition will be null too.
	 */
	protected void removeConditionBlock() {
		if (condition != null)
			condition.setSurroundingBlock(null);
		this.condition = null;

	}

	protected boolean hasValidCondition() {
		if (this.condition == null) {
			return false;
		} else {
			return this.condition.isValidCondition();
		}
	}

	/**
	 * 
	 * @return The block that needs to be executed or checked after the body has
	 *         been executed.
	 */
	protected abstract SequenceBlock getNextAfterLoop();

	@Override
	protected List<Block> getAllNextBlocks() {
		List<Block> l = new ArrayList<Block>();

		l.addAll(super.getAllNextBlocks());

		if (this.bodyBlock != null)
			l.addAll(bodyBlock.getAllNextBlocks());

		if (this.condition != null)
			l.addAll(condition.getAllNextBlocks());
		return l;
	}

}
