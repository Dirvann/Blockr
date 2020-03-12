package presentation.block.specific;

import domain.block.MoveForward;
import domain.game_world.Vector;
import presentation.block.ActionBlockPresentation;
import presentation.block.PresentationBlock;

public class MoveForwardPresentation extends ActionBlockPresentation {

	public MoveForwardPresentation(Vector pos, MoveForward block) {
		setPosition(pos);
		setBlock(block);
		setPresentationName("Move Forward");
	}

	@Override
	public PresentationBlock getNewBlockOfThisType() {
		return new MoveForwardPresentation(getPosition(), null);
	}

}
