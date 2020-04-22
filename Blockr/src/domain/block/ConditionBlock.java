package domain.block;

import domain.GameController;

public abstract class ConditionBlock extends Block {

	protected ChainConditionBlock previous = null;

	/**
	 * 
	 * @return true if it would be possible to evaluate.
	 */
	abstract protected boolean isValidCondition();

	/**
	 * 
	 * @param gamecontroller
	 * @return true if the condition is true;
	 */
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
	protected Block execute(GameController gameController) throws Exception{
		throw new Exception("conditionblocks can't be executed");
	}
	
	@Override
	protected boolean hasValidExecutionColumn() {
		// ConditionBlocks can't be executed
		return false;
	}
	
}
