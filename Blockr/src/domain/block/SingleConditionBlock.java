package domain.block;

import domain.GameController;
import game_world.ImplementationGameWorld;

public class SingleConditionBlock extends ConditionBlock {
	
	private String name;
	
	public SingleConditionBlock(String name) {
		this.name = name;
	}
	
	@Override
	protected boolean isValidCondition() {
		return true;
	}
	
	@Override
	protected void removeNextBlock() {
		return;		
	}

	@Override
	protected Block getNextBlock() {
		return null;
	}

	@Override
	protected boolean setNextBlock(Block block) {
		return false;
	}

	@Override
	protected boolean evaluate(ImplementationGameWorld iGameWorld) {
		if (iGameWorld == null) {
			return false;
		}		
		else
			return iGameWorld.evaluatePredicate(getName());
	}

	@Override
	protected Block getNewBlockOfThisType() {
		return new SingleConditionBlock(getName());
	}

	@Override
	protected String getName() {
		return this.name;
	}
	
	
	
}
