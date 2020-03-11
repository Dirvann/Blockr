package domain.block;

import domain.GameController;
import domain.block.abstract_classes.ActionBlock;

public class TurnRight extends ActionBlock {

	@Override
	public void performAction(GameController gameController) {

		if (gameController == null) 
			System.out.println("Turn Right");
		else
			gameController.getGameWorld().robotTurnRight();
	}

}
