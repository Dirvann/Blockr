public interface FacadeGameWorld<GameWorld, Robot, Vector, Direction, Cell, Grid> {
	
	public GameWorld makeGameWorld(Grid grid, Robot robot);
	// TODO this function overrides the above function
	public GameWorld makeGameWorld(Grid grid, Vector startPosition);
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
