package domain.block;

public abstract class SequenceBlock extends Block {
	protected SequenceBlock next = null;
	protected SequenceBlock previous = null;

	@Override
	protected SequenceBlock getPreviousBlock() {
		if (previous != null) {
			return previous;
		}
		if (getSurroundingBlock() != null) {
			return getSurroundingBlock();
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

		sBlock.previous = this;

		SequenceBlock last = sBlock.getLastBlock();
		last.next = next;
		if (next != null)
			next.previous = last;
		next = sBlock;
		return true;
	}

	/**
	 * 
	 * @return The next block directly underneath
	 */
	protected SequenceBlock getNextBlock() {
		return next;
	}

	/**
	 * removes all the next blocks on the same level (same surrounding bloc). This
	 * keeps the sequence of the next blocks, but they are now independent.
	 */
	protected void removeNextBlock() {
		if (this.next != null) {
			this.next.previous = null;
			this.setSurroundingBlock(null);
			this.next = null;
		}
	}

	@Override
	protected void disconnect() {
		if (previous != null) {
			previous.removeNextBlock();
		} else if (getSurroundingBlock() != null) {
			getSurroundingBlock().removeBodyBlock();
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

}