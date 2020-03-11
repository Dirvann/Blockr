package facade;

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

public interface Facade {
	//TODO: positions and gameworld

	public Block makeIfBlock(int ID);
	
	public Block makeMoveForwardBlock(int ID);
	
	public Block makeNotBlock(int ID);
	
	public Block makeTurnLeftBlock(int ID);
	
	public Block makeTurnRightBlock(int ID);
	
	public Block makeWallInFrontBlock(int ID);
	
	public Block makeWhileBlock(int ID);
	
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

class Implementation implements Facade{

	@Override
	public Block makeIfBlock(int ID) {
		Block block = new IfBlock();
		block.setID(ID);
		return block;
	}

	@Override
	public Block makeMoveForwardBlock(int ID) {
		Block block = new MoveForward();
		block.setID(ID);
		return block;
	}

	@Override
	public Block makeNotBlock(int ID) {
		Block block = new NotBlock();
		block.setID(ID);
		return block;
	}

	@Override
	public Block makeTurnLeftBlock(int ID) {
		Block block = new TurnLeft();
		block.setID(ID);
		return block;
	}

	@Override
	public Block makeTurnRightBlock(int ID) {
		Block block = new TurnRight();
		block.setID(ID);
		return block;
	}

	@Override
	public Block makeWallInFrontBlock(int ID) {
		Block block = new WallInFront();
		block.setID(ID);
		return block;
	}

	@Override
	public Block makeWhileBlock(int ID) {
		Block block = new WhileBlock();
		block.setID(ID);
		return block;
	}

	@Override
	public boolean connect(Block firstBlock, Block secondBlock) {
		if (firstBlock instanceof SequenceBlock && secondBlock instanceof SequenceBlock) {
			((SequenceBlock)firstBlock).setNextBlock((SequenceBlock)secondBlock);
			return true;
		}
		return false;
		
	}

	@Override
	public boolean disconnect(Block blockToDisconnect) {
		if (blockToDisconnect instanceof ConditionBlock) {
			ConditionBlock disc = (ConditionBlock) blockToDisconnect;
		}
		if (blockToDisconnect instanceof SequenceBlock) {
			SequenceBlock disc = (SequenceBlock) blockToDisconnect;
		}
	return false;
		
	}

	@Override
	public void addCondition(Block blockOrCondition, Block conditionToAdd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeBlock(Block block) {
		// TODO Auto-generated method stub
		
	}

	
	
}
