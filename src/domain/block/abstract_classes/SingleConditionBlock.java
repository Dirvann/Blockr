package domain.block.abstract_classes;

import domain.block.block_types.ConditionBlock;
import presentation.block.SingleConditionBlockPresentation;

public abstract class SingleConditionBlock extends ConditionBlock{
	
	private SingleConditionBlockPresentation presentationBlock;
	
	public SingleConditionBlock() {

	}
	
	public boolean isValidCondition() {
		return true;
	}
	
	@Override
	public SingleConditionBlockPresentation getPresentationBlock() {
		return this.presentationBlock;
	}
	
}
