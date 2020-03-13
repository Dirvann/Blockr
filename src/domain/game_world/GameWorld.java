package domain.game_world;

import java.util.Random;

import domain.game_world.cell.*;

// GameWorld contains a grid and the entities that move around on that grid
// Currently only a single Robot moves around
public class GameWorld {
	
	private Grid grid;
	private Robot robot;
	private Robot startRobot;
	
	public GameWorld(Grid grid, Vector startPosition) {
		this.setGrid(grid);
		this.startRobot = new Robot(startPosition);
		this.setRobot(new Robot(startPosition));
	}

	// Default 5x5 empty GameWorld with Robot at (0,0) and goal at (4, 4);
	public GameWorld() {
		this(new Grid(), new Vector(0, 0));
	}
	
	// Create random GameWorld with given width and height;
	public GameWorld(int width, int height) {
		this.setGrid(Grid.randomGrid(width, height));
		Vector randomRobotLocation = this.getRandomRobotLocation();
		this.startRobot = new Robot(randomRobotLocation, getRandomRobotDirection());
		this.setRobot(new Robot(startRobot));
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}
	
	public Robot getRobot() {
		return robot;
	}

	public void setRobot(Robot robot) {
		this.robot = robot;
	}
	
	
	// Robot commands
	
	// True if the cell in front of the robot is a Wall
	public boolean robotWallInFront() {
		try {
			Vector positionInFront = getRobot().getPositionInFront();
			return getGrid().getCell(positionInFront) instanceof Wall;
		} catch (Exception e) {
			return false;
		}
		
	}
	// True if the robots location is equal to the goal location
	public boolean robotOnGoal() {
		try {
			Vector currentPosition =getRobot().getLocation();
			return getGrid().getCell(currentPosition) instanceof Goal;
			
		}catch (Exception e) {
			return false;
		}
	}
	
	public void robotStepForwards() {
		try {
			Vector positionInFront = getRobot().getPositionInFront();
			if (getGrid().getCell(positionInFront) instanceof RobotCanEnter) {
				getRobot().stepForwards();
			}
		} catch (Exception e) {
			// Exception thrown because getCell requested cell out of bounds
			// Robot doesn't move
		}
		
	}
	
	public void robotTurnLeft() {
		getRobot().turnLeft();
	}
	
	public void robotTurnRight() {
		getRobot().turnRight();
	}
	
	private Vector getRandomRobotLocation() {
		Random rand = new Random();

		int attemptX = rand.nextInt(grid.getWidth());
		int attemptY = rand.nextInt(grid.getHeight());

		try {
			if (grid.getCell(new Vector(attemptX, attemptY)) instanceof RobotCanEnter) {
				return new Vector(attemptX, attemptY);
			} else {
				return getRandomRobotLocation();
			}
		} catch (Exception e) {
			System.out.println("This exception should not happen. See GameWorld.getRandomRobotLocation");
		}
		return new Vector(0, 0);
	}
	
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
	
	
	public void resetWorld() {
		this.setRobot(new Robot(startRobot));
	}
	
}
