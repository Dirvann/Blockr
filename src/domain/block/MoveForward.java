package domain.block;

import domain.GameController;
import domain.block.abstract_classes.ActionBlock;
import domain.block.block_types.Block;

public class MoveForward extends ActionBlock {
	private final String displayName = "Move Forward";

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

	@Override
	public Block getNewBlockOfThisType() {
		return new MoveForward();
	}

}
