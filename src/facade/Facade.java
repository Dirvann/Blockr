package facade;


import domain.block.block_types.Block;

public interface Facade {

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
	public void connect(Block firstBlock, Block secondBlock);
	
	/**
	 * 
	 * @param firstBlock
	 * @param blockToDisconnect
	 * @Post blockToDisconnct is disconnected from firstBlock.
	 */
	public void disconnect(Block firstBlock, Block blockToDisconnect);
	
	
	/**
	 * 
	 * @param blockOrCondition: A block or condition where conditionToAdd needs to be added.
	 * @param conditionToAdd
	 * @Post condtionToAdd is connected to blockOrCondition
	 */
	public void addCondition(Block blockOrCondition, Block conditionToAdd);
	
	public void removeBlock(Block block);
	
}
