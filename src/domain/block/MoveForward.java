package domain.block;

import domain.GameController;
import domain.block.abstract_classes.ActionBlock;
import domain.game_world.Vector;
import presentation.block.PresentationBlock;

public class MoveForward extends ActionBlock {

	public MoveForward() {
		super();
	}

	@Override
	public void performAction(GameController gameController) {
		if (gameController == null) 
			System.out.println("Move Forward");
		else
			gameController.getGameWorld().robotStepForwards();		
	}

}
