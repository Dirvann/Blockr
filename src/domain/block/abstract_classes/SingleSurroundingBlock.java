package domain.block.abstract_classes;

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
}
