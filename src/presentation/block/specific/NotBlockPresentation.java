package presentation.block.specific;

import domain.block.NotBlock;
import domain.game_world.Vector;
import presentation.block.ChainConditionBlockPresentation;
import presentation.block.PresentationBlock;

public class NotBlockPresentation extends ChainConditionBlockPresentation {

	public NotBlockPresentation(Vector position, NotBlock block) {
		setPosition(position);
		setBlock(block);
		setPresentationName("Not");
	}

	@Override
	public PresentationBlock getNewBlockOfThisType() {
		return new NotBlockPresentation(getPosition(), null);
	}
	
	

}
