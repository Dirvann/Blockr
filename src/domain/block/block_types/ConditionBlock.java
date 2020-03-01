package domain.block.block_types;

public abstract class ConditionBlock extends Block{
	
	/**
	 * 
	 * @param block The block which gets connected to the current one.
	 * This is only applicable when there can be a next condition. (not possible 'Not-WallInFront-WallInFront')
	 */
	public void setNextConditon(ConditionBlock block) {
	}
	
	/**
	 * 
	 * @return The next block of the condition as ConditionBlock.
	 */
	public ConditionBlock getNextCondition() {
		return null;
	}

	public boolean evaluate() {
		return false;
	}
	
	public boolean isValidCondition() {
		return false;
	}
}
