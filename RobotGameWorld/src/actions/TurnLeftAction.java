package actions;

import game_world.GameWorld;
import game_world.api.ActionResult;

public class TurnLeftAction extends ActionExecution {

	@Override
	public String getName() {
		return "Turn Left";
	}

	@Override
	public ActionResult execute(GameWorld gameWorld) {
		gameWorld.robotTurnLeft();
		return ActionResult.Success;
	}

}
