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

		block.setSurroundingBlock(this.surroundingBlock);
		block.previous = this;

		SequenceBlock last = block;
		while (last.getNextBlock() != null) {
			last = last.getNextBlock();
		}
		last.setNextBlock(getNextBlock());
		if (getNextBlock() != null) getNextBlock().previous = last;
		setNextBlock(block);
			
	}

	/**
	 * 
	 * @return The next block directly underneath
	 */
	public SequenceBlock getNextBlock() {
		SequenceBlock nextBlock = next;
		return nextBlock;
	}
	

	/**
	 * removes all the next blocks on the same level (same surrounding bloc). This
	 * keeps the sequence of the next blocks, but they are now independent.
	 */
	public void removeNextBlock() {
		if (this.next != null) {
			this.next.setSurroundingBlock(null);
			this.next.previous = null;
			this.next = null;
		}
	}
	
	public abstract Block execute(GameController gamecontroller) throws Exception;

}
