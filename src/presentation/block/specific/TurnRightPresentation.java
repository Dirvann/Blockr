package presentation.block.specific;

import domain.block.TurnRight;
import domain.block.abstract_classes.ActionBlock;
import domain.game_world.Vector;
import presentation.block.ActionBlockPresentation;
import presentation.block.PresentationBlock;

public class TurnRightPresentation extends ActionBlockPresentation {

	public TurnRightPresentation(Vector pos, ActionBlock block) {
		setPosition(pos);
		setBlock(block);
		setPresentationName("Turn Right");
	}

	@Override
	public PresentationBlock<ActionBlock> getNewBlockOfThisType(ActionBlock block) throws Exception {
		if (block instanceof TurnRight)
			return new TurnRightPresentation(getPosition(), block);
		else {
			throw new Exception("block is not the same type as blockPresentaion");
		}
	}
	
	

}
