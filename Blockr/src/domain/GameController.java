package domain;

import command.ExecutionCommand;
import domain.block.Block;
import game_world.GameWorld;
import game_world.ImplementationGameWorld;

public class GameController {

	private ProgramArea programArea;
	private GameWorld gameWorld;

	ImplementationGameWorld gameWorldFunctions = new ImplementationGameWorld();
	/**
	 * Initialize a new GameController with a new ProgramArea and
	 * a random 10x10 GameWorld.
	 * 
	 * @post The ProgramArea of this GameController is a new ProgramArea
	 * 		 | new.getProgramArea = new ProgramArea()
	 * @post The GameWorld of this GameController is a 10 by 10 random GameWorld.
	 * 		 | new.getGameWorld = gameWorldFunctions.makeRandomGameWorld(10,10)
	 */
	protected GameController() {
		this.programArea = new ProgramArea();
		this.gameWorld = gameWorldFunctions.makeRandomGameWorld(10, 10);
	}
	/**
	 * Initialize a new GameController with a new ProgramArea and
	 * a given GameWorld.
	 * 
	 * @param gameWorld
	 * 		  The gameWorld for this new GameController.
	 * @post The ProgramArea of this GameController is a new ProgramArea.
	 * 		 | new.getProgramArea = new ProgramArea()
	 * @post The GameWorld of this GameController is the given gameWorld.
	 * 		 | new.getGameWorld = gameWorld
	 */
	protected GameController(GameWorld gameWorld) {
		this.programArea = new ProgramArea();
		this.gameWorld = gameWorld;
	}	
	/**
	 * Execute the next block if the program is in progress.
	 * If this execution fails the execution stops and the 
	 * gameWorld resets.
	 * When the program is not in progress, the gameworld
	 * resets and the execution starts (if possible).
	 * TODO: lijntje over return
	 *  
	 * @post If the program isn't running, then the gameworld is reset.
	 * 		 This means that the robot is set to it's startingposition.
	 * 		 |if !programArea.programInProgress()
	 * 		 |  then new.getGameWorld().getRobot() = getGameWorld().startRobot 
	 * @post If the program isn't running the program will start running.
	 * 		 |if !programArea.programInProgress()
	 * 		 |  then programArea.programInProgress() == True
	 * @post If the program is running, the next block is executed.
	 * 		 |if programArea.programInProgress()
	 * 		 |  then TODO:hier invullen
	 * TODO: exception catch met effect of met pre fzo
	 * @return TODO:dit invullen
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
				gameWorldFunctions.resetGameWorld(gameWorld);
				throw e;
			}
		} else {
			//after each try reset to original state
			gameWorldFunctions.resetGameWorld(gameWorld);
			programArea.startExecution(); // this also throws exceptions
		}
		return programArea.exeCmd;
	}
	//TODO: mag deze functie niet weg?
	protected void stopExecution() {
		programArea.stopExecution();
	}
	//TODO: mag deze functie ook niet weg?
	protected Block getNextBlockToExecute() {
		return programArea.getNextBlockToExecute();
	}
	/**
	 * Set the GameWorld of the current GameController.
	 * 
	 * @param gameWorld
	 * 		  The GameWorld for this GameController.
	 * @post  The GameWorld of this GameController is the given gameWorld.
	 * 		  |new.getGameWorld() = gameWorld
	 */
	protected void setGameWorld(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}
	/**
	 * The GameWorld of this GameController.
	 * 
	 * @return the GameWorld of this GameController
	 */
	protected GameWorld getGameWorld() {
		return this.gameWorld;
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
