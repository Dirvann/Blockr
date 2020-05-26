package actions;

import game_world.GameWorld;
import game_world.api.ActionResult;

/**
 * Class that executes a turn left action.
 * 
 * @version 4.0
 * @author Andreas Awouters 
 * 	       Thomas Van Erum 
 * 		   Dirk Vanbeveren 
 * 		   Geert Wesemael
 *
 */
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
