package domain.game_world;

import domain.game_world.cell.Cell;
import domain.game_world.cell.Goal;
import domain.game_world.cell.Wall;

public class ImplementationGameWorld implements FacadeGameWorld{

	public ImplementationGameWorld() {};
	
	@Override
	public GameWorld makeGameWorld(Grid grid, Robot robot) {
		return new GameWorld(grid,robot);
	}
	
	@Override
	public GameWorld makeGameWorld(Grid grid, Vector startPosition) {
		return new GameWorld(grid,startPosition);
	}

	@Override
	public GameWorld makeStandardEmptyGameWorld() {
		return new GameWorld();
	}

	@Override
	public GameWorld makeRandomGameWorld(int width, int height) {
		return new GameWorld(width, height);
	}
	
	@Override
	public Robot makeRobot(Vector location, Direction direction) {
		return new Robot(location,direction);
	}
	
	@Override
	public boolean robotWallInFront(GameWorld world) {
		return world.robotWallInFront();
	}

	@Override
	public boolean robotOnGoal(GameWorld world) {
		return world.robotOnGoal();
	}

	@Override
	public void robotStepForwards(GameWorld world) {
		world.robotStepForwards();
	}

	@Override
	public void robotTurnLeft(GameWorld world) {
		world.robotTurnLeft();
	}

	@Override
	public void robotTurnRight(GameWorld world) {
		world.robotTurnRight();
	}

	@Override
	public Grid makeGrid(int height, int width, Vector[] locations, Cell[] cells) throws Exception {
		return new Grid(height, width, locations, cells);
	}

	@Override
	public Grid makeStandardEmptyGrid() {
		return new Grid();
	}

	@Override
	public Grid makeRandomGrid(int width, int height) {
		return new Grid(width,height);
	}

	@Override
	public void resetGameWorld(GameWorld world) {
		world.resetGameWorld();
		
	}

	@Override
	public Integer getGridWidth(GameWorld world) {
		return world.getGrid().getHeight();
	}

	@Override
	public Integer getGridHeight(GameWorld world) {
		return world.getGrid().getHeight();
	}

	@Override
	public boolean isWall(GameWorld world, Integer x, Integer y) {
		try {
			Cell cell = world.getGrid().getCell(x, y);
			return (cell instanceof Wall);
		} catch (Exception e) {
			return true;
		}
	}

	@Override
	public boolean isGoal(GameWorld world, Integer x, Integer y) {
		try {
			Cell cell = world.getGrid().getCell(x, y);
			return (cell instanceof Goal);
		} catch (Exception e) {
			return true;
		}
	}

	@Override
	public Vector getRobotLocation(GameWorld world) {
		return world.getRobot().getLocation();
	}

	@Override
	public Direction getRobotDirection(GameWorld world) {
		return world.getRobot().getDirection();
	}
	
}
