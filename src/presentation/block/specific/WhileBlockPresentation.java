package presentation.block.specific;

import domain.block.WhileBlock;
import domain.game_world.Vector;
import presentation.block.SingleSurroundBlockPresentation;

public class WhileBlockPresentation extends SingleSurroundBlockPresentation {

	public WhileBlockPresentation(Vector pos, WhileBlock block) {
		setPosition(pos);
		setBlock(block);
		setPresentationName("While");
	}

	@Override
	public WhileBlockPresentation getNewBlockOfThisType() {
		return new WhileBlockPresentation(getPosition(), null);
	}

}
