package presentation.block.specific;

import domain.block.TurnRight;
import domain.game_world.Vector;
import presentation.block.ActionBlockPresentation;

public class TurnRightPresentation extends ActionBlockPresentation {

	public TurnRightPresentation(Vector pos, TurnRight block) {
		setPosition(pos);
		setBlock(block);
		setPresentationName("Turn Right");
	}

	@Override
	public TurnRightPresentation getNewBlockOfThisType() {
		return new TurnRightPresentation(getPosition(), null);
	}
	
	

}
