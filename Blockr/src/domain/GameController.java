package domain;

import domain.block.Block;
import domain.game_world.Direction;
import domain.game_world.GameWorld;
import domain.game_world.Grid;
import domain.game_world.ImplementationGameWorld;
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

	protected void execute() throws Exception {
		if (programArea.programInProgress()) {
			try {
				programArea.executeNextBlock(this);
			} catch (Exception e) {
				programArea.stopExecution();
				gameWorldFunctions.resetGameWorld(gameWorld);
				throw e;
			}
		} else {
			//after each try reset to original state
			gameWorldFunctions.resetGameWorld(gameWorld);
			programArea.startExecution(); // this also throws exceptions
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

}