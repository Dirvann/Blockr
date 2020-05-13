package domain;

import command.ExecutionCommand;
import domain.block.Block;
import game_world.api.FacadeGameWorld;
import presentation.BlockrPanel;
/**
 * A class of GameController that holds the ProgramArea and Gameworld Implementation.
 * It contains functions for the execution of the program. This class connects
 * and controls the ProgramArea and the Gameworld.
 * 
 * @version 3.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum
 * 		   Dirk Vanbeveren
 * 		   Geert Wesemael
 *
 */
public class GameController {

	private ProgramArea programArea;
	private FacadeGameWorld iGameWorld;

	/**
	 * Initialize a new GameController with a new ProgramArea and
	 * a new instance of the gameWorld.
	 * 
	 * @post The ProgramArea of this GameController is a new ProgramArea
	 * 		 | new.getProgramArea = new ProgramArea()
	 * @post The GameWorld Implementation of this GameController is set to a new implementation of the API.
	 * 		 | new.getGameWorldImplementation = FacadeGameWorld.newInstance(ClientMainClass.getImplementationClass())
	 */
	protected GameController() throws InstantiationException, IllegalAccessException {
		this.programArea = new ProgramArea();
		this.iGameWorld = FacadeGameWorld.newInstance(BlockrPanel.getImplementationClass());
	}
	/**
	 * Initialize a new GameController with a new ProgramArea and
	 * a new instance of the gameWorld.
	 * 
	 * @param iGameWorld
	 * 		  The GameWorld Implementation for this GameController.
	 * @post The ProgramArea of this GameController is a new ProgramArea
	 * 		 | new.getProgramArea = new ProgramArea()
	 * @post The GameWorld Implementation of this GameController is set to the given Implementation.
	 * 		 | new.getGameWorldImplementation = iGameWorld
	 */
	protected GameController(FacadeGameWorld iGameWorld) {
		this.programArea = new ProgramArea();
		this.iGameWorld = iGameWorld;
	}	
	/**
	 * Execute the next block if the program is in progress.
	 * If this execution fails the execution stops and the 
	 * gameWorld resets.
	 * When the program is not in progress, the gameworld
	 * resets and the execution starts (if possible).
	 * Returns the command of the execution.
	 *  
	 * @post   If the program wasn't running, then the gameworld is reset.
	 * 		   This means that the robot is set to it's startingposition.
	 * 		   |if !programArea.programInProgress()
	 * 		   |  then new.getGameWorld().getRobot() = getGameWorld().startRobot 
	 * @post   If the program wasn't running the program starts running.
	 * 		   |if !programArea.programInProgress()
	 * 		   |  then new.programArea.programInProgress() == True
	 * @post   If the program was running, the next block is going to be executed.
	 * 		   |if programArea.programInProgress()
	 * 		   |  then new.getProgramArea().currentExe = getNextBlockToExecute()
	 * @return The execution command of the executed command.
	 * 		   | return = programArea.exeCmd
	 * @throws Exception
	 * 		   The execution can't run or the exection can't start.
	 * 
	 */
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
	/**
	 * Stop the execution of the program.
	 * 
	 *@post There is no block to execute next.
	 *		|nextToExecute == null
	 */
	protected void stopExecution() {
		programArea.stopExecution();
	}
	/**
	 * The next block to execute.
	 * 
	 * @return The next block to execute.
	 * 		   |Result = programArea.nextToExecute
	 */
	protected Block getNextBlockToExecute() {
		return programArea.getNextBlockToExecute();
	}
	/**
	 * Set the GameWorld Implementation of the current GameController.
	 * 
	 * @param iGameWorld
	 * 		  The GameWorld Implementation for this GameController.
	 * @post  The GameWorld Implementation of this GameController is the given gameWorld Implementation.
	 * 		  |new.getGameWorldImplementation() = iGameWorld
	 */
	protected void setGameWorldImplementation(FacadeGameWorld iGameWorld) {
		this.iGameWorld = iGameWorld;
	}

	/**
	 * The GameWorld Implementation of this GameController.
	 * 
	 * @return the GameWorld Implementation of this GameController
	 */
	protected FacadeGameWorld getGameWorldImplementation() {
		return this.iGameWorld;
	}
	/**
	 * The ProgramArea of this GameController.
	 * 
	 * @return the ProgramArea of this GameController
	 */
	protected ProgramArea getProgramArea() {
		return this.programArea;
	}

}