package actions;

import exceptions.domainExceptions.robotExceptions.RobotEnteringWallException;
import exceptions.domainExceptions.robotExceptions.RobotMovingOffGridException;
import game_world.GameWorld;
import game_world.api.ActionResult;

public class MoveForwardAction extends ActionExecution  {

	@Override
	public String getName() {
		return "Move Forward";
	}

	@Override
	public ActionResult execute(GameWorld gameWorld) {
		try {
			gameWorld.robotStepForwards();
		} catch (RobotEnteringWallException | RobotMovingOffGridException e) {
			return ActionResult.Illegal;
		}
		
		return ActionResult.Success;
	}


}
