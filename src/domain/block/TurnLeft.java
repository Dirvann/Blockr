package domain.block;

import domain.GameController;
import domain.block.abstract_classes.ActionBlock;
import domain.game_world.Vector;

public class TurnLeft extends ActionBlock {

	public TurnLeft(Vector pos) {
		super(pos, "Turn Left");
	}

	@Override
	public void performAction(GameController gameController) {

		if (gameController == null)
			System.out.println("Turn Left");
		else
			gameController.getGameWorld().robotTurnLeft();
	}

}
