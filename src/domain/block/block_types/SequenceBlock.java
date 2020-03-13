package domain.block.block_types;

import java.util.ArrayList;
import java.util.List;

import domain.GameController;
import domain.block.abstract_classes.SurroundingBlock;
import domain.game_world.Vector;

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

	@Override
	public List<Block> getAllNextBlocks() {
		List<Block> l = new ArrayList<Block>();

		l.add(this);
		if (this.getNextBlock() != null)
			l.addAll(getNextBlock().getAllNextBlocks());
		
		return l;
	}
	

}
