package domain;

import command.ExecutionCommand;
import domain.block.Block;
import game_world.GameWorld;
import game_world.ImplementationGameWorld;

public class GameController {

	private ProgramArea programArea;
	private ImplementationGameWorld iGameWorld;

	ImplementationGameWorld gameWorldFunctions = new ImplementationGameWorld();

	protected GameController() {
		this.programArea = new ProgramArea();
		this.iGameWorld = new ImplementationGameWorld();
	}

	protected GameController(ImplementationGameWorld iGameWorld) {
		this.programArea = new ProgramArea();
		this.iGameWorld = iGameWorld;
	}	

	protected ExecutionCommand execute() throws Exception {
		if (programArea.programInProgress()) {
			try {
				programArea.executeNextBlock(getGameWorldImplementation());
			} catch (Exception e) {
				programArea.stopExecution();
				getGameWorldImplementation().resetGameWorld();
				throw e;
			}
		} else {
			//after each try reset to original state
			getGameWorldImplementation().resetGameWorld();
			programArea.startExecution(); // this also throws exceptions
		}
		return programArea.exeCmd;
	}

	protected void stopExecution() {
		programArea.stopExecution();
	}

	protected Block getNextBlockToExecute() {
		return programArea.getNextBlockToExecute();
	}

	protected void setGameWorldImplementation(ImplementationGameWorld iGameWorld) {
		this.iGameWorld = iGameWorld;
	}

	protected ImplementationGameWorld getGameWorldImplementation() {
		return this.iGameWorld;
	}

	protected ProgramArea getProgramArea() {
		return this.programArea;
	}

}
