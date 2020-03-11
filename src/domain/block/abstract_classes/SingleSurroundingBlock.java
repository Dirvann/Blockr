package domain.block.abstract_classes;

import domain.game_world.Vector;
import presentation.block.SingleSurroundBlockPresentation;

public abstract class SingleSurroundingBlock extends SurroundingBlock {
	
	private SingleSurroundBlockPresentation presentationBlock;
	
	public SingleSurroundingBlock(Vector pos) {
		this.presentationBlock = new SingleSurroundBlockPresentation(pos, this);
	}
	
	@Override
	public SingleSurroundBlockPresentation getPresentationBlock() {
		return this.presentationBlock;
	}
}
