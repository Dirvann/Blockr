package presentation.block.specific;

import domain.block.WallInFront;
import domain.block.abstract_classes.SingleConditionBlock;
import domain.game_world.Vector;
import presentation.block.PresentationBlock;
import presentation.block.SingleConditionBlockPresentation;

public class WallInfrontPresentation extends SingleConditionBlockPresentation {

	public WallInfrontPresentation(Vector pos, SingleConditionBlock block) {
		setPosition(pos);
		setBlock(block);
		setPresentationName("Wall In Front");
	}

	@Override
	public PresentationBlock<SingleConditionBlock> getNewBlockOfThisType(SingleConditionBlock block) throws Exception {
		if (block instanceof WallInFront)
			return new WallInfrontPresentation(getPosition(), block);
		else {
			throw new Exception("block is not the same type as blockPresentaion");
		}
	}

}
