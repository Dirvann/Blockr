package domain;

import domain.block.Block;
import domain.game_world.Direction;
import domain.game_world.GameWorld;
import domain.game_world.Grid;
import domain.game_world.ImplementationGameWorld;
import domain.game_world.Vector;
import domain.game_world.cell.Cell;

public class GameController {

	private ProgramArea programArea;
	private GameWorld gameWorld;

	ImplementationGameWorld gameWorldFunctions = new ImplementationGameWorld();

	public GameController() {
		this.programArea = new ProgramArea();
		this.gameWorld = gameWorldFunctions.makeRandomGameWorld(10, 10);
	}

	public GameController(GameWorld gameWorld) {
		this.programArea = new ProgramArea();
		this.gameWorld = gameWorld;
	}	
	
	/**
	 * Function made to use custom gameWorlds.
	 * 
	 * @param height
	 * @param width
	 * @param locations
	 * @param cells
	 * @param startPositionRobot
	 */
	public GameController(int height, int width, Vector[] locations, Cell[] cells,Vector startPositionRobot) {
		this.programArea = new ProgramArea();
		Grid grid = null;
		try {
			grid = gameWorldFunctions.makeGrid(height,width,locations,cells);
		} catch (Exception e) {
			System.out.println("The fail happens in the Grid Creation"); //TODO: fix die exception
		}
		this.gameWorld = gameWorldFunctions.makeGameWorld(grid, startPositionRobot);
	}

	public void execute() {
		if (programArea.programInProgress()) {
			executeNextBlock();
		} else {
			programArea.startExecution();
		}
	}

	public void stopExecution() {
		programArea.stopExecution();
	}

	public Block getNextBlockToExecute() {
		return programArea.getNextBlockToExecute();
	}

	private void executeNextBlock() {
		programArea.executeNextBlock(this);
	}

	public void setGameWorld(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}

	public GameWorld getGameWorld() {
		return this.gameWorld;
	}

	public void addTopLevelBlock(Block block) {
		programArea.addTopLevelBlock(block);
	}

	public boolean isTopLevelBlock(Block block) {
		return programArea.isTopLevelBlock(block);
	}

	public void removeTopLevelBlock(Block block) {
		try {
			programArea.removeTopLevelBlock(block);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ProgramArea getProgramArea() {
		return this.programArea;
	}

	public void resetWorld() {
		gameWorldFunctions.resetGameWorld(gameWorld);
	}

	public void robotTurnLeft() {
		gameWorldFunctions.robotTurnLeft(gameWorld);

	}

	public void robotTurnRight() {
		gameWorldFunctions.robotTurnRight(gameWorld);

	}

	public boolean robotWallInFront() {
		return gameWorldFunctions.robotWallInFront(gameWorld);
	}

	public void robotStepForwards() {
		gameWorldFunctions.robotStepForwards(gameWorld);
	}
	
	public Direction robotGetDirection() {
		return gameWorldFunctions.getRobotDirection(gameWorld);
	}

}
