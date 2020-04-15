package domain.block;

import command.turnLeftCommand;
import domain.GameController;

class TurnLeft extends ActionBlock {

	protected TurnLeft() {
		super();
	}

	@Override
	protected void performAction(GameController gameController) {

		
		if (gameController == null) 
			System.out.println("Turn Left");
		else {
			IGW.robotTurnLeft(IGC.getGameWorld(gameController));
			IGC.setExecutionCommand(new turnLeftCommand(null, null, null, gameController), gameController);
		}
	}

	@Override
	protected TurnLeft getNewBlockOfThisType() {
		return new TurnLeft();
	}

	@Override
	protected String getName() {
		// TODO Auto-generated method stub
		return "Turn Left";
	}

}
