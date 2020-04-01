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

	protected GameController() {
		this.programArea = new ProgramArea();
		this.gameWorld = gameWorldFunctions.makeRandomGameWorld(10, 10);
	}

	protected GameController(GameWorld gameWorld) {
		this.programArea = new ProgramArea();
		this.gameWorld = gameWorld;
	}	
	
//	/**
//	 * Function made to use custom gameWorlds.
//	 * 
//	 * @param height
//	 * @param width
//	 * @param locations
//	 * @param cells
//	 * @param startPositionRobot
//	 */
//	public GameController(int height, int width, Vector[] locations, Cell[] cells,Vector startPositionRobot,Direction robotDirection) {
//		this.programArea = new ProgramArea();
//		Grid grid = null;
//		try {
//			grid = gameWorldFunctions.makeGrid(height,width,locations,cells);
//		} catch (Exception e) {
//			System.out.println("The fail happens in the Grid Creation"); //TODO: fix die exception
//		}
//		this.gameWorld = gameWorldFunctions.makeGameWorld(grid, gameWorldFunctions.makeRobot(startPositionRobot,robotDirection));
//	}

	protected void execute() throws Exception {
		if (programArea.programInProgress()) {
			programArea.executeNextBlock(this);
		} else {
			programArea.startExecution();
		}
	}

	protected void stopExecution() {
		programArea.stopExecution();
	}

	protected Block getNextBlockToExecute() {
		return programArea.getNextBlockToExecute();
	}

	protected void setGameWorld(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}

	protected GameWorld getGameWorld() {
		return this.gameWorld;
	}

	protected ProgramArea getProgramArea() {
		return this.programArea;
	}

//	public void resetWorld() {
//		gameWorldFunctions.resetGameWorld(gameWorld);
//	}
//
//	public void robotTurnLeft() {
//		gameWorldFunctions.robotTurnLeft(gameWorld);
//
//	}
//
//	public void robotTurnRight() {
//		gameWorldFunctions.robotTurnRight(gameWorld);
//
//	}
//
//	public boolean robotWallInFront() {
//		return gameWorldFunctions.robotWallInFront(gameWorld);
//	}
//
//	public void robotStepForwards() {
//		gameWorldFunctions.robotStepForwards(gameWorld);
//	}

}
