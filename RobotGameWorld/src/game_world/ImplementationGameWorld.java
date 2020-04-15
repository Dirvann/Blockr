package game_world;

import java.awt.Graphics;

import game_world.api.FacadeGameWorld;
import game_world.cell.Cell;
import game_world.cell.Goal;
import game_world.cell.Wall;
import java.awt.Color;

public class ImplementationGameWorld implements FacadeGameWorld<GameWorld, Vector, Grid, Robot, Direction, Cell, Graphics>{

	public ImplementationGameWorld() {};
	
	@Override
	public GameWorld makeGameWorld(Grid grid, Robot robot) {
		return new GameWorld(grid,robot);
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
	public void robotStepForwards(GameWorld world) throws Exception {
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

	@Override
	public void drawGameWorld(Graphics g, GameWorld gameWorld, int width) {
		int worldWidth = width;
		int worldHeight = worldWidth / getGridWidth(gameWorld) * getGridHeight(gameWorld);
		
		int cellWidth = worldWidth / getGridWidth(gameWorld);
		int cellHeight = worldHeight /getGridHeight(gameWorld);
		
		
		// Vertical lines
		for (int i = 0; i < getGridWidth(gameWorld) +1; i++) {
			g.drawLine(i * cellWidth, 0, i * cellWidth, worldHeight);
		}
		
		// Horizontal lines
		for (int i = 0; i < getGridHeight(gameWorld) +1; i++) {
			g.drawLine(0, i * cellHeight, worldWidth, i * cellHeight);
		}
		
		drawCells(g, gameWorld, cellWidth, cellHeight);
		
	}
	
	void drawCells(Graphics g, GameWorld gameWorld, int cellWidth, int cellHeight) {
		for (int x = 0; x < getGridWidth(gameWorld); x++) {
			for (int y = 0; y < getGridHeight(gameWorld); y++) {
				if (isWall(gameWorld, x, y)) {
					g.setColor(Color.BLACK);
					g.fillRect(cellWidth * x, cellHeight * y, cellWidth, cellHeight);
				} else if (isGoal(gameWorld, x, y)) {
					g.setColor(Color.GREEN);
					g.fillRect(cellWidth * x, cellHeight * y, cellWidth, cellHeight);
				}
			}
		}
		
		Vector robotPostition = getRobotLocation(gameWorld);
		Direction robotDirection = getRobotDirection(gameWorld);

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

	
}
