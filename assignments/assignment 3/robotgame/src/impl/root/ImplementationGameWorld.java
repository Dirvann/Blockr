package impl.root;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.domainExceptions.OutOfBoundsException;
import exceptions.domainExceptions.robotExceptions.RobotEnteringWallException;
import exceptions.domainExceptions.robotExceptions.RobotMovingOffGridException;
import game_world.Direction;
import game_world.GameWorld;
import game_world.Vector;
import game_world.api.ActionResult;
import game_world.api.FacadeGameWorld;
import game_world.api.PredicateResult;
import game_world.cell.Goal;
import game_world.cell.Wall;
import java.awt.Color;

public class ImplementationGameWorld implements FacadeGameWorld {

	private final int gameWorldWidth = 10;
	private final int gameWorldHeight = 10;
	
	private GameWorld gameWorld;
	
	public ImplementationGameWorld() {
		makeNewGameWorld();
		snapshots = new HashMap<>();
		snapshotIndex = 0;
	};
	
	
	/**
	 * List of all valid actions
	 * 
	 * @return a list of Strings with all valid action names
	 * 		   | "MoveForward", "TurnLeft", "TurnRight"
	 */
	@Override
	public List<String> getAllActions() {
		return Arrays.asList("MoveForward", "TurnLeft", "TurnRight");
	}
	
	/**
	 * List of all valid predicates
	 * 
	 * @return a list of Strings with all valid predicate names
	 * 		   | "WallInFront"
	 */
	@Override
	public List<String> getAllPRedicates() {
		return Arrays.asList("WallInFront");
	}

	/**
	 * Execute an action in the robotGameWorld
	 * 
	 * @param action 
	 * 		  | name of the action to be executed
	 * 		  | "MoveForward" || "TurnLeft" || "TurnRight"
	 * @return ActionResult depending on the result of the action
	 * 		  | Success :  		Action executed successfully
	 * 		  | Illegal :  		Action is illegal in the current game state
	 * 		  | GoalReached : 	Action resulted in the reaching of the goal
	 * 		  | UnknownAction :	Action is not known in the current gameWorld system
	 */
	@Override
	public ActionResult executeAction(String action) {
		switch (action) {
		case "MoveForward": {
			try {
				gameWorld.robotStepForwards();
			} catch (RobotEnteringWallException | RobotMovingOffGridException e) {
				return ActionResult.Illegal;
			}
			return ActionResult.Success;
		}
		
		case "TurnLeft": {
			gameWorld.robotTurnLeft();
			return ActionResult.Success;
		}
		
		case "TurnRight": {
			gameWorld.robotTurnRight();
			return ActionResult.Success;
		}
		
		default:
			return ActionResult.UnknownAction;
		}
	}
	
	/**
	 * Evaluate a predicate in the gameWorld
	 * 
	 * @param predicate
	 * 		  | name of the predicate to be evaluated
	 *        | "WallInFront"
	 * @return PredicateResult depending on the evaluation of the predicate
	 * 		  | True :  		Result of evaluation is true
	 * 		  | False :  		Result of evaluation is false
	 * 		  | BadPredicate :  Predicate is not known in the current gameWorld system
	 */
	@Override
	public PredicateResult evaluatePredicate(String predicate) {
		switch (predicate) {
		case "WallInFront": {
			if (gameWorld.robotWallInFront()) {
				return PredicateResult.True;
			} else {
				return PredicateResult.False;
			}
		}
		
		default:
			return PredicateResult.BadPredicate;
		}
	}
	
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
	@Override
	public ActionResult undoAction(String action) {
		switch (action) {
		case "MoveForward": {
			gameWorld.robotTurnLeft();
			gameWorld.robotTurnLeft();
			try {
				gameWorld.robotStepForwards();
			} catch (RobotEnteringWallException | RobotMovingOffGridException e) {
				return ActionResult.Illegal;
			}
			gameWorld.robotTurnLeft();
			gameWorld.robotTurnLeft();
			return ActionResult.Success;
		}
		
		case "TurnLeft": {
			gameWorld.robotTurnRight();
			return ActionResult.Success;
		}
		
		case "TurnRight": {
			gameWorld.robotTurnLeft();
			return ActionResult.Success;
		}
		
		default:
			return ActionResult.UnknownAction;
		}
	}

	/**
	 * Create a new gameMode
	 */
	@Override
	public void makeNewGameWorld() {
		this.gameWorld = new GameWorld(gameWorldWidth, gameWorldHeight);
	}
	
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
	@Override
	public void drawGameWorld(Graphics g, int width, int height) {
		int gridWidth = gameWorld.getGrid().getWidth();
		int gridHeight = gameWorld.getGrid().getHeight();
		
		int worldWidth = width;
		int worldHeight = worldWidth / gridWidth * gridHeight;
		
		int cellWidth = worldWidth / gridWidth;
		int cellHeight = worldHeight / gridHeight;
		
		
		// Vertical lines
		for (int i = 0; i < gridWidth +1; i++) {
			g.drawLine(i * cellWidth, 0, i * cellWidth, worldHeight);
		}
		
		// Horizontal lines
		for (int i = 0; i < gridHeight +1; i++) {
			g.drawLine(0, i * cellHeight, worldWidth, i * cellHeight);
		}
		
		drawCells(g, gameWorld, cellWidth, cellHeight);
		
	}
	
	private void drawCells(Graphics g, GameWorld gameWorld, int cellWidth, int cellHeight) {
		int gridWidth = gameWorld.getGrid().getWidth();
		int gridHeight = gameWorld.getGrid().getHeight();
		
		for (int x = 0; x < gridWidth; x++) {
			for (int y = 0; y < gridHeight; y++) {
				if (isWall(gameWorld, x, y)) {
					g.setColor(Color.BLACK);
					g.fillRect(cellWidth * x, cellHeight * y, cellWidth, cellHeight);
				} else if (isGoal(gameWorld, x, y)) {
					g.setColor(Color.GREEN);
					g.fillRect(cellWidth * x, cellHeight * y, cellWidth, cellHeight);
				}
			}
		}
		
		Vector robotPostition = gameWorld.getRobot().getLocation();
		Direction robotDirection = gameWorld.getRobot().getDirection();

		double circleRatio = 0.9;
		double rectWidth = 0.2;
		g.setColor(Color.RED);
		g.fillOval(cellWidth * robotPostition.getX() + (int) (cellWidth * (1 - circleRatio)),
				 cellHeight * robotPostition.getY() + (int) (cellHeight * (1 - circleRatio)),
				(int) (cellWidth * circleRatio), (int) (cellHeight * circleRatio));
		g.setColor(Color.BLACK);
		switch (robotDirection) {
		case RIGHT:
			g.fillRect(cellWidth * robotPostition.getX() + cellWidth / 2,
					cellHeight * robotPostition.getY() + (int) (cellHeight * (1 - rectWidth) / 2),
					cellWidth / 2, (int) (cellHeight * rectWidth));
			break;
		case LEFT:
			g.fillRect(cellWidth * robotPostition.getX(),
					cellHeight * robotPostition.getY() + (int) (cellHeight * (1 - rectWidth) / 2),
					cellWidth / 2, (int) (cellHeight * rectWidth));
			break;
		case UP:
			g.fillRect(cellWidth * robotPostition.getX() + (int) (cellWidth * (1 - rectWidth) / 2),
					cellHeight * robotPostition.getY(), (int) (cellWidth * rectWidth), cellHeight / 2);
			break;
		case DOWN:
			g.fillRect(cellWidth * robotPostition.getX() + (int) (cellWidth * (1 - rectWidth) / 2),
					cellHeight * robotPostition.getY() + (int) (cellHeight * (1 - rectWidth) / 2),
					(int) (cellWidth * rectWidth), cellHeight / 2);
			break;
		}
	}

	private boolean isWall(GameWorld gameWorld, int x, int y) {
		try {
			return gameWorld.getGrid().getCell(x, y) instanceof Wall;
		} catch (OutOfBoundsException e) {
			e.printStackTrace();
			System.out.println("This should not happen. see RobotGameWorld");
			return false;
		}
	}
	
	private boolean isGoal(GameWorld gameWorld, int x, int y) {
		try {
			return gameWorld.getGrid().getCell(x, y) instanceof Goal;
		} catch (OutOfBoundsException e) {
			e.printStackTrace();
			System.out.println("This should not happen. see RobotGameWorld");
			return false;
		}
		
	}

	private Map<String, GameWorld> snapshots;
	private int snapshotIndex;

	/**
	 * get all snapshot IDs
	 * 
	 * @return List of all snapshot IDs
	 */
	@Override
	public List<String> getAllSnapshots() {
		return new ArrayList<>(snapshots.keySet());
	}


	/**
	 * Load the game state from the snapshot with given snapshotID
	 * 
	 * @param snapshotID
	 * 		  | ID of snapshot to load
	 * @post if valid ID, corresponding snapshot is loaded
	 */
	@Override
	public void loadSnapshot(String snapshotName) {
		this.gameWorld = snapshots.get(snapshotName).createCopy();
	}


	/**
	 * Make a snapshot of the current gameWorld state
	 * 
	 * @return ID of the taken snapshot
	 */
	@Override
	public String makeSnapshot() {
		String snapshotName = "AutoSnapshot" + snapshotIndex;
		snapshotIndex += 1;
		makeSnapshot(snapshotName);
		return snapshotName;
	}


	/**
	 * make a snapshot of the current gameWorld state
	 * 
	 * @param snapshotID
	 *        | taken snapshots ID is equal to given ID
	 */
	@Override
	public void makeSnapshot(String snapshotName) {
		snapshots.put(snapshotName, gameWorld.createCopy());
	}


	/**
	 * remove the snapshot with the given snapshotID
	 * 
	 * @param snapshotID
	 * 		  | iD of snapshot to remove
	 * @post if valid ID, corresponding snapshot is removed
	 */
	@Override
	public void removeSnapshot(String snapshotName) {
		snapshots.remove(snapshotName);		
	}


	/**
	 * reset the gameWorld
	 */
	@Override
	public void resetGameWorld() {
		gameWorld.resetGameWorld();
	}


	/**
	 * 
	 * 
	 * @return true if the goal is reached in the current game state
	 */
	@Override
	public boolean goalReached() {
		return this.gameWorld.robotOnGoal();
	}
}
