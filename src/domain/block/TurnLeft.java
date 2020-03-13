package domain.block;

import domain.GameController;
import domain.block.abstract_classes.ActionBlock;
import domain.block.block_types.Block;

public class TurnLeft extends ActionBlock {

	public TurnLeft() {
		super();
	}

	@Override
	public void performAction(GameController gameController) {

		if (gameController == null) 
			System.out.println("Turn Left");
		else
			gameController.getGameWorld().robotTurnLeft();
	}

	@Override
	public Block getNewBlockOfThisType() {
		return new TurnLeft();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Turn Left";
	}

}
