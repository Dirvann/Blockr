package domain.block.block_types;

public abstract class ConditionBlock extends Block{

	public boolean evaluate() throws Exception {
		throw new Exception("no condition block to evaluate");
	}
	
	public boolean isValidCondition() {
		return false;
	}
}
