package domain.block;

import domain.GameController;

class WallInFront extends SingleConditionBlock {
	
	
	protected WallInFront() {
		super();
	}

	@Override
	protected boolean evaluate(GameController gameController) {

		if (gameController == null) {
			return false;
		}		
		else
			return IGW.robotWallInFront(IGC.getGameWorld(gameController));
	}

	@Override
	protected WallInFront getNewBlockOfThisType() {
		return new WallInFront();
	}

	@Override
	protected String getName() {
		return "Wall In Front";
	}
	
}
