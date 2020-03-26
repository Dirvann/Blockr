package domain.block;

import domain.GameController;

class MoveForward extends ActionBlock {

	protected MoveForward() {
		super();
	}

	@Override
	protected void performAction(GameController gameController) {
		if (gameController == null) 
			System.out.println("Move Forward");
		else
			gameController.robotStepForwards();		
	}

	@Override
	protected Block getNewBlockOfThisType() {
		return new MoveForward();
	}

	@Override
	protected String getName() {
		// TODO Auto-generated method stub
		return "Move Forward";
	}

}
