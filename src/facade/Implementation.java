package facade;

<<<<<<< Updated upstream:src/facade/Implementation.java
import domain.GameController;
import domain.block.IfBlock;
import domain.block.MoveForward;
import domain.block.NotBlock;
import domain.block.TurnLeft;
import domain.block.TurnRight;
import domain.block.WallInFront;
import domain.block.WhileBlock;
import domain.block.abstract_classes.ChainConditionBlock;
import domain.block.abstract_classes.SurroundingBlock;
import domain.block.block_types.Block;
import domain.block.block_types.ConditionBlock;
import domain.block.block_types.SequenceBlock;
import domain.game_world.GameWorld;
import domain.game_world.Grid;
import domain.game_world.Vector;

public class Implementation implements Facade{
	
	public Implementation() {};
=======
public class ImplementationBlock implements FacadeBlock{
	
	public ImplementationBlock() {};
>>>>>>> Stashed changes:src/domain/block/ImplementationBlock.java

	@Override
	public Block makeIfBlock() {
		Block block = new IfBlock();
		return block;
	}

	@Override
	public Block makeMoveForwardBlock() {
		Block block = new MoveForward();
		return block;
	}

	@Override
	public Block makeNotBlock() {
		Block block = new NotBlock();
		return block;
	}

	@Override
	public Block makeTurnLeftBlock() {
		Block block = new TurnLeft();
		return block;
	}

	@Override
	public Block makeTurnRightBlock() {
		Block block = new TurnRight();
		return block;
	}

	@Override
	public Block makeWallInFrontBlock() {
		Block block = new WallInFront();
		return block;
	}

	@Override
	public Block makeWhileBlock() {
		Block block = new WhileBlock();
		return block;
	}

	@Override
	public boolean connect(Block firstBlock, Block secondBlock) {
		if (firstBlock instanceof SequenceBlock && secondBlock instanceof SequenceBlock) {
			((SequenceBlock)firstBlock).setNextBlock((SequenceBlock)secondBlock);
			return true;
		}
		else if (firstBlock instanceof ChainConditionBlock && secondBlock instanceof ConditionBlock) {
			((ChainConditionBlock)firstBlock).addCondition(((ConditionBlock)secondBlock));
			return true;
		}
		else if (firstBlock instanceof SurroundingBlock && secondBlock instanceof ConditionBlock) {
			((SurroundingBlock)firstBlock).setConditionBlock(((ConditionBlock)secondBlock));
			return true;
		}
		return false;
		
	}

	@Override
	public boolean disconnect(Block blockToDisconnect) {
		return blockToDisconnect.disconnect();
	}

	@Override
	public void deleteBlock(Block block) {
		disconnect(block);
		block = null;
		//TODO counter adding
		
	}

	@Override
	public GameController makeGameController() {
		return new GameController();
	}

	@Override
	public void addTopLevelBlockToController(GameController gameController, Block block) {
		gameController.addTopLevelBlock(block);
	}

	@Override
	public void removeTopLevelBlockFromController(GameController gameController, Block block) {
		gameController.removeTopLevelBlock(block);
	}

	@Override
	public void randomiseGameWorld(GameController gameController, int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Block getNextBlockToExecute(GameController gameController) {
		return gameController.getNextBlockToExecute();
	}

	@Override
	public void executeNextBlock(GameController gameController) {
		try {
			gameController.execute();
		} catch (Exception e) {
			System.out.println("Exception during execution of block in interface.");
			e.printStackTrace();
		}
	}

	@Override
	public GameWorld makeGameWorld() {
		return new GameWorld();
	}
	
	@Override
	public GameWorld makeGameWorld(Grid grid, Vector vector) {
		return new GameWorld(grid, vector);
	}
	@Override
	public void setGameWorldForController(GameController gameController, GameWorld gameWorld) {
		gameController.setGameWorld(gameWorld);
	}

	@Override
	public boolean addBodyBlock(SurroundingBlock surroundingBlock, SequenceBlock block) {
		// TODO Auto-generated method stub
		return false;
	}


	
	
}