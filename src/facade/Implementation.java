package facade;

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

public class Implementation implements Facade{
	
	public Implementation() {};

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
		if (blockToDisconnect instanceof ConditionBlock) {
			ConditionBlock disc = (ConditionBlock) blockToDisconnect;
			if	(disc.getPrevious() == null) {
				if (disc.getSurroundingBlock() != null) {
					disc.getSurroundingBlock().removeConditionBlock();
					return true;
				}
			}
			else {
				((ChainConditionBlock)(disc.getPrevious())).removeNextCondition();
				return true;
			}
		}
		else if (blockToDisconnect instanceof SequenceBlock) {
			SequenceBlock disc = (SequenceBlock) blockToDisconnect;
			if (disc.getPreviousBlock() != null) {
				disc.getPreviousBlock().removeNextBlock();
				return true;
			}
			else if (disc.getSurroundingBlock()!= null) {
				disc.getSurroundingBlock().removeBodyBlock();
				return true;
			}
		}
	return false;
		
	}

	@Override
	public void deleteBlock(Block block) {
		disconnect(block);
		block = null;
		//TODO counter adding
		
	}

	@Override
	public GameController makeGameController() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameWorld makeGameWorld() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}