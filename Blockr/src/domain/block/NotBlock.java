package domain.block;

import domain.GameController;

class NotBlock extends ChainConditionBlock {
	

	protected NotBlock() {
		super();
	}

	@Override
	protected boolean evaluate(GameController gamecontroller) {
		if (!this.isValidCondition()) {
			return false;
		}
		return !this.next.evaluate(gamecontroller);
	}

	@Override
	protected NotBlock getNewBlockOfThisType() {
		return new NotBlock();
	}

	@Override
	protected String getName() {
		return "Not";
	}
}
