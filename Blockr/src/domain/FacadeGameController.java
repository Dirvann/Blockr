package domain;

import java.util.List;

import domain.block.Block;
import domain.block.ConditionBlock;
import domain.block.SequenceBlock;
import domain.block.SurroundingBlock;
import gameworld.GameWorld;
import presentation.block.PresentationBlock;

public interface FacadeGameController {
	
	public GameController makeGameController();
	public GameController makeGameController(GameWorld gameWorld);
	
	public void setGameWorld(GameController gameController, GameWorld gameWorld);
	public GameWorld getGameWorld(GameController gameController);
	public ProgramArea getProgramArea(GameController gameController);
	
	public void addBlockToProgramArea(GameController gameController,PresentationBlock<?> pBlock);
	public void removeBlockFromProgramArea(GameController gameController,PresentationBlock<?> pBlock);
	
	public void addTopLevelBlock(GameController gameController,Block block);
	public void removeTopLevelBlock(GameController gameController,Block block);
	public List<Block> getCopyOfAllTopLevelBlocks(GameController gameController);
	public Boolean isTopLevelBlock(GameController gameController, Block block);

	public void execute(GameController gameController) throws Exception;
	public void stopExecution(GameController gameController);
	public Block getNextBlockToExecute(GameController gameController);
	
	public int getAmountOfBlocksLeft(GameController gameController);
	
	//functions for connecting and disconnecting blocks
	public void disconnect(Block block, GameController gamecontroller);
	public boolean connect(Block firstBlock, Block secondBlock, GameController GC);
	public void setBody(SurroundingBlock surroundingBlock, SequenceBlock block, GameController GC);
	public void setCondition(SurroundingBlock surroundingBlock, ConditionBlock condition, GameController GC);
}
