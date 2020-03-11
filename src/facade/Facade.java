package facade;

import domain.GameController;
import domain.block.block_types.Block;
import domain.game_world.GameWorld;

public interface Facade {
	//TODO: positions and gameworld
	
	public GameController makeGameController();

	public GameWorld makeGameWorld();

	public Block makeIfBlock();
	
	public Block makeMoveForwardBlock();
	
	public Block makeNotBlock();
	
	public Block makeTurnLeftBlock();
	
	public Block makeTurnRightBlock();
	
	public Block makeWallInFrontBlock();
	
	public Block makeWhileBlock();
	
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
	

	
	/**
	 * 
	 * @param block
	 * @Post The block will be deleted from the game.
	 */
	public void deleteBlock(Block block);

	
}