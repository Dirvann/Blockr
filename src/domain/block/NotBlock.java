package domain.block;

import domain.GameController;

class NotBlock extends ChainConditionBlock {
	

	protected NotBlock() {
		super();
	}

	protected boolean evaluate(GameController gamecontroller) {
		if (this.getNextCondition() == null) {
			return false;
		}
		return !this.getNextCondition().evaluate(gamecontroller);
	}

	@Override
	protected Block getNewBlockOfThisType() {
		return new NotBlock();
	}

	@Override
	protected String getName() {
		// TODO Auto-generated method stub
		return "Not";
	}
}
