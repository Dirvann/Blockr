package game_world.api;

import java.awt.Graphics;
import java.util.List;

public interface FacadeGameWorld {
	
	public void makeNewGameWorld();
	
	/**
	 * List of all valid actions
	 * 
	 * @return a list of Strings with all valid action names
	 */
	public List<String> getAllActions();
	
	/**
	 * List of all valid predicates
	 * 
	 * @return a list of Strings with all valid predicate names
	 */
	public List<String> getAllPRedicates();
	
	/**
	 * Execute an action in the gameWorld
	 * 
	 * @param action 
	 * 		  | name of the action to be executed
	 * @return ActionResult depending on the result of the action
	 * 		  | Success :  		Action executed successfully
	 * 		  | Illegal :  		Action is illegal in the current game state
	 * 		  | GoalReached : 	Action resulted in the reaching of the goal
	 * 		  | UnknownAction :	Action is not known in the current gameWorld system
	 */
	public ActionResult executeAction(String action);
	
	/**
	 * Evaluate a predicate in the gameWorld
	 * 
	 * @param predicate
	 * 		  | name of the predicate to be evaluated
	 * @return PredicateResult depending on the evaluation of the predicate
	 * 		  | True :  		Result of evaluation is true
	 * 		  | False :  		Result of evaluation is false
	 * 		  | BadPredicate :  Predicate is not known in the current gameWorld system
	 */
	public PredicateResult evaluatePredicate(String predicate);
	
	/**
	 * 
	 * 
	 * @return true if the goal is reached in the current game state
	 */
	public boolean goalReached();
	
	/**
	 * Undo an action in the gameWorld
	 * 
	 * @param action 
	 * 		  | name of the action to be undone
	 * @return ActionResult depending on the result of the undo
	 * 		  | Success :  		Undo executed successfully
	 * 		  | Illegal :  		Undo is illegal in the current game state
	 * 		  | UnknownAction :	Action is not known in the current gameWorld system
	 */
	public ActionResult undoAction(String action);
	
	/**
	 * Draw the gameWorld onto a Graphics object
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
	 * reset the gameWorld
	 */
	public void resetGameWorld();
	
	/**
	 * Make a snapshot of the current gameWorld state
	 * 
	 * @return ID of the taken snapshot
	 */
	public String makeSnapshot();
	
	/**
	 * make a snapshot of the current gameWorld state
	 * 
	 * @param snapshotID
	 *        | taken snapshots ID is equal to given ID
	 */
	public void   makeSnapshot(String snapshotID);
	
	/**
	 * get all snapshot IDs
	 * 
	 * @return List of all snapshot IDs
	 */
	public List<String> getAllSnapshots();
	
	/**
	 * Load the game state from the snapshot with given snapshotID
	 * 
	 * @param snapshotID
	 * 		  | ID of snapshot to load
	 * @post if valid ID, corresponding snapshot is loaded
	 */
	public void loadSnapshot(String snapshotID);
	
	/**
	 * remove the snapshot with the given snapshotID
	 * 
	 * @param snapshotID
	 * 		  | iD of snapshot to remove
	 * @post if valid ID, corresponding snapshot is removed
	 */
	public void removeSnapshot(String snapshotID);
	
}
