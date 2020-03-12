package presentation.block.specific;

import domain.block.WhileBlock;
import domain.block.block_types.Block;
import domain.game_world.Vector;
import presentation.block.SingleSurroundBlockPresentation;

public class WhileBlockPresentation extends SingleSurroundBlockPresentation {

	public WhileBlockPresentation(Vector pos, Block block) {
		setPosition(pos);
		setBlock(block);
		setPresentationName("While");
	}

	@Override
	public WhileBlockPresentation getNewBlockOfThisType(Block block) throws Exception{
		if (block instanceof WhileBlock)
			return new WhileBlockPresentation(getPosition(), block);
		else {
			throw new Exception("block is not the same type as blockPresentaion");
		}
	}

}
