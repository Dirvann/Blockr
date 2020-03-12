package domain.block;

import domain.GameController;
import domain.block.abstract_classes.ActionBlock;
import domain.block.block_types.Block;

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
	
	
	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public Block getNewBlockOfThisType() {
		return new TurnRight();
	}

}
