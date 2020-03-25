package domain.block;

import presentation.ProgramAreaPresentation;

abstract class SingleConditionBlock extends ConditionBlock{
	
	protected SingleConditionBlock() {

	}
	
	protected boolean isValidCondition() {
		return true;
	}
	
	protected void removeFromProgramAreaPresentationRecursively(ProgramAreaPresentation programAreaP) {
		programAreaP.removeBlock(getPresentationBlock());
		programAreaP.increaseBlocksLeft();
	}
	
	@Override
	protected void connectTo(Block block) {
		return;
	}
	
}
