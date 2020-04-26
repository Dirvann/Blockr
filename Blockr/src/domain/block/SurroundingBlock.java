package domain.block;

import java.util.ArrayList;
import java.util.List;
/**
 * An abstract class of SurroundingBlocks that extends SequenceBlock.
 * It has a conditionblock and a bodyblock.
 * 
 * @version 3.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum
 * 		   Dirk Vanbeveren
 * 		   Geert Wesemael
 *
 */
public abstract class SurroundingBlock extends SequenceBlock {

	protected ConditionBlock condition = null;
	protected SequenceBlock bodyBlock = null;

	/**
	 * Set the given block as first body block.
	 * 
	 * @param block 
	 * 		  Sets this block as first (of a sequence) under the statement.
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
	 * The first block inside of this block.
	 * 
	 * @return The first block which is surrounded by this block
	 */
	protected SequenceBlock getBodyBlock() {
		SequenceBlock copy = this.bodyBlock; // TODO: decent copy
		return copy;
	}

	/**
	 * Remove all blocks inside of this block.
	 * 
	 * @post removes all blocks which are surrounded by this block. Sets the pointer
	 *       to the first block to null.
	 */
	protected void removeBodyBlock() {
		if (this.bodyBlock != null) {
			this.bodyBlock.setSurroundingBlock(null);
			this.bodyBlock = null;
		}
	}

	/**
	 * The first condition block attached to this block.
	 * 
	 * @return Returns the first condition block (might be of a sequence accessible
	 *         by block.next()).
	 */
	protected ConditionBlock getConditionBlock() {
		ConditionBlock copy = this.condition; // TODO proper copy
		return copy;
	}

	/**
	 * Set the condition block to the given condition block.
	 * 
	 * @param block 
	 * 		  The block that needs to be added. 
	 * @post  sets this block as the first block in the sequence which is surrounded by the if block.
	 */
	protected void setConditionBlock(ConditionBlock block) {
		block.setSurroundingBlock(this);
		block.getLastBlock().setNextBlock(condition);
		condition = block;

	}

	/**
	 * Removes the condition block(s).
	 * 
	 * @post removes the condition block and sets this to null. The surroundingBlock of
	 *       the condition will be null too.
	 */
	protected void removeConditionBlock() {
		if (condition != null)
			condition.setSurroundingBlock(null);
		this.condition = null;

	}

	/**
	 * Checks if the condition is valid.
	 * 
	 * @return True if the condition can be evaluated.
	 */
	protected boolean hasValidCondition() {
		if (this.condition == null) {
			return false;
		} else {
			return this.condition.isValidCondition();
		}
	}
	
	@Override
	protected boolean hasValidExecutionColumn() {
		// First check if this block has a valid condition
		boolean result = this.hasValidCondition();
		// If this block has a bodyBlock, check if it can be executed
		if (this.bodyBlock != null) {
			result = result && this.bodyBlock.hasValidExecutionColumn();
		}
		// If this block has a following block, check if it can be executed
		if (this.getNextBlock() != null) {
			result = result && this.getNextBlock().hasValidExecutionColumn();
		}
		
		return result;
	}

	/**
	 * The block that needs to be executed next, when everything in the body is done executing.
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
