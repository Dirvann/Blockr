package game_world.api;

import java.awt.Graphics;
import java.util.List;
/**
 * The Facade for the GameWorld.
 * 
 * @version 3.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum
 * 		   Dirk Vanbeveren
 * 		   Geert Wesemael
 *
 */
public interface FacadeGameWorld {
	/**
	 * Initialize a new GameWorld.
	 */
	public void makeNewGameWorld();
	
	/**
	 * List of all valid actions.
	 * 
	 * @return a list of Strings with all valid action names
	 */
	public List<Action> getAllActions();
	
	/**
	 * List of all valid predicates.
	 * 
	 * @return a list of Strings with all valid predicate names
	 */
	public List<Predicate> getAllPRedicates();
	
	/**
	 * Execute an action in the gameWorld.
	 * 
	 * @param action 
	 * 		  | name of the action to be executed
	 * @return ActionResult depending on the result of the action
	 * 		  | Success :  		Action executed successfully
	 * 		  | Illegal :  		Action is illegal in the current game state
	 * 		  | GoalReached : 	Action resulted in the reaching of the goal
	 * 		  | UnknownAction :	Action is not known in the current gameWorld system
	 */
	public ActionResult executeAction(Action action);
	
	/**
	 * Evaluate a predicate in the gameWorld.
	 * 
	 * @param predicate
	 * 		  | name of the predicate to be evaluated
	 * @return PredicateResult depending on the evaluation of the predicate
	 * 		  | True :  		Result of evaluation is true
	 * 		  | False :  		Result of evaluation is false
	 * 		  | BadPredicate :  Predicate is not known in the current gameWorld system
	 */
	public PredicateResult evaluatePredicate(Predicate predicate);
	
	/**
	 * Check if the goal is reached.
	 * 
	 * @return true if the goal is reached in the current game state
	 */
	public boolean goalReached();
	
	
	/**
	 * Draw the gameWorld onto a Graphics object.
	 * 
	 * @param g
	 * 		  | Graphics object to draw the gameWorld on
	 * @param width
	 * 		  | allowed width of the drawing
	 * @param height
	 * 	      | allowed height of the drawing
	 * 
	 */
	public void drawGameWorld(Graphics g, int width, int height);
	
	/**
	 * Reset the gameWorld.
	 */
	public void resetGameWorld();
	
	/**
	 * Make a snapshot of the current gameWorld state
	 * 
	 * @return ID of the taken snapshot
	 */
	public Snapshot makeSnapshot();
	
	/**
	 * Load the game state from the snapshot with given snapshotID.
	 * 
	 * @param snapshotID
	 * 		  | ID of snapshot to load
	 * @post if valid ID, corresponding snapshot is loaded
	 */
	public void loadSnapshot(Snapshot snapshot);
	
	/**
	 * Return a new instance of a FacadeGameWorld Implementation using a given Implementation class.
	 * 
	 * @param implClass
	 * 		  Class of the Game World implementation.
	 * @return a new instance of FacadeGameWorld
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static FacadeGameWorld newInstance(Class<?> implClass) throws InstantiationException, IllegalAccessException {
		return (FacadeGameWorld)implClass.newInstance();
	}
	
}
