package domain.block.abstract_classes;

import domain.block.block_types.Block;
import domain.game_world.Vector;
import presentation.ProgramAreaPresentation;
import presentation.block.SingleSurroundBlockPresentation;

public abstract class SingleSurroundingBlock extends SurroundingBlock {
	
	public SingleSurroundingBlock() {

	}
	
	
	public void removeFromProgramAreaPresentationRecursively(ProgramAreaPresentation programAreaP) {
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
