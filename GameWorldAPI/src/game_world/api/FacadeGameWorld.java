package game_world.api;

import java.awt.Graphics;
import java.util.List;

public interface FacadeGameWorld {
	
	public void makeNewGameWorld();
	
	public List<String> getAllActions();
	public List<String> getAllPRedicates();
	
	public boolean executeAction(String action);
	public boolean evaluatePredicate(String predicate);
	
	
	public void drawGameWorld(Graphics g, int width, int height);
	
	public boolean undoAction(String action);
	
	public void resetGameWorld();
	
	public String makeSnapshot();
	public void   makeSnapshot(String snapshotID);
	public List<String> getAllSnapshots();
	public void loadSnapshot(String snapshotID);
	public void removeSnapshot(String snapshotID);
	
	
	
	/*public GameWorld makeGameWorld(Grid grid, Robot robot);
	public GameWorld makeStandardEmptyGameWorld();
	public GameWorld makeRandomGameWorld(int width,int height);
	public void resetGameWorld(GameWorld world);
	
	public Robot makeRobot(Vector location,Direction direction);
	public boolean robotWallInFront(GameWorld world);
	public boolean robotOnGoal(GameWorld world);
	
	public void robotStepForwards(GameWorld world) throws Exception;
	public void robotTurnLeft(GameWorld world);
	public void robotTurnRight(GameWorld world);
	
	public Grid makeGrid(int height, int width, Vector[] locations,Cell[] cells) throws Exception;
	public Grid makeStandardEmptyGrid();
	public Grid makeRandomGrid(int width, int height);
	
	public Integer getGridWidth(GameWorld world);
	public Integer getGridHeight(GameWorld world);
	
	public boolean isWall(GameWorld world, Integer x, Integer y);
	public boolean isGoal(GameWorld world, Integer x, Integer y);
	public Vector getRobotLocation(GameWorld world);
	public Direction getRobotDirection(GameWorld world);
	
	public void drawGameWorld(Graphics g, GameWorld gameWorld, int width);
	
	
	public void undo(String action);*/
	
	
	
}
