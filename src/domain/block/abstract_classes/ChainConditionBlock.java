package domain.block.abstract_classes;

import domain.block.block_types.ConditionBlock;
import domain.game_world.Vector;
import presentation.block.ChainConditionBlockPresentation;

public abstract class ChainConditionBlock extends ConditionBlock{
	
	private ChainConditionBlockPresentation presentationBlock;
	
	public ChainConditionBlock(Vector pos) {
		this.presentationBlock = new ChainConditionBlockPresentation(pos, this);
	}
	
	/**
	 * 
	 * @param block The block which gets connected to the current one.
	 */
	public void addCondition(ConditionBlock block) {
		block.setPrevious(this);
		
		ConditionBlock last = block;
		while(last.getNextCondition() != null) {
			last = last.getNextCondition();
		}
		last.setNextCondition(getNextCondition());
		if (getNextCondition() != null) getNextCondition().setPrevious(last);
		setNextCondition(block);
	}

	public boolean isValidCondition() {
		if (getNextCondition() != null)
			return getNextCondition().isValidCondition();
		return false;
	}
	
	@Override
	public ChainConditionBlockPresentation getPresentationBlock() {
		return this.presentationBlock;
	}

}
