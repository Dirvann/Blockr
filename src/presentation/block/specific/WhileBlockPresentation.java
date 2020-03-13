package presentation.block.specific;

import domain.block.WhileBlock;
import domain.block.abstract_classes.SingleSurroundingBlock;
import domain.game_world.Vector;
import presentation.block.PresentationBlock;
import presentation.block.SingleSurroundBlockPresentation;

public class WhileBlockPresentation extends SingleSurroundBlockPresentation {

	public WhileBlockPresentation(Vector pos, SingleSurroundingBlock block) {
		setPosition(pos);
		setBlock(block);
		setPresentationName("While");
	}

	@Override
	public PresentationBlock<SingleSurroundingBlock> getNewBlockOfThisType(SingleSurroundingBlock block) throws Exception {
		if (block instanceof WhileBlock)
			return new WhileBlockPresentation(getPosition(), block);
		else {
			throw new Exception("block is not the same type as blockPresentaion");
		}
	}

}
