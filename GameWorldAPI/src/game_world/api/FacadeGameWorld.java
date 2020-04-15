package game_world.api;

public interface FacadeGameWorld<GameWorld, Vector, Grid, Robot, Direction, Cell> {
	
	public GameWorld makeGameWorld(Grid grid, Robot robot);
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
	
	
	
}
