package domain.block.abstract_classes;

import domain.block.block_types.ConditionBlock;
import domain.game_world.Vector;
import presentation.block.PresentationBlock;
import presentation.block.SingleConditionBlockPresentation;

public abstract class SingleConditionBlock extends ConditionBlock{
	
	private SingleConditionBlockPresentation presentationBlock;
	
	public SingleConditionBlock(Vector pos, String displayName) {
		this.presentationBlock = new SingleConditionBlockPresentation(pos, this, displayName);
	}
	
	public boolean isValidCondition() {
		return true;
	}
	
	@Override
	public SingleConditionBlockPresentation getPresentationBlock() {
		return this.presentationBlock;
	}
	
}
