package domain.block;

import domain.GameController;
import exceptions.domainExceptions.NoConditionBlockException;

class IfBlock extends SingleSurroundingBlock {
	
	

	protected IfBlock() {
		super();
	}

	protected Block execute(GameController gameController) throws Exception {
		if (getConditionBlock() == null || !getConditionBlock().isValidCondition()) {
			throw new NoConditionBlockException();
		}
		if (this.getBodyBlock() != null) {
			if (getConditionBlock().evaluate(gameController)) {
				return this.getBodyBlock();
			}
		}
		if (this.getNextBlock() == null) {
			if (this.getSurroundingBlock() == null) {
				return null;
			}
			return this.getSurroundingBlock().getNextAfterLoop();
		}
		return this.getNextBlock();
	}

	@Override
	protected SequenceBlock getNextAfterLoop() {
		if (this.getNextBlock() == null) {
			if (this.getSurroundingBlock() == null) {
				return null;
			}
			
			return this.getSurroundingBlock().getNextAfterLoop();
		}
		return this.getNextBlock();
	}


	
	@Override
	protected IfBlock getNewBlockOfThisType() {
		return new IfBlock();
	}


	@Override
	protected String getName() {
		return "if";
	}

}
