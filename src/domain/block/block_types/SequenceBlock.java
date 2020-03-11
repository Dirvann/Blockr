package domain.block.block_types;

import domain.GameController;
import domain.block.abstract_classes.SurroundingBlock;

public abstract class SequenceBlock extends Block {

	private SurroundingBlock surroundingBlock = null;
	protected SequenceBlock next = null;
	protected SequenceBlock previous = null;
	
	public SequenceBlock getPreviousBlock() {
		return this.previous;
	}
	// TODO: Comments

	/**
	 * 
	 * @param block The surrounding block of the this.block Sets the surrounding
	 *              block this.block and all of the blocks which comes after
	 *              this.block
	 */
	public void setSurroundingBlock(SurroundingBlock block) {
		SequenceBlock i = this;
		while (i != null) {
			i.surroundingBlock = block;
			i = i.next;
		}

	}

	public SurroundingBlock getSurroundingBlock() {
		SurroundingBlock copy = this.surroundingBlock; // TODO make proper clone
		return copy;
	}

	/**
	 * 
	 * @param block The first block (of a group of blocks) which will be added
	 *              between this and this.getNextBlock().
	 * 
	 */
	public void setNextBlock(SequenceBlock block) {

		if (block == null)
			removeNextBlock();
		else {
			block.setSurroundingBlock(this.surroundingBlock);
			block.previous = this;

			SequenceBlock last = block;
			while (last.getNextBlock() != null) {
				last = last.getNextBlock();
			}
			last.next = getNextBlock();
			if (getNextBlock() != null)
				getNextBlock().previous = last;
			next = block;
		}

	}

	/**
	 * removes all the next blocks on the same level (same surrounding bloc). This
	 * keeps the sequence of the next blocks, but they are now independant.
	 */
	public void removeNextBlock() {
		this.next.setSurroundingBlock(null);
		this.next = null;
	}

	/**
	 * 
	 * @return Last block of the group of connected blocks with this block.
	 */
	public SequenceBlock getLastBlock() {
		if (this.next == null) {
			return this;
		} else
			return this.next.getLastBlock();
	}

	/**
	 * 
	 * @return The next block to be EXECUTED
	 */
	public SequenceBlock getNextBlock() {
		if (this.next == null)
			return this.getSurroundingBlock().getNextAfterLoop();
		return this.next; // TODO appropriate copy
	}

	public abstract Block execute(GameController gamecontroller) throws Exception;

}
