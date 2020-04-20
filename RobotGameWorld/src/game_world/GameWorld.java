package game_world;

import java.util.Random;

import exceptions.domainExceptions.OutOfBoundsException;
import exceptions.domainExceptions.robotExceptions.RobotEnteringWallException;
import exceptions.domainExceptions.robotExceptions.RobotMovingOffGridException;
import game_world.cell.*;

// GameWorld contains a grid and the entities that move around on that grid
// Currently only a single Robot moves around

/**
 * A class of gameWorlds, containing the grid, robot and startRobot.
 * The startrobot is used to reset the game world.
 *
 */
public class GameWorld {
	
	private Grid grid;
	private Robot robot;
	private Robot startRobot;
	
	/**
	 * Initialize a new gameworld with a given grid. 
	 * Also sets the startRobot and robot to the given robot.
	 * 
	 * @param grid
	 * 		  The grid for this new gameworld.
	 * @param robot
	 * 		  The robot for this new gameworld.
	 * @post  The grid of this new gameworld is equal to the given grid.
	 * 		  |new.getGrid() = grid
	 * @post  The robot of this new gameworld is equal to the given robot.
	 * 		  |new.getRobot() = robot
	 */
	protected GameWorld(Grid grid, Robot robot) {
		this.setGrid(grid);
		this.startRobot = new Robot(robot.getLocation(),robot.getDirection()); //make copy
		this.setRobot(robot);
	}
	/**
	 * Initialize an empty game world with the standard grid and the robot at position (0,0) and the goal at (4,4).
	 * 
	 * @post  The grid of this new gameworld is a 5x5 grid filled with empty cells.
	 * 		  |new.getGrid() = new Grid()
	 * @post  The robot of this new gameworld is equal to the given robot.
	 * 		  |new.getRobot() = new Robot(new Vector(0, 0))
	 */
	protected GameWorld() {
		this(new Grid(), new Robot(new Vector(0, 0)));
	}
	/**
	 * Initialize a gameworld with the given width and height.
	 * The robot's location and the grid are made randomly.
	 * 
	 * @param width
	 * 		  The width for the grid of this new gameworld.
	 * @param height
	 * 		  The height for the grid of this new gameworld.
	 * @post  The grid of this new gameworld is equal to a random grid with the given width and height.
	 * 		  |new.getGrid() = Grid.randomGrid(width, height)
	 * @post  The robot of this new gameworld is placed at a random possible location.
	 * 		  |new.getRobot() = new Robot(randomRobotLocation, getRandomRobotDirection())
	 */
	protected GameWorld(int width, int height) {
		this.setGrid(Grid.randomGrid(width, height));
		Vector randomRobotLocation = this.getRandomRobotLocation();
		this.startRobot = new Robot(randomRobotLocation, getRandomRobotDirection());
		this.setRobot(new Robot(startRobot));
	}
	/**
	 * The grid of the gameworld.
	 * 
	 * @return the grid of this gameworld.
	 */
	protected Grid getGrid() {
		return grid;
	}
	/**
	 * Set grid of the current gameworld.
	 * 
	 * @param grid
	 * 		  The grid for this new gameworld.
	 * @post  The grid of this gameworld is equal to the given grid.
	 * 		  |new.getGrid() = grid
	 */
	protected void setGrid(Grid grid) {
		this.grid = grid;
	}
	/**
	 * The robot of the gameworld.
	 * 
	 * @return the robot of this gameworld
	 */
	protected Robot getRobot() {
		return robot;
	}
	/**
	 * Set robot of the current gameworld.
	 * 
	 * @param robot
	 * 		  The robot for this gameworld.
	 * @post  The robot of this gameworld is equal to the given robot.
	 * 		  |new.getRobot() = robot
	 */
	protected void setRobot(Robot robot) {
		this.robot = robot;
	}
	/**
	 * Returns true if the cell in front of the robot is a wall.
	 * 
	 * @return true if the cell in front of the robot is a wall.
	 */
	protected boolean robotWallInFront() {
		try {
			Vector positionInFront = getRobot().getPositionInFront();
			return getGrid().getCell(positionInFront) instanceof Wall;
		} catch (OutOfBoundsException e) {
			return false;
		}
		
	}
	/**
	 * Returns true if the robot is located on the same position as the goal.
	 * 
	 * @return true if the robot is on the same location as the goal.
	 *       | result == getGrid().getCell(getRobot().getLocation()) instanceof Goal
	 */
	protected boolean robotOnGoal() {
		try {
			Vector currentPosition = getRobot().getLocation();
			return getGrid().getCell(currentPosition) instanceof Goal;
			
		}catch (OutOfBoundsException e) {
			return false;
		}
	}
	/**
	 * Set the location to the robot to the cell in front of the robot,
	 * if this is allowed (no walls or end of grid).
	 * 
	 * @throws RobotEnteringWallException 
	 * 		   The robot can't move into a wall.
	 * @throws RobotMovingOffGridException 
	 * 		   The robot can't move off the grid.
	 */
	protected void robotStepForwards() throws RobotEnteringWallException, RobotMovingOffGridException {
		try {
			Vector positionInFront = getRobot().getPositionInFront();
			if (getGrid().getCell(positionInFront) instanceof RobotCanEnter) {
				getRobot().stepForwards();
			} else {
				throw new RobotEnteringWallException();
			}
		} catch (OutOfBoundsException e) {
			throw new RobotMovingOffGridException();
		} 
		
	}
	/**
	 * Set the direction of the robot to left.
	 * 
	 * @post  The robot of this gameworld is looking to the left of the previous direction.
	 */
	protected void robotTurnLeft() {
		getRobot().turnLeft();
	}
	/**
	 * Set the direction of robot to right.
	 * 
	 * @post  The robot of this gameworld is looking to the right of the previous direction.
	 */
	protected void robotTurnRight() {
		getRobot().turnRight();
	}
	/**
	 * Return a random location where the robot can stand thats not the goal location.
	 * 
	 * @return a random location where the robot can stand thats not the goal location.
	 * 		  | result == getRobot().getLocation() instanceof RobotCanEnter
	 * 		  | result != getRobot().getLocation() instanceof Goal
	 */
	private Vector getRandomRobotLocation() {
		Random rand = new Random();

		int attemptX = rand.nextInt(grid.getWidth());
		int attemptY = rand.nextInt(grid.getHeight());

		try {
			if (grid.getCell(new Vector(attemptX, attemptY)) instanceof RobotCanEnter || grid.getCell(new Vector(attemptX, attemptY)) instanceof Goal) {
				return new Vector(attemptX, attemptY);
			} else {
				return getRandomRobotLocation();
			}
		} catch (OutOfBoundsException e) {
			e.printStackTrace();
		} 
		return new Vector(0, 0);
	}
	/**
	 * Return a random direction.
	 * 
	 * @return a random direction.
	 * 
	 */
	private Direction getRandomRobotDirection() {
		Random rand = new Random();
		
		int directionInt = rand.nextInt(3);
		Direction dir = Direction.UP;
		
		switch (directionInt) {
		case 0:
			dir = Direction.RIGHT;
			break;
		case 1:
			dir = Direction.DOWN;
			break;
		case 2:
			dir = Direction.LEFT;
			break;
		default:
		}
		
		return dir;
	}
	
	/**
	 * Set the robots location to the original location when the world was created.
	 * 
	 * @post The robot is location is set to it's original location.
	 * 		 |new.getRobot().getLocation() = startRobot
	 */
	protected void resetGameWorld() {
		this.setRobot(new Robot(startRobot));
	}
	
}
