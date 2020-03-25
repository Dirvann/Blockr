package domain.block;

import java.util.List;
import java.util.ArrayList;

import domain.GameController;

abstract class ConditionBlock extends Block {

	protected ConditionBlock next = null;
	private ConditionBlock previous = null;
	private SurroundingBlock surroundingBlock = null;

	/**
	 * 
	 * @return The next block of the condition as ConditionBlock.
	 */
	protected ConditionBlock getNextCondition() {
		ConditionBlock copy = this.next; // TODO: make actual copy
		return copy;
	}

	protected void setNextCondition(ConditionBlock condition) {
		this.next = condition;
		if (condition != null)
			condition.setPrevious(this);
	}

	protected ConditionBlock getPrevious() {
		return previous;
	}

	protected void setPrevious(ConditionBlock previous) {
		this.previous = previous;
	}

	protected SurroundingBlock getSurroundingBlock() {
		return surroundingBlock;
	}

	protected void setSurroundingBlock(SurroundingBlock surroundingBlock) {
		ConditionBlock i = this;
		while (i != null) {
			i.surroundingBlock = surroundingBlock;
			i = i.getNextCondition();
		}
	}

	abstract protected boolean isValidCondition();

	abstract protected boolean evaluate(GameController gamecontroller);

	@Override
	protected List<Block> getAllNextBlocks() {
		List<Block> l = new ArrayList<Block>();

		l.add(this);
		if (this.getNextCondition() != null)
			l.addAll(getNextCondition().getAllNextBlocks());

		return l;
	}

	@Override
	protected Block getPreviousBlock() {
		if (getPrevious() != null) {
			return getPrevious();
		}
		if (getSurroundingBlock() != null) {
			return getSurroundingBlock();
		}
		return null;
	}

	@Override
	protected boolean disconnect() {
		if (getPrevious() == null) {
			if (getSurroundingBlock() != null) {
				getSurroundingBlock().removeConditionBlock();
				return true;
			}
		} else {
			((ChainConditionBlock) (getPrevious())).removeNextCondition();
			return true;
		}
		return false;
	}
	
	@Override
	protected ConditionBlock getLastBlock() {
		if(next == null) {
			return this;
		} else {
			return next.getLastBlock();
		}
	}
}
