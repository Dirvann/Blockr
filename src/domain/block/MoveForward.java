package domain.block;

import domain.GameController;
import domain.block.abstract_classes.ActionBlock;
import domain.game_world.Vector;
import presentation.block.PresentationBlock;

public class MoveForward extends ActionBlock {
	private final String displayName = "Move Forward";

	public MoveForward(Vector pos) {
		super(pos);
	}

	@Override
	public void performAction(GameController gameController) {
		if (gameController == null)
			System.out.println("Move Forward");
		else
			gameController.getGameWorld().robotStepForwards();
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}
}
