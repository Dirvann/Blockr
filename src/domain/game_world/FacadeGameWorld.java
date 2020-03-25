package domain.game_world;

import domain.game_world.cell.Cell;

public interface GameWorldFacade {
	
	public GameWorld makeGameWorld(Grid grid, Vector startPosition);
	public GameWorld makeStandardEmptyGameWorld();
	public GameWorld makeRandomGameWorld(int width,int height);
	public void resetGameWorld();
	
	public boolean robotWallInFront();
	public boolean robotOnGoal();
	public boolean robotStepForwards();
	public boolean robotTurnLeft();
	public boolean robotTurnRight();
	
	public Grid makeGrid(int height, int width, Vector[] locations,Cell[] cells) throws Exception;
	public Grid makeStandardEmptyGrid();
	public Grid makeRandomGrid(int width, int height);
}
