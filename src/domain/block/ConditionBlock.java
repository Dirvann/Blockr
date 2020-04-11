package domain.block;

import domain.GameController;

public abstract class ConditionBlock extends Block {

	protected ChainConditionBlock previous = null;

	abstract protected boolean isValidCondition();

	abstract protected boolean evaluate(GameController gamecontroller);

	@Override
	protected void disconnect() {
		Block prev = this.getPreviousBlock();
		if (prev instanceof SurroundingBlock) {
			((SurroundingBlock) prev).removeConditionBlock();
			return;
		}
		if (prev instanceof ChainConditionBlock) {
			((ChainConditionBlock) prev).removeNextBlock();
		}
	}

	@Override
	protected Block getPreviousBlock() {
		if (previous != null) {
			return previous;
		} else if (surroundingBlock != null) {
			return surroundingBlock;
		}
		return null;
	}

	@Override
	protected ConditionBlock getLastBlock() {
		return this;
	}
	
	@Override
	protected Block execute(GameController gameController) throws Exception {
		return null;
	}
	
	@Override
	protected boolean hasValidExecutionColumn() {
		// ConditionBlocks can't be executed
		return false;
	}
	
}
