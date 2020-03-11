package domain.block.block_types;

import domain.GameController;
import domain.block.abstract_classes.SurroundingBlock;

public abstract class ConditionBlock extends Block{


	public boolean evaluate() {
		return false;
	}
	
	public boolean isValidCondition() {
		return false;
	}

	public Block getPrevious() {
		return previous;
	}

	public void setPrevious(ConditionBlock previous) {
		this.previous = previous;
	}

	public SurroundingBlock getSurroundingBlock() {
		return surroundingBlock;
	}

	public void setSurroundingBlock(SurroundingBlock surroundingBlock) {
		ConditionBlock i = this;
		while (i != null) {
			i.surroundingBlock = surroundingBlock;
			i = i.getNextCondition();
		}
	}
	
	abstract public boolean isValidCondition();

	abstract public boolean evaluate(GameController gamecontroller);
}
