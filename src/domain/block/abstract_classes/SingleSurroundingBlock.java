package domain.block.abstract_classes;

import domain.block.block_types.Block;
import domain.game_world.Vector;
import presentation.ProgramAreaPresentation;
import presentation.block.SingleSurroundBlockPresentation;

public abstract class SingleSurroundingBlock extends SurroundingBlock {
	
	public SingleSurroundingBlock() {

	}
	
	public void setConnectedBlockPositionRecursivelyByDifference(Vector deltaPos) {
		Block nextBlock = this.getNextBlock();
		Block bodyBlock = this.getBodyBlock();
		Block conditionBlock = this.getConditionBlock();
		
		if (nextBlock != null) {
			nextBlock.getPresentationBlock().setPositionRecursivelyByDifference(deltaPos);
		}
		if (bodyBlock != null) {
			bodyBlock.getPresentationBlock().setPositionRecursivelyByDifference(deltaPos);
		}
		if (conditionBlock != null) {
			conditionBlock.getPresentationBlock().setPositionRecursivelyByDifference(deltaPos); 
		}
	}
	
	public void removeFromProgramAreaPresentationRecursively(ProgramAreaPresentation programAreaP) {
		programAreaP.removeBlock(getPresentationBlock());
		
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
