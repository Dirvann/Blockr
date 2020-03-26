package domain.block;

import domain.GameController;

class WallInFront extends SingleConditionBlock {
	
	
	protected WallInFront() {
		super();
	}

	protected boolean evaluate(GameController gameController) {

		if (gameController == null) {
			return false;
		}		
		else
			return gameController.robotWallInFront();
	}

	@Override
	protected Block getNewBlockOfThisType() {
		return new WallInFront();
	}

	@Override
	protected String getName() {
		return "Wall In Front";
	}
	
}
