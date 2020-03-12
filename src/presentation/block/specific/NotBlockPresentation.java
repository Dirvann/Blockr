package presentation.block.specific;

import domain.block.NotBlock;
import domain.block.block_types.Block;
import domain.game_world.Vector;
import presentation.block.ChainConditionBlockPresentation;
import presentation.block.PresentationBlock;

public class NotBlockPresentation extends ChainConditionBlockPresentation {

	public NotBlockPresentation(Vector position, Block block) {
		setPosition(position);
		setBlock(block);
		setPresentationName("Not");
	}

	@Override
	public PresentationBlock getNewBlockOfThisType(Block block) throws Exception{
		if (block instanceof NotBlock)
			return new NotBlockPresentation(getPosition(), block);
		else {
			throw new Exception("block is not the same type as blockPresentaion");
		}
	}
	
	

}
