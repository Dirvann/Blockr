package domain.block;

import domain.GameController;
import domain.block.abstract_classes.ActionBlock;

public class TurnLeft extends ActionBlock {

	@Override
	public void performAction(GameController gameController) {

		if (gameController == null) 
			System.out.println("Turn Left");
		else
			gameController.getGameWorld().robotTurnLeft();
	}

}
