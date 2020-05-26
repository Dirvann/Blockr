package domain;

import java.util.List;

import command.ExecutionCommand;
import domain.block.Block;
import domain.block.ConditionBlock;
import domain.block.FunctionCallBlock;
import domain.block.FunctionDefinitionBlock;
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
 * @version 4.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum
 * 		   Dirk Vanbeveren
 * 		   Geert Wesemael
 *
 */
public class GameController {
	
	ImplementationBlock BF = new ImplementationBlock();
	ImplementationPresentationBlock BFP = new ImplementationPresentationBlock();
	private ProgramArea programArea;
	private FacadeGameWorld iGameWorld;

	/**
	 * Initialize a new GameController with a new ProgramArea and
	 * a new instance of the gameWorld.
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * 
	 * @post The ProgramArea of this GameController is a new ProgramArea
	 * 		 | new.getProgramArea = new ProgramArea()
	 * @post The GameWorld Implementation of this GameController is set to a new implementation of the API.
	 * 		 | new.getGameWorldImplementation = FacadeGameWorld.newInstance(ClientMainClass.getImplementationClass())
	 */
	
	public GameController() throws InstantiationException, IllegalAccessException {
		this.programArea = new ProgramArea();
		this.iGameWorld = FacadeGameWorld.newInstance(BlockrPanel.getImplementationClass());
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
	public ExecutionCommand execute() throws Exception {
		if (getProgramArea().programInProgress()) {
			try {
				getProgramArea().executeNextBlock(this);
			} catch (Exception e) {
				getProgramArea().stopExecution();
				getGameWorldImplementation().resetGameWorld();
				throw e;
			}
		} else {
			//after each try reset to original state
			getGameWorldImplementation().resetGameWorld();
			getProgramArea().startExecution(); // this also throws exceptions
		}
		return getProgramArea().exeCmd;
	}
	/**
	 * Stop the execution of the program.
	 * 
	 *@post There is no block to execute next.
	 *		|nextToExecute == null
	 */
	public void stopExecution() {
		getProgramArea().stopExecution();
	}
	/**
	 * The next block to execute.
	 * 
	 * @return The next block to execute.
	 * 		   |Result = getProgramArea().getNextToExecute()
	 */
	public Block getNextBlockToExecute() {
		return getProgramArea().getNextBlockToExecute();
	}
	/**
	 * Set the GameWorld Implementation of the current GameController.
	 * 
	 * @param iGameWorld
	 * 		  The GameWorld Implementation for this GameController.
	 * @post  The GameWorld Implementation of this GameController is the given gameWorld Implementation.
	 * 		  |new.getGameWorldImplementation() = iGameWorld
	 */
	public void setGameWorldImplementation(FacadeGameWorld iGameWorld) {
		this.iGameWorld = iGameWorld;
	}

	/**
	 * The GameWorld Implementation of this GameController.
	 * 
	 * @return the GameWorld Implementation of this GameController
	 */
	public FacadeGameWorld getGameWorldImplementation() {
		return this.iGameWorld;
	}
	/**
	 * The ProgramArea of this GameController.
	 * 
	 * @return the ProgramArea of this GameController
	 */
	
	public ProgramArea getProgramArea() {
		return this.programArea;
	}
	
	/**
	 * Add the given Block Presentation to the ProgramArea
	 * 
	 * @param pBlock
	 * 		  The Presentation Block to add to the programArea.
	 * @post  The list of top level blocks in ProgramArea contains the
	 * 		  block associated to the given pBlock.
	 *        | !getProgramArea().getTopBlocks().contains(BFP.getBlock(pBlock))
	 * @post  The amount of blocks left is lowered by one. 
	 * 		  | new.getAmountOfBlocksLeft() == getAmountOfBlocksLeft() - 1
	 */
	public void addBlockToProgramArea(PresentationBlock<?> pBlock) {
		getProgramArea().addBlock(pBlock);
	}
	
	/**
	 * Remove the given Block Presentation from the ProgramArea
	 * 
	 * @param pBlock 
	 * 		  The Presentation Block to add to the programArea
	 * @post  The list of top level blocks in ProgramArea does not contain 
	 * 		  the block associated to the given pBlock.
	 *        | !getProgramArea().getTopBlocks().contains(BFP.getBlock(pBlock))
	 * @post  One is added to the amount of blocks left. 
	 * 		  | new.getAmountOfBlocksLeft() == getAmountOfBlocksLeft() + 1
	 */
	public void removeBlockFromProgramArea(PresentationBlock<?> pBlock) {
		getProgramArea().removeBlock(BFP.getBlock(pBlock), this);
	}

	/**
	 * Return the amount of blocks that can be added to the ProgramArea.
	 * 
	 * @return the amount of blocks that can be added to the ProgramArea.
	 * 		   |Result = getProgramArea().getBlocksLeft()
	 */
	public int getAmountOfBlocksLeft() {
		return getProgramArea().getBlocksLeft();
	}

	/**
	 * Return a copy of all the blocks in the ProgramArea.
	 * 
	 * @return a copy of all the blocks in the ProgramArea.
	 * 		   |Result = getProgramArea().getAllBlocks()
	 */
	public List<Block> getCopyOfAllBlocks() {
		return getProgramArea().getCopyOfAllBlocks();		
	}
	/**
	 * Disconnect the current block from the blocks above, 
	 * to the left of or around this block. Add the block as
	 * a TopLevelBlock in the ProgramArea.
	 * 
	 * @param block
	 * 		  The block to disconnect.
	 * @post  The given block is a TopLevelBlock.
	 * 		  | getProgramArea().getTopBlocks().contains(block)
	 * @post  For all the blocks in the ProgramArea, no block has the
	 * 		  the given block as it's next block.
	 */
	public void disconnect(Block block) {
		if (block != null) {
			getProgramArea().addTopLevelBlock(block);
			BF.disconnect(block);
		}
	}
	/**
	 * Connect the firstBlock to secondBlock. The secondBlock is
	 * removed from the ProgramArea. Return true if blocks are connected
	 * successfully.
	 * 
	 * @param firstBlock
	 * 		  The block that gets connected to.
	 * @param secondBlock
	 * 		  The block to connect to the firstBlock.
	 * @post  For all the blocks in the ProgramArea, no block has the
	 * 		  the given block as it's next block.
	 * @return true if blocks are connected successfully.
	 */
	public boolean connect(Block firstBlock, Block secondBlock) {
		if(BF.connect(firstBlock, secondBlock)) {
			getProgramArea().removeTopLevelBlock(secondBlock);
			return true;
		}
		return false;
	}
	/**
	 * Set the body of the given surroundingBlock to the given block.
	 * 
	 * @param surroundingBlock
	 * 		  The SurroundingBlock to add block as body to.
	 * @param block
	 * 		  The SequenceBlock to put inside the body of the SurroundingBlock.
	 * @post  The given block is not a Toplevel Block in programArea.
	 * 		  | !getProgramArea().getTopBlocks().contains(BFP.getBlock(block))
	 */
	public void setBody(SurroundingBlock surroundingBlock, SequenceBlock block) {
		BF.setBodyBlock(surroundingBlock, block);
		getProgramArea().removeTopLevelBlock(block);
	}
	/**
	 * Set the body of the given functionDefBlock to the given block.
	 * 
	 * @param functionDefBlock
	 * 		  The FunctionDefinitionBlock to add block as body to.
	 * @param block
	 * 		  The SequenceBlock to put inside the body of the FunctionDefinitionBlock.
	 * @post  The given block is not a Toplevel Block in programArea.
	 * 		  | !getProgramArea().getTopBlocks().contains(BFP.getBlock(block))
	 */
	public void setBody(FunctionDefinitionBlock functionDefBlock, SequenceBlock block) {
		BF.setBodyBlock(functionDefBlock, block);
		getProgramArea().removeTopLevelBlock(block);
	}
	/**
	 * Set the ConditionBlock of the given surroundingBlock to the given condition.
	 * 
	 * @param surroundingBlock
	 * 		  The SurroundingBlock to add condition as Condition to.
	 * @param condition
	 * 		  The ConditionBlock to put as condition of the SurroundingBlock.
	 * @post  The given condition is not a Toplevel Block in programArea.
	 * 		  | !getProgramArea().getTopBlocks().contains(BFP.getBlock(condition))
	 */
	public void setCondition(SurroundingBlock surroundingBlock, ConditionBlock condition) {
		BF.setConditionBlock(surroundingBlock, condition);
		getProgramArea().removeTopLevelBlock(condition);
	}
	
	/**
	 * Set the ExecutionCommand of the ProgramArea to 
	 * the given ExecutionCommand.
	 * 
	 * @param exeCmd
	 * 		  The given executioner command to set.
	 * @post  The given exeCmd is set as Execution Command for
	 * 		  the ProgramArea.
	 * 		  | getProgramArea().getExecutionCommand() == exeCmd
	 */
	public void setExecutionCommand(ExecutionCommand exeCmd) {
		getProgramArea().setExecutionCommand(exeCmd);
		
	}

	/**
	 * Returns true if the program is in progress.
	 * 
	 * @return true if the program is in progress.
	 * 		   |Result = getProgramArea().programInProgress()
	 */
	public Boolean isExecuting() {
		return getProgramArea().programInProgress();
	}

	/**
	 * Remember the block that is executed right now and te block
	 * that is next to be executed.
	 * 
	 * @param currentlyExecuted
	 * 		  The currently executed block.
	 * @param nextToExecute
	 * 		  The block to execute after the currently executed block.
	 * @post  The currentely executed Block of ProgramArea is set to the
	 * 		  given currentlyExecuted.
	 * 		  | new.getProgramArea().currentExe == currentlyExecuted
	 * @post  The Block to execute next in ProgramArea is set to the
	 * 		  given nextToExecute.
	 * 		  | new.getProgramArea().nextToExecute == nextToExecute
	 */
	public void setNewExecution(Block currentlyExecuted, Block nextToExecute) {
		getProgramArea().currentExe = currentlyExecuted;
		getProgramArea().nextToExecute = nextToExecute;
		
	}
	
	/**
	 * Return all the FunctionCallBlocks with the same ID as the given ID.
	 * 
	 * @param  ID
	 * 		   The given ID that all the returned FunctionCallBlocks have.
	 * @return a list of all the FunctionCallBlocks with the same ID as the given ID.
	 * 		   | Result = getProgramArea().getAllFunctionCallsWithID(ID)
	 */
	public List<FunctionCallBlock> getAllFunctionCallsOfID(int ID) {
		return getProgramArea().getAllFunctionCallsWithID(ID);
	}

}