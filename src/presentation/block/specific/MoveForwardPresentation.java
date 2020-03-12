package presentation.block.specific;

import domain.block.MoveForward;
import domain.block.block_types.Block;
import domain.game_world.Vector;
import presentation.block.ActionBlockPresentation;
import presentation.block.PresentationBlock;

public class MoveForwardPresentation extends ActionBlockPresentation {

	public MoveForwardPresentation(Vector pos, Block block) {
		setPosition(pos);
		setBlock(block);
		setPresentationName("Move Forward");
	}

	@Override
	public PresentationBlock getNewBlockOfThisType(Block block) throws Exception{
		if (block instanceof MoveForward)
			return new MoveForwardPresentation(getPosition(), block);
		else {
			throw new Exception("block is not the same type as blockPresentaion");
		}
	}
}
