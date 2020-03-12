package domain.block;

import domain.GameController;
import domain.block.abstract_classes.ActionBlock;
import domain.game_world.Vector;

public class TurnRight extends ActionBlock {

	private final String displayName = "Turn Right";

	public TurnRight(Vector pos) {
		super(pos);
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

}
