package domain.block;

import command.MoveForwardCommand;
import domain.GameController;
class MoveForward extends ActionBlock {
	protected MoveForward() {
		super();
	}

	@Override
	protected void performAction(GameController gameController) throws Exception {
		if (gameController == null) 
			System.out.println("Move Forward");
		else {
			IGC.setExecutionCommand(new MoveForwardCommand(null, null, null, gameController), gameController);
			IGW.robotStepForwards(IGC.getGameWorld(gameController));
		}
	}

	@Override
	protected MoveForward getNewBlockOfThisType() {
		return new MoveForward();
	}

	@Override
	protected String getName() {
		// TODO Auto-generated method stub
		return "Move Forward";
	}

}
