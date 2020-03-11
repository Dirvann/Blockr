package domain.block.block_types;

public abstract class ConditionBlock extends Block{


	public boolean evaluate() {
		return false;
	}
	
	public boolean isValidCondition() {
		return false;
	}
}
