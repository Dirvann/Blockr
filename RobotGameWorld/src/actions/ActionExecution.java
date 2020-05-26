package actions;

import game_world.GameWorld;
import game_world.api.Action;
import game_world.api.ActionResult;

public abstract class ActionExecution implements Action{
	public abstract ActionResult execute(GameWorld gameWorld);
}
