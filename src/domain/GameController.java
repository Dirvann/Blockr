package domain;

import domain.block.Block;
import domain.game_world.GameWorld;
import domain.game_world.ImplementationGameWorld;

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

}
