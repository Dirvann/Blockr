package domain.game_world;

import domain.game_world.cell.*;

// GameWorld contains a grid and the entities that move around on that grid
// Currently only a single Robot moves around
public class GameWorld {
	
	private Grid grid;
	private Robot robot;

	
	public GameWorld(Grid grid, Vector startPosition) {
		this.setGrid(grid);
		this.setRobot(new Robot(startPosition));
	}

	// Default 5x5 empty GameWorld with Robot at (0,0) and goal at (4, 4);
	public GameWorld() {
		this(new Grid(), new Vector(0, 0));
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
			if (getGrid().getCell(positionInFront) instanceof RobotCanEnter);
				getRobot().stepForwards();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void robotTurnLeft() {
		getRobot().turnLeft();
	}
	
	public void robotTurnRight() {
		getRobot().turnRight();
	}
	
}
