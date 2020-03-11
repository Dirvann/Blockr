package domain.block;

import domain.GameController;
import domain.block.abstract_classes.SingleConditionBlock;

public class WallInFront extends SingleConditionBlock {
	
	public WallInFront() {
		
	}
	
	public boolean evaluate(GameController gameController) {

		if (gameController == null) {
			return false;
		}		
		else
			return gameController.getGameWorld().robotWallInFront();
	}

}
