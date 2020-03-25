package domain.block;

import domain.GameController;

class TurnLeft extends ActionBlock {

	protected TurnLeft() {
		super();
	}

	@Override
	protected void performAction(GameController gameController) {

		if (gameController == null) 
			System.out.println("Turn Left");
		else
			gameController.getGameWorld().robotTurnLeft();
	}

	@Override
	protected Block getNewBlockOfThisType() {
		return new TurnLeft();
	}

	@Override
	protected String getName() {
		// TODO Auto-generated method stub
		return "Turn Left";
	}

}
