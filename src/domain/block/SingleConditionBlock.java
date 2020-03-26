package domain.block;

import presentation.ProgramAreaPresentation;

public abstract class SingleConditionBlock extends ConditionBlock {

	protected boolean isValidCondition() {
		return true;
	}

	protected void removeFromProgramAreaPresentationRecursively(ProgramAreaPresentation programAreaP) {
		programAreaP.removeBlock(getPresentationBlock());
		programAreaP.increaseBlocksLeft();
	}
	
	@Override
	protected void removeNextBlock() {
		return;		
	}

	@Override
	protected Block getNextBlock() {
		return null;
	}

	@Override
	protected boolean setNextBlock(Block block) {
		return false;
	}
}
