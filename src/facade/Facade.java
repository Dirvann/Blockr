package facade;

import domain.GameController;
import domain.block.IfBlock;
import domain.block.MoveForward;
import domain.block.NotBlock;
import domain.block.TurnLeft;
import domain.block.TurnRight;
import domain.block.WallInFront;
import domain.block.WhileBlock;
import domain.block.block_types.Block;
import domain.block.block_types.ConditionBlock;
import domain.block.block_types.SequenceBlock;
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
	 * @param blockOrCondition: A block or condition where conditionToAdd needs to be added.
	 * @param conditionToAdd
	 * @Post condtionToAdd is connected to blockOrCondition
	 */
	public void addCondition(Block blockOrCondition, Block conditionToAdd);
	
	/**
	 * 
	 * @param block
	 * @Post The block will be removed from the game.
	 */
	public void removeBlock(Block block);
	
}