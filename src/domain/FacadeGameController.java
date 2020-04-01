package domain;

import java.util.List;

import domain.block.Block;
import domain.game_world.GameWorld;
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
}
