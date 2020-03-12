package presentation.block.specific;

import domain.block.TurnRight;
import domain.block.block_types.Block;
import domain.game_world.Vector;
import presentation.block.ActionBlockPresentation;

public class TurnRightPresentation extends ActionBlockPresentation {

	public TurnRightPresentation(Vector pos, Block block) {
		setPosition(pos);
		setBlock(block);
		setPresentationName("Turn Right");
	}

	@Override
	public TurnRightPresentation getNewBlockOfThisType(Block block) throws Exception{
		if (block instanceof TurnRight)
			return new TurnRightPresentation(getPosition(), block);
		else {
			throw new Exception("block is not the same type as blockPresentaion");
		}
	}
	
	

}
