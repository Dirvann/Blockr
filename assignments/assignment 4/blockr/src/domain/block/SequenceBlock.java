package domain.block;

import domain.GameController;

/**
 * An abstract class of SequenceBlocks that extends Block and has a next and previous block.
 * 
 * @version 4.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum
 * 		   Dirk Vanbeveren
 * 		   Geert Wesemael
 *
 */
public abstract class SequenceBlock extends Block {
	protected SequenceBlock next = null;
	protected SequenceBlock previous = null;
	protected FunctionDefinitionBlock function = null;

	@Override
	protected Block getBlockAbove() {
		if (previous != null) {
			return previous;
		}
		if (getSurroundingBlock() != null) {
			return getSurroundingBlock();
		}
		if (function != null) {
			return function;
		}
		return null;
	}

	@Override
	protected boolean setNextBlock(Block block) {

		if (block == null) {
			removeNextBlock();
			return true;
		}
		if (!(block instanceof SequenceBlock))
			return false;

		SequenceBlock sBlock = (SequenceBlock) block;

		sBlock.setSurroundingBlock(this.surroundingBlock);
		sBlock.function = function;

		sBlock.previous = this;

		SequenceBlock last = sBlock.getLastBlock();
		last.next = next;
		if (next != null)
			next.previous = last;
		next = sBlock;
		return true;
	}

	/**
	 * The block directly underneath.
	 * 
	 * @return The next block directly underneath
	 */
	protected SequenceBlock getNextBlock() {
		return next;
	}

	/**
	 * Removes all the next blocks on the same level.
	 * 
	 * @post removes all the next blocks on the same level (same surrounding bloc). This
	 * keeps the sequence of the next blocks, but they are now independent.
	 * 		 |new.next == null
	 */
	protected void removeNextBlock() {
		if (this.next != null) {
			this.next.previous = null;
			this.next.setSurroundingBlock(null);
			this.next.setFunctionBlock(null);
			this.next = null;
		}
	}

	@Override
	protected void disconnect() {
		if (previous != null) {
			previous.removeNextBlock();
		} else if (getSurroundingBlock() != null) {
			getSurroundingBlock().removeBodyBlock();
		} else if (this.function != null) {
			function.removeBodyBlock();
		}
		
	}

	@Override
	protected SequenceBlock getLastBlock() {
		if (next == null) {
			return this;
		} else {
			return next.getLastBlock();
		}
	}

	/**
	 * Set the surrounding block to the given block.
	 * 
	 * @param function
	 * 		  The function that will include this block.
	 * @post  block is set as the surrounding block of this block and all the next
	 *        ones after this block.
	 *        | this.function == function
	 */
	protected void setFunctionBlock(FunctionDefinitionBlock function) {
		SequenceBlock iterator = this;
		while (iterator != null) {
			iterator.function = function;
			iterator = iterator.getNextBlock();
		}
	}
	
	@Override
	protected boolean hasValidExecutionColumn() {
		if (this.getNextBlock() != null) {
			return this.getNextBlock().hasValidExecutionColumn();
		} else {
			return true;
		}
	}
	
	@Override
	protected Block execute(GameController GC) throws Exception {
		return this.getNextToExecute();
	}
	/**
	 * Return the next block to execute. This checks the block underneath first,
	 * then it checks for a surrounding block and lastly it checks if 
	 * it is in a function definition.
	 * 
	 * @return the next block to execute, this is no block to execute next.
	 */
	protected SequenceBlock getNextToExecute() {
		if (this.getNextBlock() == null) {
			if (this.getSurroundingBlock() == null) {
				if (this.function == null) return null;
				else return this.function.getNextAfterFunction();
			}
			return this.getSurroundingBlock().getNextAfterLoop();
		}
		return this.getNextBlock();
	}
	
	@Override
	protected Block getPrevious() {
		return this.previous;
	}
}
