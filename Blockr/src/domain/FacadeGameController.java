package domain;

import java.util.List;

import command.ExecutionCommand;
import domain.block.Block;
import domain.block.ConditionBlock;
import domain.block.FunctionCallBlock;
import domain.block.FunctionDefinitionBlock;
import domain.block.SequenceBlock;
import domain.block.SurroundingBlock;
import game_world.api.FacadeGameWorld;
import presentation.block.PresentationBlock;
/**
 * The Facade for the Implementation GameController class.
 * 
 * @version 4.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum
 * 		   Dirk Vanbeveren
 * 		   Geert Wesemael
 *
 */
public interface FacadeGameController {
	public FacadeGameWorld getGameWorldImplementation();
	public ProgramArea getProgramArea();
	public void addBlockToProgramArea(PresentationBlock<?> pBlock);
	public void removeBlockFromProgramArea(PresentationBlock<?> pBlock);
	public void setGameWorldImplementation(FacadeGameWorld iGameWorld);
	public List<Block> getCopyOfAllBlocks();
	public ExecutionCommand execute() throws Exception;
	public Boolean isExecuting();
	public void stopExecution();
	public int getAmountOfBlocksLeft();
	
	//functions for connecting and disconnecting blocks
	public void disconnect(Block block);
	public boolean connect(Block firstBlock, Block secondBlock);
	public void setBody(SurroundingBlock surroundingBlock, SequenceBlock block);
	public void setBody(FunctionDefinitionBlock surroundingBlock, SequenceBlock block);
	public void setCondition(SurroundingBlock surroundingBlock, ConditionBlock condition);
	
	//execution info for undo redo and manipulation of execution
	public Block getNextBlockToExecute();
	public void setExecutionCommand(ExecutionCommand exeCmd);
	public void setNewExecution(Block currentlyExecuted, Block nextToExecute);
	public List<FunctionCallBlock> getAllFunctionCallsOfID(int ID);
}
