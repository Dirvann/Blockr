package domain;

import java.util.List;

import command.ExecutionCommand;
import domain.block.Block;
import domain.block.ConditionBlock;
import domain.block.SequenceBlock;
import domain.block.SurroundingBlock;
import game_world.GameWorld;
import game_world.ImplementationGameWorld;
import presentation.block.PresentationBlock;

public interface FacadeGameController {
	
	public GameController makeGameController(ImplementationGameWorld iGameWorld);
	public GameController makeGameController();
	
	public void setGameWorldImplementation(GameController gameController, ImplementationGameWorld iGameWorld);
	public ImplementationGameWorld getGameWorldImplementation(GameController gameController);
	public ProgramArea getProgramArea(GameController gameController);
	
	public void addBlockToProgramArea(GameController gameController,PresentationBlock<?> pBlock);
	public void removeBlockFromProgramArea(GameController gameController,PresentationBlock<?> pBlock);
	
	public void addTopLevelBlock(GameController gameController,Block block);
	public void removeTopLevelBlock(GameController gameController,Block block);
	public List<Block> getCopyOfAllBlocks(GameController gameController);
	public Boolean isTopLevelBlock(GameController gameController, Block block);

	public ExecutionCommand execute(GameController gameController) throws Exception;
	public Boolean isExecuting(GameController GC);
	public void stopExecution(GameController gameController);
	public Block getNextBlockToExecute(GameController gameController);
	
	public int getAmountOfBlocksLeft(GameController gameController);
	
	//functions for connecting and disconnecting blocks
	public void disconnect(Block block, GameController gamecontroller);
	public boolean connect(Block firstBlock, Block secondBlock, GameController GC);
	public void setBody(SurroundingBlock surroundingBlock, SequenceBlock block, GameController GC);
	public void setCondition(SurroundingBlock surroundingBlock, ConditionBlock condition, GameController GC);
	
	//execution info for undo redo and manipulation of execution
	public Block getNextToExecute(GameController GC);
	public void setNextToExecute(GameController GC, Block block);
	public ExecutionCommand getExecutionCommand(GameController GC);
	public void setExecutionCommand(ExecutionCommand exeCmd, GameController GC);
	public void setNewExecution(Block currentlyExecuted, Block nextToExecute, GameController GC);
}
