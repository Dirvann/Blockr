package presentation.block.specific;

import domain.block.IfBlock;
import domain.block.block_types.Block;
import domain.game_world.Vector;
import presentation.block.SingleSurroundBlockPresentation;

public class IfBlockPresentation extends SingleSurroundBlockPresentation {
	
	public IfBlockPresentation(Vector pos, Block block) {
		setPosition(pos);
		setBlock(block);
		setPresentationName("If");
	}

	@Override
	public IfBlockPresentation getNewBlockOfThisType(Block block) throws Exception {
		if (block instanceof IfBlock)
			return new IfBlockPresentation(getPosition(), block);
		else {
			throw new Exception("block is not the same type as blockPresentaion");
		}
	}

}
