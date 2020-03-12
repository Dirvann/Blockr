package presentation.block.specific;

import domain.block.TurnLeft;
import domain.block.block_types.Block;
import domain.game_world.Vector;
import presentation.block.ActionBlockPresentation;

public class TurnLeftPresentation extends ActionBlockPresentation {

	public TurnLeftPresentation(Vector pos, Block block) {
		setPosition(pos);
		setBlock(block);
		setPresentationName("Turn Left");
	}

	@Override
	public TurnLeftPresentation getNewBlockOfThisType(Block block) throws Exception{
		if (block instanceof TurnLeft)
			return new TurnLeftPresentation(getPosition(), block);
		else {
			throw new Exception("block is not the same type as blockPresentaion");
		}
	}
	
	

}
