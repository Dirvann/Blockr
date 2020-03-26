package domain.block;

import java.util.ArrayList;
import java.util.List;

import presentation.ProgramAreaPresentation;

public abstract class ChainConditionBlock extends ConditionBlock {

	protected ConditionBlock next = null;

	@Override
	protected boolean isValidCondition() {
		if (next != null)
			return next.isValidCondition();
		return false;
	}

	@Override
	protected void removeFromProgramAreaPresentationRecursively(ProgramAreaPresentation programAreaP) {
		programAreaP.removeBlock(getPresentationBlock());
		programAreaP.increaseBlocksLeft();

		Block connectedCondition = this.getNextBlock();
		if (connectedCondition != null) {
			connectedCondition.removeFromProgramAreaPresentationRecursively(programAreaP);
		}
	}

	// Function that controls and chacks the connections
	// ___________________________________________________________________________________________//
	@Override
	protected boolean setNextBlock(Block block) {
		if (block == null) {
			removeNextBlock();
			return true;
		}
		if (!(block instanceof ConditionBlock))
			return false;
		
		block.setSurroundingBlock(surroundingBlock);
		ConditionBlock cBlock = (ConditionBlock) block;
		
		// put this.next after block.last
		if (this.next != null) {
			ConditionBlock next = this.next;
			ConditionBlock last = cBlock.getLastBlock();
			next.disconnect();
			if (last instanceof ChainConditionBlock) {
				((ChainConditionBlock) last).next = next;
				next.previous = (ChainConditionBlock) last;
			}
		}
		this.next = cBlock;
		cBlock.previous = this;
		return true;
	}

	@Override
	protected Block getNextBlock() {
		return next;
	}

	@Override
	protected ConditionBlock getLastBlock() {
		if (next == null) {
			return this;
		} else {
			return next.getLastBlock();
		}
	}

	@Override
	protected List<Block> getAllNextBlocks() {
		List<Block> l = new ArrayList<Block>();

		l.add(this);
		if (this.getNextBlock() != null)
			l.addAll(getNextBlock().getAllNextBlocks());

		return l;
	}

	@Override
	protected void removeNextBlock() {
		this.next.disconnect();

	}

}
