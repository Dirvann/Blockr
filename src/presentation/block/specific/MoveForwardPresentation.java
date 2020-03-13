package presentation.block.specific;

import domain.block.MoveForward;
import domain.block.abstract_classes.ActionBlock;
import domain.game_world.Vector;
import presentation.block.ActionBlockPresentation;
import presentation.block.PresentationBlock;

public class MoveForwardPresentation extends ActionBlockPresentation {

	public MoveForwardPresentation(Vector pos, ActionBlock block) {
		setPosition(pos);
		setBlock(block);
		setPresentationName("Move Forward");
	}

	@Override
	public PresentationBlock<ActionBlock> getNewBlockOfThisType(ActionBlock block) throws Exception {
		if (block instanceof MoveForward)
			return new MoveForwardPresentation(getPosition(), block);
		else {
			throw new Exception("block is not the same type as blockPresentaion");
		}
	}
}
