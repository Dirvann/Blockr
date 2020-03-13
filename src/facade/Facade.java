package facade;

import domain.GameController;
import domain.block.abstract_classes.SurroundingBlock;
import domain.block.block_types.Block;
import domain.block.block_types.SequenceBlock;
import domain.game_world.GameWorld;
import domain.game_world.Grid;
import domain.game_world.Vector;

public interface Facade {
	//TODO: positions and gameworld
	
	public GameController makeGameController(Vector pos);

	public Block makeIfBlock(Vector pos);
	
	public Block makeMoveForwardBlock(Vector pos);
	
	public Block makeNotBlock(Vector pos);
	
	public Block makeTurnLeftBlock(Vector pos);
	
	public Block makeTurnRightBlock(Vector pos);
	
	public Block makeWallInFrontBlock(Vector pos);
	
	public Block makeWhileBlock(Vector pos);
	
	
	/**
	 * 
	 * @param firstBlock
	 * @param secondBlock
	 * @Post The secondBlock is connected to the first block..
	 */
	public boolean connect(Block firstBlock, Block secondBlock);
	
	/**
	 * 
	 * @param blockToDisconnect
	 * @Post blockToDisconnct is disconnected.
	 */
	public boolean disconnect(Block blockToDisconnect);
	
	public boolean addBodyBlock(SurroundingBlock surroundingBlock, SequenceBlock block);
	

	
	/**
	 * 
	 * @param block
	 * @Post The block will be deleted from the game.
	 */
	public void deleteBlock(Block block);

	
	
	public void setGameWorldForController(GameController gameController, GameWorld gameWorld);
	
	public void addTopLevelBlockToController(GameController gameController, Block block);
	
	public void removeTopLevelBlockFromController(GameController gamecontroller, Block block);
	
	public void randomiseGameWorld(GameController gameController, int width, int height);
	
	public Block getNextBlockToExecute(GameController gameController);
	
	public void executeNextBlock(GameController gameController);

	public GameWorld makeGameWorld();

	public GameWorld makeGameWorld(Grid grid, Vector vector);
	
	
	
}