package domain.block.abstract_classes;

import domain.block.block_types.Block;
import domain.game_world.Vector;
import presentation.block.SingleSurroundBlockPresentation;

public abstract class SingleSurroundingBlock extends SurroundingBlock {
	
	private SingleSurroundBlockPresentation presentationBlock;
	
	public SingleSurroundingBlock() {

	}

	@Override
	public SingleSurroundBlockPresentation getPresentationBlock() {
		return this.presentationBlock;
	}
	
	public void setConnectedBlockPositionRecursivelyByDifference(Vector deltaPos) {
		Block nextBlock = this.getNextBlock();
		Block bodyBlock = this.getBodyBlock();
		
		if (nextBlock != null) {
			nextBlock.getPresentationBlock().setPositionRecursivelyByDifference(deltaPos);
		}
		if (bodyBlock != null) {
			bodyBlock.getPresentationBlock().setPositionRecursivelyByDifference(deltaPos);
		}
	}
}
