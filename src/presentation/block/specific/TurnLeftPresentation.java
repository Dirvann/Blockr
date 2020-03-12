package presentation.block.specific;

import domain.block.TurnLeft;
import domain.game_world.Vector;
import presentation.block.ActionBlockPresentation;

public class TurnLeftPresentation extends ActionBlockPresentation {

	public TurnLeftPresentation(Vector pos, TurnLeft block) {
		setPosition(pos);
		setBlock(block);
	}

	@Override
	public TurnLeftPresentation getNewBlockOfThisType() {
		return new TurnLeftPresentation(getPosition(), null);
	}
	
	

}
