package domain;

import client.main.ClientMainClass;
import command.ExecutionCommand;
import domain.block.Block;
import game_world.api.FacadeGameWorld;

public class GameController {

	private ProgramArea programArea;
	private FacadeGameWorld iGameWorld;

	protected GameController() throws InstantiationException, IllegalAccessException {
		this.programArea = new ProgramArea();
		this.iGameWorld = FacadeGameWorld.newInstance(ClientMainClass.getImplementationClass());
	}

	protected GameController(FacadeGameWorld iGameWorld) {
		this.programArea = new ProgramArea();
		this.iGameWorld = iGameWorld;
	}	

	protected ExecutionCommand execute() throws Exception {
		if (programArea.programInProgress()) {
			try {
				programArea.executeNextBlock(this);
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

	protected void setGameWorldImplementation(FacadeGameWorld iGameWorld) {
		this.iGameWorld = iGameWorld;
	}

	protected FacadeGameWorld getGameWorldImplementation() {
		return this.iGameWorld;
	}

	protected ProgramArea getProgramArea() {
		return this.programArea;
	}

}