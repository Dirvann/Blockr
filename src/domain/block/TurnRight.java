package domain.block;

import command.turnRightCommand;
import domain.GameController;

class TurnRight extends ActionBlock {

	protected TurnRight() {
		super();
	}

	@Override
	protected void performAction(GameController gameController) {

		if (gameController == null) 
			System.out.println("Turn Right");
		else {
			IGW.robotTurnRight(IGC.getGameWorld(gameController));
			IGC.setExecutionCommand(new turnRightCommand(null, null, null, gameController), gameController);
		}
	}

	@Override
	protected TurnRight getNewBlockOfThisType() {
		return new TurnRight();
	}

	@Override
	protected String getName() {
		// TODO Auto-generated method stub
		return "Turn Right";
	}

}
