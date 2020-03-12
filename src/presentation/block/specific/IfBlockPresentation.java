package presentation.block.specific;

import domain.block.IfBlock;
import domain.game_world.Vector;
import presentation.block.SingleSurroundBlockPresentation;

public class IfBlockPresentation extends SingleSurroundBlockPresentation {

	public IfBlockPresentation(Vector pos, IfBlock block) {
		setPosition(pos);
		setBlock(block);
	}

	@Override
	public IfBlockPresentation getNewBlockOfThisType() {
		return new IfBlockPresentation(getPosition(), null);
	}

}
