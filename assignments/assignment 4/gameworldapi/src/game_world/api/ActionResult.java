package game_world.api;

/**
 * 
 * Result given after executing an Action.
 */
public enum ActionResult {
	Success,
	UnknownAction,
	Illegal,
	GoalReached,
}
