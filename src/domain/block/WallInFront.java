package domain.block;

import domain.GameController;
import domain.block.abstract_classes.SingleConditionBlock;
import domain.block.block_types.Block;

public class WallInFront extends SingleConditionBlock {
	
	public WallInFront() {
		super();
	}

	public boolean evaluate(GameController gameController) {

		if (gameController == null) {
			return false;
		}		
		else
			return gameController.getGameWorld().robotWallInFront();
	}

	@Override
	public Block getNewBlockOfThisType() {
		return new WallInFront();
	}

}
