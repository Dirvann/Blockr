package domain.block;

public abstract class SingleConditionBlock extends ConditionBlock {

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
	
}
