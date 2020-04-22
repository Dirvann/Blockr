package domain.block;

import command.ExecutionCommand;
import domain.GameController;
import exceptions.domainExceptions.InfiniteLoopWhileException;
import exceptions.domainExceptions.NoConditionBlockException;

class WhileBlock extends SingleSurroundingBlock {
	
	
	protected WhileBlock() {
		super();
	}

	@Override
	protected Block execute(GameController gameController) throws Exception {
		if (getConditionBlock() == null || !getConditionBlock().isValidCondition()) {
			throw new NoConditionBlockException();
		}
		if (getConditionBlock().evaluate(gameController)) {
			if (this.getBodyBlock() == null) throw new InfiniteLoopWhileException();
			IGC.setExecutionCommand(new ExecutionCommand(null, null, null, gameController), gameController);
			return this.getBodyBlock();
		}
		IGC.setExecutionCommand(new ExecutionCommand(null, null, null, gameController), gameController);
		return this.getNextBlock();
	}

	@Override
	protected boolean hasValidExecutionColumn() {
		// First check if this block has a valid condition
		boolean result = true;
		if (!this.hasValidCondition()) return false;
		if (this.bodyBlock == null) return false;
		// If this block has a bodyBlock, check if it can be executed
		else{
			result = result && this.bodyBlock.hasValidExecutionColumn();
		}
		// If this block has a following block, check if it can be executed
		if (this.getNextBlock() != null) {
			result = result && this.getNextBlock().hasValidExecutionColumn();
		}
		
		return result;
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
