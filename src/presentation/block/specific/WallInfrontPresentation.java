package presentation.block.specific;

import domain.block.WallInFront;
import domain.game_world.Vector;
import presentation.block.SingleConditionBlockPresentation;

public class WallInfrontPresentation extends SingleConditionBlockPresentation {

	public WallInfrontPresentation(Vector pos, WallInFront block) {
		setPosition(pos);
		setBlock(block);
		setPresentationName("Wall In Front");
	}

	@Override
	public WallInfrontPresentation getNewBlockOfThisType() {
		return new WallInfrontPresentation(getPosition(), null);
	}

}
