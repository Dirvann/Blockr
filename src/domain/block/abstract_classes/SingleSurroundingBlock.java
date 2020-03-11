package domain.block.abstract_classes;

import domain.game_world.Vector;
import presentation.block.SingleSurroundBlockPresentation;

public class SingleSurroundingBlock extends SurroundingBlock {
	
	private SingleSurroundBlockPresentation blockPresentation;

	public SingleSurroundingBlock(Vector pos) {
		this.blockPresentation = new SingleSurroundBlockPresentation(pos, this);
	}

	public SingleSurroundBlockPresentation getBlockPresentation() {
		return blockPresentation;
	}

	public void setBlockPresentation(SingleSurroundBlockPresentation blockPresentation) {
		this.blockPresentation = blockPresentation;
	}
	
}
