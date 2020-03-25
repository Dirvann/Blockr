package domain.block;

import domain.GameController;
import domain.game_world.Vector;

class WallInFront extends SingleConditionBlock {
	
	
	protected WallInFront() {
		super();
	}

	protected boolean evaluate(GameController gameController) {

		if (gameController == null) {
			return false;
		}		
		else
			return gameController.getGameWorld().robotWallInFront();
	}

	@Override
	protected Block getNewBlockOfThisType() {
		return new WallInFront();
	}

	@Override
	protected String getName() {
		// TODO Auto-generated method stub
		return "Wall In Front";
	}

	protected void setConnectedBlockPositionRecursivelyByDifference(Vector deltaPos) {
		// Intentionally blank
	}
	
	
}
