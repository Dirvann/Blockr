package domain.block;

import domain.GameController;
import domain.block.abstract_classes.ActionBlock;
import domain.game_world.Vector;

public class TurnRight extends ActionBlock {

	public TurnRight() {
		super();
	}

	@Override
	public void performAction(GameController gameController) {

		if (gameController == null) 
			System.out.println("Turn Right");
		else
			gameController.getGameWorld().robotTurnRight();
	}

}
