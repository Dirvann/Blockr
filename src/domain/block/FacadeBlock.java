package domain.block;

public interface FacadeBlock {
	//TODO: positions and gameworld

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
	
	public boolean addBodyBlock(SurroundingBlock surroundingBlock, SequenceBlock block);
	

	
	/**
	 * 
	 * @param block
	 * @Post The block will be deleted from the game.
	 */
	public void deleteBlock(Block block);
	
}