package actions;

import game_world.GameWorld;
import game_world.api.Action;
import game_world.api.ActionResult;

/**
 * Class responsible to execute actions
 * 
 * @version 4.0
 * @author Andreas Awouters 
 * 	       Thomas Van Erum 
 * 		   Dirk Vanbeveren 
 * 		   Geert Wesemael
 *
 */
public abstract class ActionExecution implements Action{
	/**
	 * Execute this action on the given gameworld.
	 * @param gameWorld
	 *        The game world to execute the action on
	 * @return An action result.
	 */
	public abstract ActionResult execute(GameWorld gameWorld);
}
