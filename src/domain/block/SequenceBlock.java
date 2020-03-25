package domain.block;

import java.util.ArrayList;
import java.util.List;

import domain.GameController;

abstract class SequenceBlock extends Block {

	private SurroundingBlock surroundingBlock = null;
	protected SequenceBlock next = null;
	protected SequenceBlock previous = null;

	protected SequenceBlock getPreviousBlock() {
		if(previous != null) {
			return previous;
		}
		if (getSurroundingBlock() != null) {
			return getSurroundingBlock();
		} 
		return null;
	}
	
	protected SequenceBlock getPrevious() {
		return previous;
	}
	
	// TODO: Comments

	/**
	 * 
	 * @param block The surrounding block of the this.block Sets the surrounding
	 *              block this.block and all of the blocks which comes after
	 *              this.block
	 */
	protected void setSurroundingBlock(SurroundingBlock block) {
		SequenceBlock i = this;
		while (i != null) {
			i.surroundingBlock = block;
			i = i.next;
		}

	}

	protected SurroundingBlock getSurroundingBlock() {
		SurroundingBlock copy = this.surroundingBlock; // TODO make proper clone
		return copy;
	}

	/**
	 * 
	 * @param block The first block (of a group of blocks) which will be added
	 *              between this and this.getNextBlock().
	 * 
	 */
	protected void setNextBlock(SequenceBlock block) {

		if (block == null)
			removeNextBlock();
		else {
			block.setSurroundingBlock(this.surroundingBlock);
			block.previous = this;

			block.getLastBlock().next = getNextBlock();
			if (getNextBlock() != null)
				getNextBlock().previous = block.getLastBlock();
			next = block;
		}

	}

	/**
	 * 
	 * @return The next block directly underneath
	 */
	protected SequenceBlock getNextBlock() {
		SequenceBlock nextBlock = next;
		return nextBlock;
	}

	/**
	 * removes all the next blocks on the same level (same surrounding bloc). This
	 * keeps the sequence of the next blocks, but they are now independent.
	 */
	protected void removeNextBlock() {
		if (this.next != null) {
			this.next.setSurroundingBlock(null);
			this.next.previous = null;
			this.next = null;
		}
	}

	protected abstract Block execute(GameController gamecontroller) throws Exception;

	@Override
	protected List<Block> getAllNextBlocks() {
		List<Block> l = new ArrayList<Block>();

		l.add(this);
		if (this.getNextBlock() != null)
			l.addAll(getNextBlock().getAllNextBlocks());
		
		return l;
	}
	
	@Override
	protected boolean disconnect() {
		if (getPrevious() != null) {
			getPrevious().removeNextBlock();
			return true;
		}
		else if (getSurroundingBlock() != null) {
			getSurroundingBlock().removeBodyBlock();
			return true;
		}
		return false;
	}
	
	@Override
	protected SequenceBlock getLastBlock() {
		if(next == null) {
			return this;
		} else {
			return next.getLastBlock();
		}
	}
	
	@Override
	protected void connectTo(Block block) {
		if(!(block instanceof SequenceBlock)) return;
		SequenceBlock b = (SequenceBlock) block;
		if(next != null) {
			SequenceBlock last = b.getLastBlock();
			last.setNextBlock(next);
			setNextBlock(null);
		}
		setNextBlock((SequenceBlock)b);
	}
	

}
