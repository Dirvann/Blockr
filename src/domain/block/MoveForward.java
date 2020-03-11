package domain.block;

import domain.GameController;
import domain.block.abstract_classes.ActionBlock;

public class MoveForward extends ActionBlock {

	@Override
	public void performAction(GameController gameController) {
		if (gameController == null) 
			System.out.println("Move Forward");
		else
			gameController.getGameWorld().robotStepForwards();		
	}

}
