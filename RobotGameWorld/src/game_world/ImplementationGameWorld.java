package game_world;

import java.awt.Graphics;
import java.util.Arrays;
import java.util.List;

import exceptions.domainExceptions.OutOfBoundsException;
import exceptions.domainExceptions.robotExceptions.RobotEnteringWallException;
import exceptions.domainExceptions.robotExceptions.RobotMovingOffGridException;
import game_world.api.ActionResult;
import game_world.api.FacadeGameWorld;
import game_world.api.PredicateResult;
import game_world.cell.Goal;
import game_world.cell.Wall;
import java.awt.Color;

public class ImplementationGameWorld implements FacadeGameWorld {

	private final int gameWorldWidth = 10;
	private final int gameWorldHeight = 10;
	
	private GameWorld gameWorld;
	
	public ImplementationGameWorld() {
		makeNewGameWorld();
	};
	
	
	
	@Override
	public List<String> getAllActions() {
		return Arrays.asList("MoveForward", "TurnLeft", "TurnRight");
	}
	
	@Override
	public List<String> getAllPRedicates() {
		return Arrays.asList("WallInFront");
	}

	@Override
	public ActionResult executeAction(String action) {
		switch (action) {
		case "MoveForward": {
			try {
				gameWorld.robotStepForwards();
			} catch (RobotEnteringWallException | RobotMovingOffGridException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ActionResult.Success;
		}
		
		case "TurnLeft": {
			gameWorld.robotTurnLeft();
			return ActionResult.Success;
		}
		
		case "TurnRight": {
			gameWorld.robotTurnRight();
			return ActionResult.Success;
		}
		
		default:
			return ActionResult.BadAction;
		}
	}
	
	@Override
	public PredicateResult evaluatePredicate(String predicate) {
		switch (predicate) {
		case "WallInFront": {
			if (gameWorld.robotWallInFront()) {
				return PredicateResult.True;
			} else {
				return PredicateResult.False;
			}
		}
		
		default:
			return PredicateResult.BadPredicate;
		}
	}
	
	@Override
	public boolean undoAction(String action) {
		// TODO
		return false;
	}

	@Override
	public void makeNewGameWorld() {
		this.gameWorld = new GameWorld(gameWorldWidth, gameWorldHeight);
	}
	
	@Override
	public void drawGameWorld(Graphics g, int width, int height) {
		int gridWidth = gameWorld.getGrid().getWidth();
		int gridHeight = gameWorld.getGrid().getHeight();
		
		int worldWidth = width;
		int worldHeight = worldWidth / gridWidth * gridHeight;
		
		int cellWidth = worldWidth / gridWidth;
		int cellHeight = worldHeight / gridHeight;
		
		
		// Vertical lines
		for (int i = 0; i < gridWidth +1; i++) {
			g.drawLine(i * cellWidth, 0, i * cellWidth, worldHeight);
		}
		
		// Horizontal lines
		for (int i = 0; i < gridHeight +1; i++) {
			g.drawLine(0, i * cellHeight, worldWidth, i * cellHeight);
		}
		
		drawCells(g, gameWorld, cellWidth, cellHeight);
		
	}
	
	private void drawCells(Graphics g, GameWorld gameWorld, int cellWidth, int cellHeight) {
		int gridWidth = gameWorld.getGrid().getWidth();
		int gridHeight = gameWorld.getGrid().getHeight();
		
		for (int x = 0; x < gridWidth; x++) {
			for (int y = 0; y < gridHeight; y++) {
				if (isWall(gameWorld, x, y)) {
					g.setColor(Color.BLACK);
					g.fillRect(cellWidth * x, cellHeight * y, cellWidth, cellHeight);
				} else if (isGoal(gameWorld, x, y)) {
					g.setColor(Color.GREEN);
					g.fillRect(cellWidth * x, cellHeight * y, cellWidth, cellHeight);
				}
			}
		}
		
		Vector robotPostition = gameWorld.getRobot().getLocation();
		Direction robotDirection = gameWorld.getRobot().getDirection();

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

	private boolean isWall(GameWorld gameWorld, int x, int y) {
		try {
			return gameWorld.getGrid().getCell(x, y) instanceof Wall;
		} catch (OutOfBoundsException e) {
			e.printStackTrace();
			System.out.println("This should not happen. see RobotGameWorld");
			return false;
		}
	}
	
	private boolean isGoal(GameWorld gameWorld, int x, int y) {
		try {
			return gameWorld.getGrid().getCell(x, y) instanceof Goal;
		} catch (OutOfBoundsException e) {
			e.printStackTrace();
			System.out.println("This should not happen. see RobotGameWorld");
			return false;
		}
		
	}



	@Override
	public List<String> getAllSnapshots() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void loadSnapshot(String arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public String makeSnapshot() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void makeSnapshot(String arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void removeSnapshot(String arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void resetGameWorld() {
		// TODO Auto-generated method stub
		
	}

	
}
