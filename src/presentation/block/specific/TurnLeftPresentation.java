package presentation.block.specific;

import domain.block.TurnLeft;
import domain.block.abstract_classes.ActionBlock;
import domain.game_world.Vector;
import presentation.block.ActionBlockPresentation;
import presentation.block.PresentationBlock;

public class TurnLeftPresentation extends ActionBlockPresentation {

	public TurnLeftPresentation(Vector pos, ActionBlock block) {
		setPosition(pos);
		setBlock(block);
		setPresentationName("Turn Left");
	}

	@Override
	public PresentationBlock<ActionBlock> getNewBlockOfThisType(ActionBlock block) throws Exception {
		if (block instanceof TurnLeft)
			return new TurnLeftPresentation(getPosition(), block);
		else {
			throw new Exception("block is not the same type as blockPresentaion");
		}
	}
	
	

}
