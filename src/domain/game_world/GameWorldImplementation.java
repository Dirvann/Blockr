package domain.game_world;

import domain.game_world.cell.Cell;

public class GameWorldImplementation implements GameWorldFacade{

	public GameWorldImplementation() {};
	
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
	public boolean robotWallInFront() {
		return robotWallInFront();
	}

	@Override
	public boolean robotOnGoal() {
		return robotOnGoal();
	}

	@Override
	public boolean robotStepForwards() {
		return robotStepForwards();
	}

	@Override
	public boolean robotTurnLeft() {
		return robotTurnLeft();
	}

	@Override
	public boolean robotTurnRight() {
		return robotTurnRight();
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
	

}
