package domain.block;

import domain.GameController;
import domain.block.abstract_classes.ActionBlock;

public class MoveForward extends ActionBlock {

	@Override
	public void performAction(GameController gameController) {
		gameController.getGameWorld().robotStepForwards();		
	}

}
