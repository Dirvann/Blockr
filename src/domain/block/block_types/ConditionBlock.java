package domain.block.block_types;

import java.util.List;
import java.util.ArrayList;

import domain.GameController;
import domain.block.abstract_classes.SurroundingBlock;

public abstract class ConditionBlock extends Block{


	private ConditionBlock next = null;
	private ConditionBlock previous = null;
	private SurroundingBlock surroundingBlock = null;
	
	
	/**
	 * 
	 * @return The next block of the condition as ConditionBlock.
	 */
	public ConditionBlock getNextCondition() {
		ConditionBlock copy = this.next; //TODO: make actual copy
		return copy;
	}
	
	public void setNextCondition(ConditionBlock condition) {
		this.next = condition;
	}

	public ConditionBlock getPrevious() {
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
	
	@Override
	public List<Block> getAllNextBlocks() {
		List<Block> l = new ArrayList<Block>();
		
		l.add(this);
		if(this.getNextCondition() != null) 
			l.addAll(getNextCondition().getAllNextBlocks());
		
		
		return l;
	}
}
