package domain.block;

import domain.GameController;
import exceptions.domainExceptions.InfiniteLoopWhileException;
import exceptions.domainExceptions.NoConditionBlockException;

class WhileBlock extends SingleSurroundingBlock {
	
	
	protected WhileBlock() {
		super();
	}

	protected Block execute(GameController gameController) throws Exception {
		if (getConditionBlock() == null || !getConditionBlock().isValidCondition()) {
			throw new NoConditionBlockException();
		}
		if (getConditionBlock().evaluate(gameController)) {
			if (this.getBodyBlock() == null) throw new InfiniteLoopWhileException();
			return this.getBodyBlock();
		}
		return this.getNextBlock();
	}

	@Override
	protected WhileBlock getNewBlockOfThisType() {
		return new WhileBlock();
	}

	@Override
	protected String getName() {
		return "While";
	}

	@Override
	protected SequenceBlock getNextAfterLoop() {
		return this;
	}

}
