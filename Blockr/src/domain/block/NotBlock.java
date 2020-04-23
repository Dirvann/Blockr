package domain.block;

import domain.GameController;
import impl.root.ImplementationGameWorld;

class NotBlock extends ChainConditionBlock {
	

	protected NotBlock() {
		super();
	}

	@Override
	protected boolean evaluate(ImplementationGameWorld gamecontroller) {
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
