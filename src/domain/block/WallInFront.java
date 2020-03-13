package domain.block;

import domain.GameController;
import domain.block.abstract_classes.SingleConditionBlock;
import domain.game_world.Vector;

public class WallInFront extends SingleConditionBlock {
	
	public WallInFront(Vector pos) {
		super(pos, "Wall In Front");
	}

	public boolean evaluate(GameController gameController) {

		if (gameController == null) {
			return false;
		}		
		else
			return gameController.getGameWorld().robotWallInFront();
	}

}
