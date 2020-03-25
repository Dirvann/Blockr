package domain.block;

import presentation.ProgramAreaPresentation;

abstract class SingleSurroundingBlock extends SurroundingBlock {
	
	protected SingleSurroundingBlock() {

	}
	
	
	protected void removeFromProgramAreaPresentationRecursively(ProgramAreaPresentation programAreaP) {
		programAreaP.removeBlock(getPresentationBlock());
		programAreaP.increaseBlocksLeft();
		
		Block nextBlock = this.getNextBlock();
		Block bodyBlock = this.getBodyBlock();
		Block conditionBlock = this.getConditionBlock();
		
		if (nextBlock != null) {
			nextBlock.removeFromProgramAreaPresentationRecursively(programAreaP);
		}
		if (bodyBlock != null) {
			bodyBlock.removeFromProgramAreaPresentationRecursively(programAreaP);
		}
		if (conditionBlock != null) {
			conditionBlock.removeFromProgramAreaPresentationRecursively(programAreaP);
		}
	}
}
