package domain.block;

import domain.GameController;
import domain.block.abstract_classes.ChainConditionBlock;
import domain.block.block_types.Block;

public class NotBlock extends ChainConditionBlock {

	public NotBlock() {
		super();
	}

	public boolean evaluate(GameController gamecontroller) {
		if (this.getNextCondition() == null) {
			return false;
		}
		return !this.getNextCondition().evaluate(gamecontroller);
	}

	@Override
	public Block getNewBlockOfThisType() {
		return new NotBlock();
	}
}
