package presentation.block.specific;

import domain.block.WallInFront;
import domain.block.block_types.Block;
import domain.game_world.Vector;
import presentation.block.SingleConditionBlockPresentation;

public class WallInfrontPresentation extends SingleConditionBlockPresentation {

	public WallInfrontPresentation(Vector pos, Block block) {
		setPosition(pos);
		setBlock(block);
		setPresentationName("Wall In Front");
	}

	@Override
	public WallInfrontPresentation getNewBlockOfThisType(Block block) throws Exception{
		if (block instanceof WallInFront)
			return new WallInfrontPresentation(getPosition(), block);
		else {
			throw new Exception("block is not the same type as blockPresentaion");
		}
	}

}
