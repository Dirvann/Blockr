package domain.block;

import domain.GameController;

class WhileBlock extends SingleSurroundingBlock {
	
	
	protected WhileBlock() {
		super();
	}

	protected Block execute(GameController gameController) throws Exception {
		if (getConditionBlock() == null || !getConditionBlock().isValidCondition()) {
			throw new Exception("While-Block does not have a complete condition");
		}
		if (getConditionBlock().evaluate(gameController)) {
			if (this.getBodyBlock() == null) throw new Exception("infinite loop in while");
			return this.getBodyBlock();
		}
		return this.getNextBlock();
	}

	@Override
	protected Block getNewBlockOfThisType() {
		return new WhileBlock();
	}

	@Override
	protected String getName() {
		// TODO Auto-generated method stub
		return "While";
	}

	@Override
	protected SequenceBlock getNextAfterLoop() {
		return this;
	}

}
