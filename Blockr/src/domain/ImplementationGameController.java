package domain;

import java.util.List;

import domain.block.Block;
import domain.block.ConditionBlock;
import domain.block.ImplementationBlock;
import domain.block.SequenceBlock;
import domain.block.SurroundingBlock;
import domain.game_world.GameWorld;
import presentation.block.PresentationBlock;

public class ImplementationGameController implements FacadeGameController{


	ImplementationBlock BF = new ImplementationBlock();
	
	public ImplementationGameController() {};

	@Override
	public GameController makeGameController() {
		return new GameController();
	}

	@Override
	public GameController makeGameController(GameWorld gameWorld) {
		return new GameController(gameWorld);
	}

	@Override
	public void setGameWorld(GameController gameController,GameWorld gameWorld) {
		gameController.setGameWorld(gameWorld);
	}

	@Override
	public GameWorld getGameWorld(GameController gameController) {
		return gameController.getGameWorld();
	}

	@Override
	public ProgramArea getProgramArea(GameController gameController) {
		return gameController.getProgramArea();
	}
	
	@Override
	public void addTopLevelBlock(GameController gameController, Block block) {
		gameController.getProgramArea().addTopLevelBlock(block);
		
	}

	@Override
	public void removeTopLevelBlock(GameController gameController, Block block) {
		gameController.getProgramArea().removeTopLevelBlock(block);
		
	}

	@Override
	public void execute(GameController gameController) throws Exception {
		gameController.execute();
	}

	@Override
	public void stopExecution(GameController gameController) {
		gameController.stopExecution();
	}

	@Override
	public Block getNextBlockToExecute(GameController gameController) {
		return gameController.getNextBlockToExecute();
	}

	@Override
	public void addBlockToProgramArea(GameController gameController, PresentationBlock<?> pBlock) {
		gameController.getProgramArea().addBlock(pBlock);
		
	}

	@Override
	public void removeBlockFromProgramArea(GameController gameController, PresentationBlock<?> pBlock) {
		gameController.getProgramArea().removeBlock(pBlock);
		
	}

	@Override
	public Boolean isTopLevelBlock(GameController gameController, Block block) {
		return gameController.getProgramArea().isTopLevelBlock(block);
	}

	@Override
	public int getAmountOfBlocksLeft(GameController gameController) {
		return gameController.getProgramArea().getBlocksLeft();
	}

	@Override
	public List<Block> getCopyOfAllTopLevelBlocks(GameController gameController) {
		return gameController.getProgramArea().getAllBlocks();		
	}

	@Override
	public void disconnect(Block block, GameController gamecontroller) {
		if (block != null) {
			gamecontroller.getProgramArea().addTopLevelBlock(block);
			BF.disconnect(block);
		}
		
	}

	@Override
	public boolean connect(Block firstBlock, Block secondBlock, GameController GC) {
		if(BF.connect(firstBlock, secondBlock)) {
			GC.getProgramArea().removeTopLevelBlock(secondBlock);
			return true;
		}
		return false;
	}

	@Override
	public void setBody(SurroundingBlock surroundingBlock, SequenceBlock block, GameController GC) {
		BF.addBodyBlock(surroundingBlock, block);
		GC.getProgramArea().removeTopLevelBlock(block);
		
	}

	@Override
	public void setCondition(SurroundingBlock surroundingBlock, ConditionBlock condition, GameController GC) {
		BF.setConditionBlock(surroundingBlock, condition);
		GC.getProgramArea().removeTopLevelBlock(condition);
		
	}


}