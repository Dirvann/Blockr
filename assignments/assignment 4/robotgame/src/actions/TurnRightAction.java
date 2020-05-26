package actions;

import game_world.GameWorld;
import game_world.api.ActionResult;
/**
 * Class that executes a turn right action.
 * 
 * @version 4.0
 * @author Andreas Awouters 
 * 	       Thomas Van Erum 
 * 		   Dirk Vanbeveren 
 * 		   Geert Wesemael
 *
 */
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
