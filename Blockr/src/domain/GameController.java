package domain;

import java.util.List;

import command.ExecutionCommand;
import domain.block.Block;
import domain.block.ConditionBlock;
import domain.block.FunctionCall;
import domain.block.FunctionDefinition;
import domain.block.ImplementationBlock;
import domain.block.SequenceBlock;
import domain.block.SurroundingBlock;
import game_world.api.FacadeGameWorld;
import presentation.BlockrPanel;
import presentation.block.ImplementationPresentationBlock;
import presentation.block.PresentationBlock;
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
public class GameController implements FacadeGameController{
	
	ImplementationBlock BF = new ImplementationBlock();
	ImplementationPresentationBlock BFP = new ImplementationPresentationBlock();
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
	
	public GameController() { //TODO:exceptions zijn hier weggehaald
		this.programArea = new ProgramArea();
		try {
			this.iGameWorld = FacadeGameWorld.newInstance(BlockrPanel.getImplementationClass());
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO fix dit
			e.printStackTrace();
		}
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
	
	public GameController(FacadeGameWorld iGameWorld) {
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
	@Override
	public ExecutionCommand execute() throws Exception {
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
	@Override
	public void stopExecution() {
		programArea.stopExecution();
	}
	/**
	 * The next block to execute.
	 * 
	 * @return The next block to execute.
	 * 		   |Result = programArea.nextToExecute
	 */
	@Override
	public Block getNextBlockToExecute() {
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
	@Override
	public void setGameWorldImplementation(FacadeGameWorld iGameWorld) {
		this.iGameWorld = iGameWorld;
	}

	/**
	 * The GameWorld Implementation of this GameController.
	 * 
	 * @return the GameWorld Implementation of this GameController
	 */
	@Override
	public FacadeGameWorld getGameWorldImplementation() {
		return this.iGameWorld;
	}
	/**
	 * The ProgramArea of this GameController.
	 * 
	 * @return the ProgramArea of this GameController
	 */
	@Override
	public ProgramArea getProgramArea() {
		return this.programArea;
	}
	
	@Override
	public void addBlockToProgramArea(PresentationBlock<?> pBlock) {
		getProgramArea().addBlock(pBlock);
	}
	
	@Override
	public void removeBlockFromProgramArea(PresentationBlock<?> pBlock) {
		getProgramArea().removeBlock(BFP.getBlock(pBlock), this);
	}
	@Override
	public void removeBlockFromProgramArea(Block block) {
		getProgramArea().removeBlock(block, this);
	}
	
	@Override
	public int getAmountOfBlocksLeft() {
		return getProgramArea().getBlocksLeft();
	}

	@Override
	public List<Block> getCopyOfAllBlocks() {
		return getProgramArea().getAllBlocks();		
	}
	@Override
	public void disconnect(Block block) {
		if (block != null) {
			getProgramArea().addTopLevelBlock(block);
			BF.disconnect(block);
		}
	}
	@Override
	public boolean connect(Block firstBlock, Block secondBlock) {
		if(BF.connect(firstBlock, secondBlock)) {
			getProgramArea().removeTopLevelBlock(secondBlock);
			return true;
		}
		return false;
	}
	@Override
	public void setBody(SurroundingBlock surroundingBlock, SequenceBlock block) {
		BF.setBodyBlock(surroundingBlock, block);
		getProgramArea().removeTopLevelBlock(block);
	}
	@Override
	public void setBody(FunctionDefinition surroundingBlock, SequenceBlock block) {
		BF.setBodyBlock(surroundingBlock, block);
		getProgramArea().removeTopLevelBlock(block);
	}
	@Override
	public void setCondition(SurroundingBlock surroundingBlock, ConditionBlock condition) {
		BF.setConditionBlock(surroundingBlock, condition);
		getProgramArea().removeTopLevelBlock(condition);
	}
	
	@Override
	public ExecutionCommand getExecutionCommand() {
		return getProgramArea().getExecutionCommand();
	}
	
	@Override
	public void setExecutionCommand(ExecutionCommand exeCmd) {
		getProgramArea().setExecutionCommand(exeCmd);
		
	}

	@Override
	public Boolean isExecuting() {
		return getProgramArea().programInProgress();
	}

	@Override
	public void setNewExecution(Block currentlyExecuted, Block nextToExecute) {
		getProgramArea().currentExe = currentlyExecuted;
		getProgramArea().nextToExecute = nextToExecute;
		
	}
	
	@Override
	public List<FunctionCall> getAllFunctionCallsOfID(int ID) {
		return getProgramArea().getAllFunctionCallsWithID(ID);
	}

}