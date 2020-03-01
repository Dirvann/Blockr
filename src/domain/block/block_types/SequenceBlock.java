package domain.block.block_types;

import domain.block.abstract_classes.SurroundingBlock;

public abstract class SequenceBlock extends Block {
	private SurroundingBlock surroundingBlock = null;
	private SequenceBlock next = null;
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
	 *              between this and this.getNextBlock(). The surrounding block is
	 *              adjusted in the added blocks. If the block to be added is an
	 *              empty surrounding block (like if and while), all sequence blocks
	 *              after 'block' will be added to the body of this surrounding
	 *              block. If the 'block' is a non-empty surrounding block, its will
	 *              be added between 'block' and 'block.next'.
	 * 
	 */
	public void setNextBlock(SequenceBlock block) {

		block.setSurroundingBlock(this.surroundingBlock);

		if (this.next == null) {
			this.next = block;// TODO: verify that there are no loops
		} else {
			SequenceBlock last = block.getLastBlock();

			if (block instanceof SurroundingBlock) {
				SurroundingBlock block1 = (SurroundingBlock) block;
				if (block1.isempty()) block1.setBodyBlock(this.next);
				else block1.setNextBlock(this.next);
			} else
				last.setNextBlock(this.next);
			this.next = block;

		}
	}

	/**
	 * 
	 * @return Last block of the group of connected blocks with this block.
	 */
	private SequenceBlock getLastBlock() {
		if (this.getNextBlock() == null) {
			return this;
		} else
			return this.getNextBlock().getLastBlock();
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

}
