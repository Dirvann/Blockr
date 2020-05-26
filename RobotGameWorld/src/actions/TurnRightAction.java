package actions;

import game_world.GameWorld;
import game_world.api.ActionResult;

public class TurnRightAction extends ActionExecution {

	@Override
	public String getName() {
		return "Turn Right";
	}

	@Override
	public ActionResult execute(GameWorld gameWorld) {
		gameWorld.robotTurnRight();
		return ActionResult.Success;
	}

}
