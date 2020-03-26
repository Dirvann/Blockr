package domain.block;

import domain.GameController;

class TurnRight extends ActionBlock {

	protected TurnRight() {
		super();
	}

	@Override
	protected void performAction(GameController gameController) {

		if (gameController == null) 
			System.out.println("Turn Right");
		else
			gameController.robotTurnRight();
	}

	@Override
	protected Block getNewBlockOfThisType() {
		return new TurnRight();
	}

	@Override
	protected String getName() {
		// TODO Auto-generated method stub
		return "Turn Right";
	}

}
